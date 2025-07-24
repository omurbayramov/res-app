package com.example.reservation_application.service;

import com.example.reservation_application.model.Reservation;
import com.example.reservation_application.model.reservationStatus;
import com.example.reservation_application.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> findById(Long id) {
        return reservationRepository.findById(id);
    }

    public Reservation createReservation(Reservation reservation) {
        if(!isTableAvailable(reservation)) {
            throw new IllegalArgumentException("This table is already reserved at this time.");
        }
        return reservationRepository.save(reservation);
    }

    public Reservation updateReservation(Long id, Reservation updatedReservation) {
        return reservationRepository.findById(id).map(reservation -> {
            reservation.setTable_number(updatedReservation.getTable_number());
            reservation.setCustomer_name(updatedReservation.getCustomer_name());
            reservation.setReservation_date(updatedReservation.getReservation_date());
            reservation.setReservation_time(updatedReservation.getReservation_time());
            reservation.setMembers_count(updatedReservation.getMembers_count());
            reservation.setStatus(updatedReservation.getStatus());
            return  reservationRepository.save(reservation);
        }).orElse(null);
    }

    public Optional<Reservation> setActiveStatus(Long id, reservationStatus reservationStatus) {
        return reservationRepository.findById(id).map(reservation -> {
                    reservation.setStatus(reservationStatus.ACTIVE);
                    return reservationRepository.save(reservation);
                }
        );
    }

    public Optional<Reservation> setInactiveStatus(Long id, reservationStatus reservationStatus) {
        return reservationRepository.findById(id).map(reservation -> {
                    reservation.setStatus(reservationStatus.INACTIVE);
                    return reservationRepository.save(reservation);
                }
        );
    }

    public boolean isTableAvailable(Reservation reservation) {
        if (reservationRepository.existsByTableNumberAndReservationDateAndReservationTime(
                reservation.getTable_number(),
                reservation.getReservation_date(),
                reservation.getReservation_time()))
        {
            return false;
        }
        else {
            return true;
        }
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public List<Reservation> findReservationByStatus(reservationStatus status) {
        return reservationRepository.findByStatus(reservationStatus.ACTIVE);
    }
}
