package org.example.bookingsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "clients")
public class Client extends User{

    private String phone;

    @OneToMany(mappedBy = "client")
    private List<Booking> bookings;

}
