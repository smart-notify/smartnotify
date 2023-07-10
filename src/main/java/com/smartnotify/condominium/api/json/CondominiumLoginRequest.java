package com.smartnotify.condominium.api.json;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CondominiumLoginRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
