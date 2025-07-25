package com.example.reservation_application.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
public class Reservation {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private int tableNumber;

    @NotBlank
    private String customerName;

    @NotBlank
    private String reservationDate;

    @NotBlank
    private String reservationTime;

    @NotBlank
    private int membersCount;

    @NotBlank
    private enum resStatus{
        ACTIVE,
        INACTIVE;
    }

    @Setter
    @Getter
    private ReservationStatus status;

    public int getTable_number() {
        return tableNumber;
    }

    public void setTable_number(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getCustomer_name() {
        return customerName;
    }

    public void setCustomer_name(String customerName) {
        this.customerName = customerName;
    }

    public String getReservation_date() {
        return reservationDate;
    }

    public void setReservation_date(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservation_time() {
        return reservationTime;
    }

    public void setReservation_time(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    public int getMembers_count() {
        return membersCount;
    }

    public void setMembers_count(int membersCount) {
        this.membersCount = membersCount;
    }

}