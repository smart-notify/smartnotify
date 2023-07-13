package com.smartnotify.parcel.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.smartnotify.condominium.model.Condominium;
import com.smartnotify.notification.model.Notification;
import com.smartnotify.resident.model.Resident;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime registrationDate;

    @Column
    private LocalDateTime deliveryDate;

    @Builder.Default
    @JsonManagedReference
    @OneToMany(mappedBy = "parcel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "resident_id")
    private Resident resident;

    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "condominium_id", nullable = false)
    private Condominium condominium;

}
