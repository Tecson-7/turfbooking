package com.turfbooking.turfbookingapp.repository;

import com.turfbooking.turfbookingapp.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // find bookings for a turf on a specific date
    List<Booking> findByTurfIdAndBookingDate(Long turfId, LocalDate bookingDate);

    // optional: check overlapping bookings later
    boolean existsByTurfIdAndBookingDateAndStartTimeLessThanAndEndTimeGreaterThan(
            Long turfId,
            LocalDate bookingDate,
            LocalTime endTime,
            LocalTime startTime
    );
}
