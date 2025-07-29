package com.example.reservation_application.controller;

import com.example.reservation_application.model.response.ReservationResponse;
import com.example.reservation_application.model.ReservationEntity;
import com.example.reservation_application.model.ReservationStatus;
import com.example.reservation_application.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/{id}")
    public Optional<ReservationResponse> getReservationById(@PathVariable Long id) {
        return reservationService.findById(id);
    }

    @GetMapping("/active")
    public List<ReservationResponse> getActiveReservationsByDate(@RequestParam("date") String date) {
        return reservationService.findActiveReservationsByDate(date);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationEntity reservationEntity) {
        return ResponseEntity.ok(reservationService.createReservation(reservationEntity));
    }

    @PutMapping("/setInactive/{id}")
    public Optional<ReservationResponse> setInactiveStatus(@PathVariable Long id) {
        return reservationService.setInactiveStatus(id, ReservationStatus.INACTIVE);
    }

}
