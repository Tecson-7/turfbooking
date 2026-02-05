package com.turfbooking.turfbookingapp.controller;

import com.turfbooking.turfbookingapp.model.Booking;
import com.turfbooking.turfbookingapp.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Create a booking
    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.createBooking(booking);
    }

    // Get bookings by turf and date
    @GetMapping
    public List<Booking> getBookings(
            @RequestParam Long turfId,
            @RequestParam LocalDate bookingDate
    ) {
        return bookingService.getBookingsByTurfAndDate(turfId, bookingDate);
    }
    // Update booking
    @PutMapping("/{bookingId}")
    public Booking updateBooking(@PathVariable Long bookingId, @RequestBody Booking updatedBooking) {
        return bookingService.updateBooking(bookingId, updatedBooking);
    }

    // Delete booking
    @DeleteMapping("/{bookingId}")
    public void deleteBooking(@PathVariable Long bookingId) {
        bookingService.deleteBooking(bookingId);
    }

    // Get all bookings
    @GetMapping("/all")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }


}
