package com.smartnotify.resident.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerticalCondoResident extends Resident {

    @Column(nullable = false)
    private String apartmentNumber;

    private String block;

}
