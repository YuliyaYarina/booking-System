package org.example.bookingsystem;

import org.example.bookingsystem.model.Booking;
import org.example.bookingsystem.model.User;
import org.example.bookingsystem.repository.BookingRepository;
import org.example.bookingsystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;


@SpringBootApplication
public class BookingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookingSystemApplication.class, args);
    }
//
//
//    @Bean
//    CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        return args -> {
//            if (userRepository.findByUsername("admin").isEmpty()) {
//                User user = new User();
//                user.setUsername("admin");
//                user.setPassword(passwordEncoder.encode("1234"));
//                userRepository.save(user);
//            }
//        };
//    }

}
