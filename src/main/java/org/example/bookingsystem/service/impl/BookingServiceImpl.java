package org.example.bookingsystem.service.impl;

import org.example.bookingsystem.model.Booking;

import org.example.bookingsystem.model.Role;
import org.example.bookingsystem.model.User;
import org.example.bookingsystem.repository.BookingRepository;
import org.example.bookingsystem.service.BookingService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository repository;

    public BookingServiceImpl(BookingRepository repository) {
        this.repository = repository;
    }

    @Override
    public Booking save(Booking booking) {
        return repository.save(booking);
    }

    @Override
    public Booking create(Booking booking, User user) {
        booking.setUser(user);
        return repository.save(booking);
    }

    @Override
    public List<Booking> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Booking> getAll(User user) {
        return repository.findByUser(user);
    }

    @Override
    public List<Booking> getByPhone(String phone) {
        return repository.findByPhone(phone);
    }

    @Override
    public List<Booking> getByDate(String start, String end) {
        return repository.findByBookingTimeBetween(
                LocalDateTime.parse(start),
                LocalDateTime.parse(end)
        );
    }

    @Override
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Booking> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<Booking> filterBookings(User currentUser, LocalDate bookingDay, Long masterId) {
        LocalDateTime dayStart = bookingDay == null ? null : bookingDay.atStartOfDay();
        LocalDateTime dayEnd = bookingDay == null ? null : bookingDay.plusDays(1).atStartOfDay();

        boolean isAdmin = currentUser.getRoles().contains(Role.ADMIN);

        if (isAdmin) {
            if (dayStart != null && masterId != null) {
                return repository.findByBookingTimeBetweenAndUserId(dayStart, dayEnd, masterId);
            }
            if (dayStart != null) {
                return repository.findByBookingTimeBetween(dayStart, dayEnd);
            }
            if (masterId != null) {
                return repository.findByUserId(masterId);
            }
            return repository.findAll();
        }

        if (dayStart != null) {
            return repository.findByBookingTimeBetweenAndUserId(dayStart, dayEnd, currentUser.getId());
        }
        return repository.findByUserId(currentUser.getId());
    }

    @Override
    public Booking findById(Long id) {
        return repository.findById(id)
                .orElseThrow();
    }
}
