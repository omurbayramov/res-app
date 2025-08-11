package com.example.reservation_application.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ReservationRequest {

    @Min(value = 1, message = "validation.membersCount.min")
    private Integer membersCount;

    @NotBlank(message = "validation.customerName.blank")
    @Size(min = 3, max = 30, message = "validation.customerName.size")
    private String customerName;

    @NotNull(message = "validation.reservationDate.required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationDate;

    @NotNull(message = "validation.reservationTime.required")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime reservationTime;

    @Min(value = 1, message = "validation.tableNumber.min")
    @Max(value = 12, message = "validation.tableNumber.max")
    private Integer tableNumber;
}
