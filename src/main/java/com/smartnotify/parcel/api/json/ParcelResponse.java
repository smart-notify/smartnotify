package com.smartnotify.parcel.api.json;

import com.smartnotify.parcel.model.Parcel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParcelResponse {

    private String id;
    private String residentName;
    private String residentEmail;
    private String registrationCode;

    public static ParcelResponse convertToParcelResponse(final Parcel parcel) {
        return ParcelResponse.builder()
                .id(parcel.getId())
                .residentName(parcel.getResident().getName())
                .residentEmail(parcel.getResident().getEmail())
                .registrationCode(parcel.getRegistrationCode())
                .build();
    }

}
