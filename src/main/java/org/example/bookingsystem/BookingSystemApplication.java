package org.example.bookingsystem;

import org.example.bookingsystem.model.Role;
import org.example.bookingsystem.model.Worker;
import org.example.bookingsystem.service.impl.WorkerServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@SpringBootApplication
public class BookingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookingSystemApplication.class, args);
    }

    @Bean
    CommandLineRunner init(WorkerServiceImpl userService, PasswordEncoder passwordEncoder) {
        return args -> {
            String userName = "Admin";

            if(userService.findByUsername(userName).isEmpty()){
                Worker worker = new Worker();
                worker.setUsername(userName);
                worker.setName("Stas");
                worker.setRoles(Collections.singleton(Role.ADMIN));
                worker.setPassword(passwordEncoder.encode("0000"));
                worker.setSpecialization("Admin");
                userService.save(worker);
            }

        };
    }
//
//    @Bean
//    CommandLineRunner init(ClientService clientService, PasswordEncoder passwordEncoder) {
//        return args -> {
//            String userName = "admin";
//                Client worker = new Client();
////                worker.setUsername(userName);
////                worker.setName("Anna");
////                worker.setRoles(Collections.singleton(Role.ADMIN));
////                worker.setPassword(passwordEncoder.encode("0000"));
////                worker.setSpecialization("Администратор");
//                clientService.save(worker);
//        };
//    }

}
