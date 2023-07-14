package com.smartnotify.parcel.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartnotify.config.exception.ExceptionHandlerConfig;
import com.smartnotify.parcel.api.json.ParcelDeliveryRequest;
import com.smartnotify.parcel.api.json.ParcelNotificationRequest;
import com.smartnotify.parcel.model.DeliveryStatus;
import com.smartnotify.parcel.model.Parcel;
import com.smartnotify.parcel.service.ParcelService;
import com.smartnotify.resident.model.VerticalCondoResident;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ParcelControllerTest {

    private MockMvc mvc;

    public static final String PARCEL_API_PATH = "/api/parcel";

    @Mock
    private ParcelService parcelService;

    @InjectMocks
    private ParcelController controller;

    @BeforeEach
    void setupMockMvc() {
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ExceptionHandlerConfig())
                .build();
    }

    @Test
    void testGetParcelsByStatus() throws Exception {
        final var resident = new VerticalCondoResident();
        resident.setName("name");
        resident.setEmail("email@email.com");

        final var parcel = Parcel.builder()
                .id("id")
                .resident(resident)
                .status(DeliveryStatus.NOT_DELIVERED)
                .registrationCode("5678")
                .deliveryCode("1234")
                .build();

        when(parcelService.getParcelsByStatus(DeliveryStatus.NOT_DELIVERED)).thenReturn(List.of(parcel));

        mvc.perform(get(PARCEL_API_PATH + "?status=NOT_DELIVERED")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(parcelService, times(1)).getParcelsByStatus(DeliveryStatus.NOT_DELIVERED);
    }

    @Test
    void testSendNotification() throws Exception {
        final var request = ParcelNotificationRequest.builder().labelContent("label content").build();

        String requestBody = new ObjectMapper().writeValueAsString(request);

        mvc.perform(post(PARCEL_API_PATH + "/notification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(parcelService, times(1)).sendParcelNotification(request.getLabelContent());
    }

    @Test
    void testDeliverParcel() throws Exception {
        final var id = "parcelId";
        final var request = ParcelDeliveryRequest.builder()
                .deliveryCode("1234")
                .build();

        String requestBody = new ObjectMapper().writeValueAsString(request);

        mvc.perform(post(PARCEL_API_PATH + "/" + id + "/validation-code")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(parcelService, times(1)).deliverParcel(id, request.getDeliveryCode());
    }

}