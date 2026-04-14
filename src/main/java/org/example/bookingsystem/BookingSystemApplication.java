package org.example.bookingsystem;

import org.example.bookingsystem.model.Booking;
import org.example.bookingsystem.model.Role;
import org.example.bookingsystem.model.User;
import org.example.bookingsystem.repository.BookingRepository;
import org.example.bookingsystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collections;


@SpringBootApplication
public class BookingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookingSystemApplication.class, args);
    }

//    @Bean
//    CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        return args -> {
//            if (userRepository.findByUsername("user33").isEmpty()) {
//                User user = new User();
//                user.setUsername("user33");
//                user.setRoles(Collections.singleton(Role.valueOf("USER")));
//                user.setPassword(passwordEncoder.encode("0001"));
//                userRepository.save(user);
//            }
//        };
//    }
}
