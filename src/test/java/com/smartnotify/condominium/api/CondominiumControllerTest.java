package com.smartnotify.condominium.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartnotify.condominium.api.json.CondominiumSignUpRequest;
import com.smartnotify.condominium.model.Condominium;
import com.smartnotify.condominium.model.CondominiumType;
import com.smartnotify.condominium.service.CondominiumService;
import com.smartnotify.config.exception.ExceptionHandlerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CondominiumControllerTest {

    private MockMvc mvc;

    public static final String CONDOMINIUM_API_PATH = "/api/condominium";

    @Mock
    private CondominiumService condominiumService;

    @InjectMocks
    private CondominiumController controller;

    @Captor
    private ArgumentCaptor<Condominium> condominiumCaptor;

    @BeforeEach
    void setupMockMvc() {
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ExceptionHandlerConfig())
                .build();
    }

    @Test
    public void testSignUp() throws Exception {
        CondominiumSignUpRequest signUpRequest = CondominiumSignUpRequest.builder()
                .email("test@email.com")
                .password("123")
                .name("Test condominium")
                .type(CondominiumType.VERTICAL)
                .build();

        String requestBody = new ObjectMapper().writeValueAsString(signUpRequest);

        mvc.perform(post(CONDOMINIUM_API_PATH + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());


        verify(condominiumService, times(1)).signUpCondominium(condominiumCaptor.capture());
        Condominium capturedCondominium = condominiumCaptor.getValue();

        assertEquals(signUpRequest.getEmail(), capturedCondominium.getEmail());
        assertEquals(signUpRequest.getPassword(), capturedCondominium.getPassword());
        assertEquals(signUpRequest.getName(), capturedCondominium.getName());
        assertEquals(signUpRequest.getType(), capturedCondominium.getType());
    }

}