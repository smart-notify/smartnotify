package com.smartnotify.condominium.api.json;

import com.smartnotify.condominium.model.Condominium;
import com.smartnotify.condominium.model.CondominiumType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CondominiumSignUpRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotNull
    private CondominiumType type;

    public static Condominium toCondominium(final CondominiumSignUpRequest request) {
        return Condominium.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .name(request.getName())
                .type(request.getType())
                .build();
    }

}
