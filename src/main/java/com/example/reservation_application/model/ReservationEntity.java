package com.example.reservation_application.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

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

    private LocalDate reservationDate;

    private LocalTime reservationTime;

    private Integer membersCount;

    private ReservationStatus status;

}