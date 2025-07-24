package com.example.reservation_application.controller;

import com.example.reservation_application.model.Reservation;
import com.example.reservation_application.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.reservation_application.model.reservationStatus;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Reservation> getReservationById(@PathVariable Long id) {
        return reservationService.findById(id);
    }

    @GetMapping("/active")
    public List<Reservation> getActiveReservation() {
        return reservationService.findReservationByStatus(reservationStatus.ACTIVE);
    }

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody Reservation reservation) {
        try {
            return ResponseEntity.ok(reservationService.createReservation(reservation));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Reservation updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        return reservationService.updateReservation(id, reservation);
    }

    @PutMapping("/setActive/{id}")
    public Optional<Reservation> setActiveStatus(@PathVariable Long id) {
        return reservationService.setActiveStatus(id, reservationStatus.ACTIVE);
    }

    @PutMapping("/setInactive/{id}")
    public Optional<Reservation> setInactiveStatus(@PathVariable Long id) {
        return reservationService.setInactiveStatus(id, reservationStatus.INACTIVE);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
    }
}
