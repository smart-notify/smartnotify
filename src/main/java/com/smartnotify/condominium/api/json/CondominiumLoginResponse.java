package com.smartnotify.condominium.api.json;

import com.smartnotify.condominium.model.Condominium;
import com.smartnotify.condominium.model.CondominiumType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CondominiumLoginResponse {

    private String token;
    private String tokenType;
    private String condominiumName;
    private CondominiumType condominiumType;

    public static CondominiumLoginResponse toCondominiumLoginResponse(final String jwtToken) {
        final var condominium = Condominium.getAuthenticatedCondominium();

        return CondominiumLoginResponse.builder()
                .token(jwtToken)
                .tokenType("Bearer")
                .condominiumName(condominium.getName())
                .condominiumType(condominium.getType())
                .build();
    }

}
