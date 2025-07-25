package com.example.reservation_application.dto;

import com.example.reservation_application.model.ReservationStatus;
import lombok.Data;

@Data
public class ReservationDto {
    private Long id;
    private String customerName;
    private int membersCount;
    private String reservationDate;
    private String reservationTime;
    private ReservationStatus status;
    private int tableNumber;
}
