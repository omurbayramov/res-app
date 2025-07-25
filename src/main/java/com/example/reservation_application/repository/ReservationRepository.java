package com.example.reservation_application.repository;

import com.example.reservation_application.model.Reservation;
import com.example.reservation_application.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Long id(Long id);

    List<Reservation> findByStatus(ReservationStatus status);
    List<Reservation> findByStatusAndReservationDate(ReservationStatus status, String reservationDate);

    boolean existsByTableNumberAndReservationDateAndReservationTime(
            int tableNumber,
            String reservationDate,
            String reservationTime
    );
}