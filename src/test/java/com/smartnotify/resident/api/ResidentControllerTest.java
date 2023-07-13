package com.smartnotify.resident.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartnotify.condominium.model.Condominium;
import com.smartnotify.condominium.model.CondominiumType;
import com.smartnotify.config.exception.ExceptionHandlerConfig;
import com.smartnotify.resident.api.json.DeleteResidentRequest;
import com.smartnotify.resident.api.json.RegisterHorizontalCondoResidentRequest;
import com.smartnotify.resident.api.json.RegisterVerticalCondoResidentRequest;
import com.smartnotify.resident.model.HorizontalCondoResident;
import com.smartnotify.resident.model.VerticalCondoResident;
import com.smartnotify.resident.repository.HorizontalCondoResidentRepository;
import com.smartnotify.resident.repository.VerticalCondoResidentRepository;
import com.smartnotify.resident.service.HorizontalCondoResidentService;
import com.smartnotify.resident.service.VerticalCondoResidentService;
import com.smartnotify.resident.usecases.delete.DeleteHorizontalCondoResident;
import com.smartnotify.resident.usecases.delete.DeleteVerticalCondoResident;
import com.smartnotify.resident.usecases.delete.factory.DeleteResidentFactory;
import com.smartnotify.resident.usecases.register.RegisterHorizontalCondoResident;
import com.smartnotify.resident.usecases.register.RegisterVerticalCondoResident;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ResidentControllerTest {

    private MockMvc mvc;

    public static final String RESIDENT_API_PATH = "/api/resident";

    @Mock
    private VerticalCondoResidentService verticalCondoResidentService;

    @Mock
    private HorizontalCondoResidentService horizontalCondoResidentService;

    @Mock
    private RegisterVerticalCondoResident registerVerticalCondoResident;

    @Mock
    private RegisterHorizontalCondoResident registerHorizontalCondoResident;

    @Mock
    private DeleteResidentFactory deleteResidentFactory;

    @Mock
    private VerticalCondoResidentRepository verticalCondoResidentRepository;

    @Mock
    private HorizontalCondoResidentRepository horizontalCondoResidentRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private ResidentController controller;

    @Captor
    private ArgumentCaptor<VerticalCondoResident> verticalCondoResidentCaptor;

    @Captor
    private ArgumentCaptor<HorizontalCondoResident> horizontalCondoResidentCaptor;

    @BeforeEach
    void setupMockMvc() {
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ExceptionHandlerConfig())
                .build();
    }

    @Test
    void testRegisterVerticalCondoResidentWithBlock() throws Exception {
        final var request = RegisterVerticalCondoResidentRequest.builder()
                .name("test")
                .email("email@email.com")
                .apartmentNumber("APTO 22")
                .block("BLOCO 1")
                .build();

        String requestBody = new ObjectMapper().writeValueAsString(request);

        when(verticalCondoResidentService.buildVerticalCondoResident(any()))
                .thenReturn(VerticalCondoResident.builder()
                        .apartmentNumber(request.getApartmentNumber())
                        .block(request.getBlock())
                        .build());

        mvc.perform(post(RESIDENT_API_PATH + "/vertical-condo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(registerVerticalCondoResident, times(1))
                .execute(verticalCondoResidentCaptor.capture());
        VerticalCondoResident capturedResident = verticalCondoResidentCaptor.getValue();

        assertEquals(request.getApartmentNumber(), capturedResident.getApartmentNumber());
        assertEquals(request.getBlock(), capturedResident.getBlock());
    }

    @Test
    void testRegisterVerticalCondoResidentWithoutBlock() throws Exception {
        final var request = RegisterVerticalCondoResidentRequest.builder()
                .name("test")
                .email("email@email.com")
                .apartmentNumber("APTO 22")
                .build();

        String requestBody = new ObjectMapper().writeValueAsString(request);

        when(verticalCondoResidentService.buildVerticalCondoResident(any()))
                .thenReturn(VerticalCondoResident.builder()
                        .apartmentNumber(request.getApartmentNumber())
                        .build());

        mvc.perform(post(RESIDENT_API_PATH + "/vertical-condo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(registerVerticalCondoResident, times(1))
                .execute(verticalCondoResidentCaptor.capture());
        VerticalCondoResident capturedResident = verticalCondoResidentCaptor.getValue();

        assertEquals(request.getApartmentNumber(), capturedResident.getApartmentNumber());
        assertNull(request.getBlock());
    }

    @Test
    void testRegisterHorizontalCondoResident() throws Exception {
        final var request = RegisterHorizontalCondoResidentRequest.builder()
                .name("test")
                .email("email@email.com")
                .houseNumber("CASA 1")
                .build();

        String requestBody = new ObjectMapper().writeValueAsString(request);

        when(horizontalCondoResidentService.buildHorizontalCondoResident(any()))
                .thenReturn(HorizontalCondoResident.builder()
                        .houseNumber("CASA 1")
                        .build());

        mvc.perform(post(RESIDENT_API_PATH + "/horizontal-condo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(registerHorizontalCondoResident, times(1))
                .execute(horizontalCondoResidentCaptor.capture());
        HorizontalCondoResident capturedResident = horizontalCondoResidentCaptor.getValue();

        assertEquals(request.getHouseNumber(), capturedResident.getHouseNumber());
    }

    @Test
    void testDeleteVerticalCondoResident() throws Exception {
        final var request = DeleteResidentRequest.builder()
                .email("email@email.com")
                .build();

        String requestBody = new ObjectMapper().writeValueAsString(request);

        final var principal = Condominium.builder()
                .type(CondominiumType.VERTICAL)
                .build();

        when(authentication.getPrincipal()).thenReturn(principal);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(verticalCondoResidentRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(VerticalCondoResident.builder().build()));

        when(deleteResidentFactory.findStrategy(CondominiumType.VERTICAL))
                .thenReturn(new DeleteVerticalCondoResident(verticalCondoResidentRepository));

        mvc.perform(delete(RESIDENT_API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(deleteResidentFactory, times(1)).findStrategy(CondominiumType.VERTICAL);
    }

    @Test
    void testDeleteHorizontalCondoResident() throws Exception {
        final var request = DeleteResidentRequest.builder()
                .email("email@email.com")
                .build();

        String requestBody = new ObjectMapper().writeValueAsString(request);

        final var principal = Condominium.builder()
                .type(CondominiumType.HORIZONTAL)
                .build();

        when(authentication.getPrincipal()).thenReturn(principal);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(horizontalCondoResidentRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(HorizontalCondoResident.builder().build()));

        when(deleteResidentFactory.findStrategy(CondominiumType.HORIZONTAL))
                .thenReturn(new DeleteHorizontalCondoResident(horizontalCondoResidentRepository));

        mvc.perform(delete(RESIDENT_API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(deleteResidentFactory, times(1)).findStrategy(CondominiumType.HORIZONTAL);
    }

}
