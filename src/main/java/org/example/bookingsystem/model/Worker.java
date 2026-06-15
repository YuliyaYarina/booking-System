package org.example.bookingsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "workers")
public class Worker extends User{

    private String specialization;

    @OneToMany(mappedBy = "worker")
    private List<Booking> bookings;

    @OneToMany(mappedBy = "worker")
    private List<Slot> slots;
}
