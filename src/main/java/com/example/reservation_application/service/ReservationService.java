package com.example.reservation_application.service;

import com.example.reservation_application.model.ReservationEntity;
import com.example.reservation_application.model.response.ReservationResponse;
import com.example.reservation_application.model.ReservationStatus;
import com.example.reservation_application.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public Optional<ReservationResponse> findById(Long id) {
        Optional<ReservationEntity> reservationOpt = reservationRepository.findById(id);

        if (reservationOpt.isEmpty()) {
            log.warn("No reservation found with id: {}", id);
            return Optional.empty();
        }

        return reservationOpt.map(this::mapToDto);
    }

    public ReservationResponse createReservation(ReservationEntity reservationEntity) {
        if (!isTableAvailable(reservationEntity)) {
                log.warn("ReservationEntity conflict: table {}, date={}, time: {}",
                    reservationEntity.getTableNumber(),
                    reservationEntity.getReservationDate(),
                    reservationEntity.getReservationTime());
            throw new IllegalArgumentException("This table is already reserved at this time.");
        }
        ReservationEntity saved = reservationRepository.save(reservationEntity);
        return mapToDto(saved);
    }

    public Optional<ReservationResponse> setInactiveStatus(Long id) {
        return reservationRepository.findById(id).map(reservationEntity -> {
            reservationEntity.setStatus(ReservationStatus.INACTIVE);
            log.info("Status of the reservationEntity with id: {} was set to INACTIVE", id);
            return mapToDto(reservationRepository.save(reservationEntity));
        });
    }


    public boolean isTableAvailable(ReservationEntity reservationEntity) {
        return !reservationRepository.existsByTableNumberAndReservationDateAndReservationTime(
                reservationEntity.getTableNumber(),
                reservationEntity.getReservationDate(),
                reservationEntity.getReservationTime());
    }

    public List<ReservationResponse> findActiveReservationsByDate(String date) {
        return reservationRepository
                .findByStatusAndReservationDate(ReservationStatus.ACTIVE, date)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ReservationResponse mapToDto(ReservationEntity reservationEntity) {
        ReservationResponse dto = new ReservationResponse();
        dto.setId(reservationEntity.getId());
        dto.setCustomerName(reservationEntity.getCustomerName());
        dto.setReservationDate(reservationEntity.getReservationDate());
        dto.setReservationTime(reservationEntity.getReservationTime());
        dto.setMembersCount(reservationEntity.getMembersCount());
        dto.setTableNumber(reservationEntity.getTableNumber());
        dto.setStatus(reservationEntity.getStatus());
        return dto;

    }
}
