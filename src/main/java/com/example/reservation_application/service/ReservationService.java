package com.example.reservation_application.service;

import com.example.reservation_application.exception.AlreadyExistsException;
import com.example.reservation_application.exception.NotFoundException;
import com.example.reservation_application.exception.ErrorMessage;
import com.example.reservation_application.model.ReservationEntity;
import com.example.reservation_application.model.ReservationStatus;
import com.example.reservation_application.model.response.ReservationResponse;
import com.example.reservation_application.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
            throw new NotFoundException(ErrorMessage.RESERVATION_NOT_FOUND.getCode());
        }

        return reservationOpt.map(this::mapToDto);
    }

    public ReservationResponse createReservation(ReservationEntity reservationEntity) {


        if (!isTableAvailable(reservationEntity)) {
            log.warn("ReservationEntity conflict: table {}, date={}, time: {}",
                    reservationEntity.getTableNumber(),
                    reservationEntity.getReservationDate(),
                    reservationEntity.getReservationTime());

            throw new AlreadyExistsException(ErrorMessage.TABLE_ALREADY_RESERVED.getCode());
        }
        ReservationEntity saved = reservationRepository.save(reservationEntity);
        return mapToDto(saved);
    }

    public Optional<ReservationResponse> setInactiveStatus(Long id) {

        Optional<ReservationEntity> reservationOpt = reservationRepository.findById(id);

        if (reservationOpt.isEmpty()) {
            log.warn("Cannot set status. Reservation not found with id: {}", id);
            throw new NotFoundException(ErrorMessage.RESERVATION_NOT_FOUND.getCode());
        }

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
