package com.example.reservation_application.model;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Table (name = "reservations")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Integer tableNumber;

    private String customerName;

    private String reservationDate;

    private String reservationTime;

    private Integer membersCount;

    @Enumerated(EnumType.STRING)
    private ReservationStatus resStatus;

    private ReservationStatus status;

}