package com.example.reservation_application.service;

import com.example.reservation_application.dto.ReservationDto;
import com.example.reservation_application.model.Reservation;
import com.example.reservation_application.model.ReservationStatus;
import com.example.reservation_application.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationDto> findAll() {
        return reservationRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public Optional<ReservationDto> findById(Long id) {
        return reservationRepository.findById(id).map(this::mapToDto);
    }

    public ReservationDto createReservation(Reservation reservation) {
        if (!isTableAvailable(reservation)) {
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
            return  reservationRepository.save(reservation);
        }).orElse(null);
    }

    public Optional<ReservationDto> setActiveStatus(Long id, ReservationStatus reservationStatus) {
        return reservationRepository.findById(id).map(reservation -> {
            reservation.setStatus(reservationStatus.ACTIVE);
            return mapToDto(reservationRepository.save(reservation));
        });
    }

    public Optional<ReservationDto> setInactiveStatus(Long id, ReservationStatus reservationStatus) {
        return reservationRepository.findById(id).map(reservation -> {
            reservation.setStatus(reservationStatus.INACTIVE);
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
        reservationRepository.deleteById(id);
    }

    public List<ReservationDto> findActiveReservationsByDate(String date) {
        return reservationRepository
                .findByStatusAndReservationDate(ReservationStatus.ACTIVE, date)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ReservationDto mapToDto(Reservation reservation) {
        ReservationDto dto = new ReservationDto();
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
