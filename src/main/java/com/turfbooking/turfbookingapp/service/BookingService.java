package com.turfbooking.turfbookingapp.service;

import com.turfbooking.turfbookingapp.exception.BadRequestException;
import com.turfbooking.turfbookingapp.exception.BookingNotFoundException;
import com.turfbooking.turfbookingapp.model.Booking;
import com.turfbooking.turfbookingapp.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // Create a booking with validation
    public Booking createBooking(Booking booking) {

        // 1️⃣ Null checks
        if (booking.getTurfId() == null ||
                booking.getBookingDate() == null ||
                booking.getStartTime() == null ||
                booking.getEndTime() == null) {
            throw new BadRequestException("All booking fields are required");
        }

        // 2️⃣ Time validation
        if (!booking.getStartTime().isBefore(booking.getEndTime())) {
            throw new BadRequestException("Start time must be before end time");
        }

        // 3️⃣ Date validation
        if (booking.getBookingDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Booking date cannot be in the past");
        }

        // 4️⃣ Overlap check
        boolean overlap = bookingRepository
                .existsByTurfIdAndBookingDateAndStartTimeLessThanAndEndTimeGreaterThan(
                        booking.getTurfId(),
                        booking.getBookingDate(),
                        booking.getEndTime(),   // existing.start < new.end
                        booking.getStartTime()  // existing.end > new.start
                );

        if (overlap) {
            throw new BadRequestException("Time slot already booked");
        }

        booking.setStatus("CONFIRMED");
        return bookingRepository.save(booking);
    }

    // Get bookings by turf and date
    public List<Booking> getBookingsByTurfAndDate(Long turfId, LocalDate bookingDate) {
        return bookingRepository.findByTurfIdAndBookingDate(turfId, bookingDate);
    }

    // Update/reschedule a booking
    public Booking updateBooking(Long bookingId, Booking updatedBooking) {

        Booking existing = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + bookingId));

        // 1️⃣ Null checks
        if (updatedBooking.getTurfId() == null ||
                updatedBooking.getBookingDate() == null ||
                updatedBooking.getStartTime() == null ||
                updatedBooking.getEndTime() == null) {
            throw new BadRequestException("All booking fields are required");
        }

        // 2️⃣ Time validation
        if (!updatedBooking.getStartTime().isBefore(updatedBooking.getEndTime())) {
            throw new BadRequestException("Start time must be before end time");
        }

        // 3️⃣ Date validation
        if (updatedBooking.getBookingDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Booking date cannot be in the past");
        }

        // 4️⃣ Overlap check (exclude current booking)
        boolean overlap = bookingRepository.existsByTurfIdAndBookingDateAndStartTimeLessThanAndEndTimeGreaterThan(
                updatedBooking.getTurfId(),
                updatedBooking.getBookingDate(),
                updatedBooking.getEndTime(),
                updatedBooking.getStartTime()
        );

        if (overlap && !existing.getId().equals(bookingId)) {
            throw new BadRequestException("Time slot already booked");
        }

        // Update fields
        existing.setTurfId(updatedBooking.getTurfId());
        existing.setBookingDate(updatedBooking.getBookingDate());
        existing.setStartTime(updatedBooking.getStartTime());
        existing.setEndTime(updatedBooking.getEndTime());
        existing.setStatus("CONFIRMED");

        return bookingRepository.save(existing);
    }

    // Delete/cancel a booking
    public void deleteBooking(Long bookingId) {
        Booking existing = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + bookingId));

        bookingRepository.delete(existing);
    }

    // Get all bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }


}
