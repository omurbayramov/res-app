package com.example.reservation_application.service;

import com.example.reservation_application.exception.AlreadyExistsException;
import com.example.reservation_application.exception.NotFoundException;
import com.example.reservation_application.exception.ErrorMessage;
import com.example.reservation_application.model.request.ReservationRequest;
import com.example.reservation_application.model.ReservationEntity;
import com.example.reservation_application.model.ReservationStatus;
import com.example.reservation_application.model.response.ReservationResponse;
import com.example.reservation_application.repository.ReservationRepository;
import com.example.reservation_application.mapper.ReservationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public Optional<ReservationResponse> findById(Long id) {
        return reservationRepository.findById(id)
                .map(reservationMapper::toDto)
                .or(() -> {
                    log.warn("No reservation found with id: {}", id);
                    throw new NotFoundException(ErrorMessage.RESERVATION_NOT_FOUND.getCode());
                });
    }

    public ReservationResponse createReservation(ReservationRequest request) {
        ReservationEntity reservationEntity = reservationMapper.toEntity(request);

        if (!isTableAvailable(reservationEntity)) {
            log.warn("ReservationEntity conflict: table {}, date={}, time: {}",
                    reservationEntity.getTableNumber(),
                    reservationEntity.getReservationDate(),
                    reservationEntity.getReservationTime());

            throw new AlreadyExistsException(ErrorMessage.TABLE_ALREADY_RESERVED.getCode());
        }

        return reservationMapper.toDto(reservationRepository.save(reservationEntity));
    }

    public Optional<ReservationResponse> setInactiveStatus(Long id) {
        return reservationRepository.findById(id)
                .map(reservationEntity -> {
                    reservationEntity.setStatus(ReservationStatus.INACTIVE);
                    log.info("Status of the reservationEntity with id: {} was set to INACTIVE", id);
                    return reservationMapper.toDto(reservationRepository.save(reservationEntity));
                })
                .or(() -> {
                    log.warn("Cannot set status. Reservation not found with id: {}", id);
                    throw new NotFoundException(ErrorMessage.RESERVATION_NOT_FOUND.getCode());
                });
    }

    public boolean isTableAvailable(ReservationEntity reservationEntity) {
        return !reservationRepository.existsByTableNumberAndReservationDateAndReservationTime(
                reservationEntity.getTableNumber(),
                reservationEntity.getReservationDate(),
                reservationEntity.getReservationTime());
    }

    public List<ReservationResponse> findActiveReservationsByDate(LocalDate date) {
        return reservationRepository
                .findByStatusAndReservationDate(ReservationStatus.ACTIVE, date)
                .stream()
                .map(reservationMapper::toDto)
                .collect(Collectors.toList());
    }
}
