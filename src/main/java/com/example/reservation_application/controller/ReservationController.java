package com.example.reservation_application.controller;

import com.example.reservation_application.model.response.ReservationResponse;
import com.example.reservation_application.model.Reservation;
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

    @GetMapping
    public List<ReservationResponse> getAllReservations() {
        return reservationService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<ReservationResponse> getReservationById(@PathVariable Long id) {
        return reservationService.findById(id);
    }

    @GetMapping("/active")
    public List<ReservationResponse> getActiveReservationsByDate(@RequestParam("date") String date) {
        return reservationService.findActiveReservationsByDate(date);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody Reservation reservation) {
        return ResponseEntity.ok(reservationService.createReservation(reservation));
    }

    @PutMapping("/{id}")
    public Reservation updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        return reservationService.updateReservation(id, reservation);
    }

    @PutMapping("/setActive/{id}")
    public Optional<ReservationResponse> setActiveStatus(@PathVariable Long id) {
        return reservationService.setActiveStatus(id, ReservationStatus.ACTIVE);
    }

    @PutMapping("/setInactive/{id}")
    public Optional<ReservationResponse> setInactiveStatus(@PathVariable Long id) {
        return reservationService.setInactiveStatus(id, ReservationStatus.INACTIVE);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
    }
}
