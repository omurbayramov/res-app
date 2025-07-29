package com.example.reservation_application.repository;

import com.example.reservation_application.model.ReservationEntity;
import com.example.reservation_application.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    List<ReservationEntity> findByStatusAndReservationDate(ReservationStatus status, String reservationDate);

    boolean existsByTableNumberAndReservationDateAndReservationTime(
            int tableNumber,
            String reservationDate,
            String reservationTime
    );
}