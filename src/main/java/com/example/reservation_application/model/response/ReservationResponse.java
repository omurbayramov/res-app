package com.example.reservation_application.model.response;

import com.example.reservation_application.model.ReservationStatus;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {
    private Long id;
    private String customerName;
    private int membersCount;
    private String reservationDate;
    private String reservationTime;
    private ReservationStatus status;
    private int tableNumber;
}
