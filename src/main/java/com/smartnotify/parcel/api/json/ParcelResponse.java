package com.smartnotify.parcel.api.json;

import com.smartnotify.parcel.model.Parcel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParcelResponse {

    private String id;
    private String residenceDetails;
    private String registrationCode;

    public static ParcelResponse convertToParcelResponse(final Parcel parcel) {
        return ParcelResponse.builder()
                .id(parcel.getId())
                .residenceDetails(parcel.getResidenceDetails())
                .registrationCode(parcel.getRegistrationCode())
                .build();
    }

}
