package com.smartnotify.resident.api.json;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.smartnotify.residence.constants.ResidenceConstants.HOUSE_PATTERN;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterHorizontalCondoResidentRequest {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp = HOUSE_PATTERN,
            message = "houseNumber is out of the accepted pattern. it must be like: casa 1")
    private String houseNumber;

}
