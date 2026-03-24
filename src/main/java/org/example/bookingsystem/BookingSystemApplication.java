package org.example.bookingsystem;

import org.example.bookingsystem.model.Booking;
import org.example.bookingsystem.repository.BookingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class BookingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookingSystemApplication.class, args);
    }

//    @Bean
//    CommandLineRunner run(BookingRepository repo) {
//        return args -> {
//            Booking  booking = new Booking();
//            booking.setClientName("Anna");
//            booking.setPhone("12345678");
//            booking.setBookingDate(LocalDateTime.now());
//
//            repo.save(booking);
//        };
//    }

}
