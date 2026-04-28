package org.example.bookingsystem.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class BookingDto {
    private String clientName;
    private String phone;
    private String workDescription;
    private LocalDateTime bookingTime;
}
