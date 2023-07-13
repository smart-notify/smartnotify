package com.smartnotify.resident.api.json;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.smartnotify.residence.constants.ResidenceConstants.APARTMENT_PATTERN;
import static com.smartnotify.residence.constants.ResidenceConstants.BLOCK_PATTERN;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterVerticalCondoResidentRequest {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp = APARTMENT_PATTERN,
            message = "apartmentNumber is out of the accepted pattern. it must be like: apto 1")
    private String apartmentNumber;

    @Pattern(regexp = BLOCK_PATTERN,
            message = "block is out of the accepted pattern. it must be like: bloco 1")
    private String block;

}
