package com.smartnotify.parcel.api;

import com.smartnotify.parcel.api.json.ParcelDeliveryRequest;
import com.smartnotify.parcel.api.json.ParcelNotificationRequest;
import com.smartnotify.parcel.api.json.ParcelResponse;
import com.smartnotify.parcel.model.DeliveryStatus;
import com.smartnotify.parcel.service.ParcelService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/parcel")
public class ParcelController {

    private final ParcelService parcelService;

    @GetMapping
    public ResponseEntity<List<ParcelResponse>> getParcelsByStatus(
            @Valid @NotNull @RequestParam("status") final DeliveryStatus status) {

        final var parcels = parcelService.getParcelsByStatus(status);

        final var parcelsResponse = parcels.stream()
                .map(ParcelResponse::convertToParcelResponse)
                .toList();

        return ResponseEntity.ok(parcelsResponse);
    }

    @PostMapping("/notification")
    public ResponseEntity<?> sendNotification(@RequestBody @Valid final ParcelNotificationRequest request) {
        parcelService.sendParcelNotification(request.getLabelContent());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/validation-code")
    public ResponseEntity<?> deliverParcel(@PathVariable("id") final String id,
                                           @RequestBody @Valid final ParcelDeliveryRequest request) {
        parcelService.deliverParcel(id, request.getDeliveryCode());
        return ResponseEntity.ok().build();
    }

}
