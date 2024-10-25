package com.example.demo.api;

import com.example.demo.entity.Booking;
import com.example.demo.entity.Category;
import com.example.demo.model.BookingRequest;
import com.example.demo.model.BookingRequest2;
import com.example.demo.service.BookingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@SecurityRequirement(name = "api")
public class BookingAPI {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/getBooking")
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long bookingId) {
        Booking booking = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(booking);
    }

    @PostMapping("/createBooking")
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody BookingRequest booking) {
        Booking newBooking = bookingService.createBooking(booking);
        return ResponseEntity.ok(newBooking);
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long bookingId, @Valid @RequestBody Booking booking) {
        Booking updatedBooking = bookingService.updateBooking(bookingId, booking);
        return ResponseEntity.ok(updatedBooking);
    }
    @PutMapping("/{bookingId}/status")
    public ResponseEntity<Booking> updateStatus(@PathVariable Long bookingId, @RequestBody String newStatus) {
        Booking updatedBooking = bookingService.updateBookingStatus(bookingId, newStatus);
        return ResponseEntity.ok(updatedBooking);
    }

    @PutMapping("/{bookingId}/stylist")
    public ResponseEntity<Booking> updateStylist(@PathVariable Long bookingId, @Valid @RequestBody BookingRequest2 booking) {
        Booking updatedBooking = bookingService.updateStylistId(bookingId, booking);
        return ResponseEntity.ok(updatedBooking);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Booking> deleteCategory(@PathVariable long id) {
        Booking category = bookingService.deleteBooking(id);
        return ResponseEntity.ok(category);
    }
}
