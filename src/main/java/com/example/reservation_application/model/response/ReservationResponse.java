package com.example.reservation_application.model.response;

import com.example.reservation_application.model.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {
    private Long id;
    private String customerName;
    private int membersCount;
    private LocalDate reservationDate;
    private LocalTime reservationTime;
    private ReservationStatus status;
    private int tableNumber;
}
