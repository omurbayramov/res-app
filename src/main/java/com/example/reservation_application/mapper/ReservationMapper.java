package com.example.reservation_application.mapper;

import com.example.reservation_application.model.ReservationEntity;
import com.example.reservation_application.model.ReservationStatus;
import com.example.reservation_application.model.request.ReservationRequest;
import com.example.reservation_application.model.response.ReservationResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class ReservationMapper {

    public ReservationEntity toEntity(ReservationRequest request) {
        LocalDate date = LocalDate.parse(request.getReservationDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalTime time = LocalTime.parse(request.getReservationTime(), DateTimeFormatter.ofPattern("HH:mm"));

        return ReservationEntity.builder()
                .customerName(request.getCustomerName())
                .reservationDate(date)
                .reservationTime(time)
                .membersCount(request.getMembersCount())
                .tableNumber(request.getTableNumber())
                .status(ReservationStatus.ACTIVE)
                .build();
    }

    public ReservationResponse toDto(ReservationEntity reservationEntity) {
        return new ReservationResponse(
                reservationEntity.getId(),
                reservationEntity.getCustomerName(),
                reservationEntity.getMembersCount(),
                reservationEntity.getReservationDate(),
                reservationEntity.getReservationTime(),
                reservationEntity.getStatus(),
                reservationEntity.getTableNumber()
        );
    }
}
