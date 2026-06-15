package org.example.bookingsystem.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingDto {
    private String workDescription;
    private LocalDateTime bookingDateTime;
}
