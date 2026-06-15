package org.example.bookingsystem.security;

import org.example.bookingsystem.model.Worker;
import org.example.bookingsystem.service.WorkerService;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final WorkerService workerService;

    public CustomUserDetailsService(WorkerService workerService) {
        this.workerService = workerService;
    }


    @NonNull
    @Override
    public UserDetails loadUserByUsername(@NonNull String username){
//
        Worker worker = workerService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Set<SimpleGrantedAuthority> authorities = worker.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toSet());

        return org.springframework.security.core.userdetails.User
                .withUsername(worker.getUsername())
                .password(worker.getPassword())
                .authorities(authorities)
                .build();
    }
}
