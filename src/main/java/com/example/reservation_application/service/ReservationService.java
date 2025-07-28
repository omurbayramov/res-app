package com.example.reservation_application.service;

import com.example.reservation_application.model.response.ReservationResponse;
import com.example.reservation_application.model.Reservation;
import com.example.reservation_application.model.ReservationStatus;
import com.example.reservation_application.model.response.ReservationResponse;
import com.example.reservation_application.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponse> findAll() {
        log.info("Fetching all reservations");
        return reservationRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public Optional<ReservationResponse> findById(Long id) {
        Optional<Reservation> reservationOpt = reservationRepository.findById(id);

        if (reservationOpt.isEmpty()) {
            log.warn("No reservation found with id: {}", id);
            return Optional.empty();
        }

        return reservationOpt.map(this::mapToDto);
    }

    public ReservationResponse createReservation(Reservation reservation) {
        if (!isTableAvailable(reservation)) {
                log.warn("Reservation conflict: table {}, date={}, time: {}",
                    reservation.getTable_number(),
                    reservation.getReservation_date(),
                    reservation.getReservation_time());
            throw new IllegalArgumentException("This table is already reserved at this time.");
        }
        Reservation saved = reservationRepository.save(reservation);
        return mapToDto(saved);
    }

    public Reservation updateReservation(Long id, Reservation updatedReservation) {
        return reservationRepository.findById(id).map(reservation -> {
            reservation.setTable_number(updatedReservation.getTable_number());
            reservation.setCustomer_name(updatedReservation.getCustomer_name());
            reservation.setReservation_date(updatedReservation.getReservation_date());
            reservation.setReservation_time(updatedReservation.getReservation_time());
            reservation.setMembers_count(updatedReservation.getMembers_count());
            reservation.setStatus(updatedReservation.getStatus());
            log.info("Reservation with the id: {} was updated successfully", id);
            return  reservationRepository.save(reservation);
        }).orElse(null);
    }

    public Optional<ReservationResponse> setActiveStatus(Long id, ReservationStatus reservationStatus) {
        return reservationRepository.findById(id).map(reservation -> {
            reservation.setStatus(reservationStatus.ACTIVE);
            log.info("Status of the reservation with id: {} was set to ACTIVE", id);
            return mapToDto(reservationRepository.save(reservation));
        });
    }

    public Optional<ReservationResponse> setInactiveStatus(Long id, ReservationStatus reservationStatus) {
        return reservationRepository.findById(id).map(reservation -> {
            reservation.setStatus(reservationStatus.INACTIVE);
            log.info("Status of the reservation with id: {} was set to INACTIVE", id);
            return mapToDto(reservationRepository.save(reservation));
        });
    }

    public boolean isTableAvailable(Reservation reservation) {
        return !reservationRepository.existsByTableNumberAndReservationDateAndReservationTime(
                reservation.getTable_number(),
                reservation.getReservation_date(),
                reservation.getReservation_time());
    }

    public void deleteReservation(Long id) {
        log.info("Reservation with the id: {} was deleted successfully", id);
        reservationRepository.deleteById(id);
    }

    public List<ReservationResponse> findActiveReservationsByDate(String date) {
        return reservationRepository
                .findByStatusAndReservationDate(ReservationStatus.ACTIVE, date)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ReservationResponse mapToDto(Reservation reservation) {
        ReservationResponse dto = new ReservationResponse();
        dto.setId(reservation.getId());
        dto.setCustomerName(reservation.getCustomer_name());
        dto.setReservationDate(reservation.getReservation_date());
        dto.setReservationTime(reservation.getReservation_time());
        dto.setMembersCount(reservation.getMembers_count());
        dto.setTableNumber(reservation.getTable_number());
        dto.setStatus(reservation.getStatus());
        return dto;

    }
}
