package com.example.reservation_application.service;

import com.example.reservation_application.model.ReservationEntity;
import com.example.reservation_application.model.ReservationStatus;
import com.example.reservation_application.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationScheduler {

    private final ReservationRepository reservationRepository;

    @Scheduled(fixedRate = 30000)
    public void schedulerReservationsCheck(){
        List<ReservationEntity> activeReservations = reservationRepository.findByStatus(ReservationStatus.ACTIVE);
        LocalDateTime now = LocalDateTime.now();

        for(ReservationEntity reservation : activeReservations){
            try{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'H:mm");
                String dateTimeString = reservation.getReservationDate() + "T" + reservation.getReservationTime();
                LocalDateTime reservationTime = LocalDateTime.parse(dateTimeString, formatter);

                if (reservationTime.plusHours(2).isBefore(now)) {
                    reservation.setStatus(ReservationStatus.INACTIVE);
                    reservationRepository.save(reservation);
                    log.info("Reservation ID {} set to INACTIVE (expired)", reservation.getId());
                }

            } catch (Exception e){
                log.warn("Failed to parse reservation date/time for ID {}: {}", reservation.getId(), e.getMessage());
            }
        }
    }
}
