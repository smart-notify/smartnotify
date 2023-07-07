package com.smartnotify.condominium.api.json;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CondominiumLoginRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
