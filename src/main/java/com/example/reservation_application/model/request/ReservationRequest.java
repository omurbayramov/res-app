package com.example.reservation_application.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReservationRequest {

    @Min(value = 1, message = "validation.membersCount.min")
    private Integer membersCount;

    @NotBlank(message = "validation.customerName.blank")
    @Size(min = 3, max = 30, message = "validation.customerName.size")
    private String customerName;

    @NotBlank(message = "validation.reservationDate.required")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "validation.reservationDate.format")
    private String reservationDate;

    @NotBlank(message = "validation.reservationTime.required")
    @Pattern(regexp = "([01]\\d|2[0-3]):[0-5]\\d", message = "validation.reservationTime.format")
    private String reservationTime;

    @Min(value = 1, message = "validation.tableNumber.min")
    @Max(value = 12, message = "validation.tableNumber.max")
    private Integer tableNumber;
}
