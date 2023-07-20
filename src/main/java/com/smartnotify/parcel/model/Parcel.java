package com.smartnotify.parcel.model;

import com.smartnotify.condominium.model.Condominium;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Parcel {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status = DeliveryStatus.NOT_DELIVERED;

    @Column(nullable = false)
    private String deliveryCode;

    @Column(nullable = false)
    private String registrationCode;

    @Column(nullable = false)
    private String residenceDetails;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime registrationDate;

    @Column
    private LocalDateTime deliveryDate;

    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "condominium_id", nullable = false)
    private Condominium condominium;

}
