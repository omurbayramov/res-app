package com.example.reservation_application.controller;

import com.example.reservation_application.model.ReservationEntity;
import com.example.reservation_application.model.response.ReservationResponse;
import com.example.reservation_application.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
        return reservationService.setInactiveStatus(id);
    }

}
