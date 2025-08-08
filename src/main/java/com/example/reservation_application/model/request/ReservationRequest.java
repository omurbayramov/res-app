package com.example.reservation_application.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReservationRequest {

    @Min(value = 1, message = "validation.membersCount.min")
    private Integer membersCount;

    @NotBlank(message = "validation.customerName.blank")
    @Size(min = 3, max = 30, message = "validation.customerName.size")
    private String customerName;

    @NotBlank(message = "validation.reservationDate.blank")
    private String reservationDate; //change to local date., sue value of. delete database entries.

    @NotBlank(message = "validation.reservationTime.blank")
    private String reservationTime;

    @Min(value = 1, message = "validation.tableNumber.min")
    @Max(value = 12, message = "validation.tableNumber.max")
    private Integer tableNumber;
}
