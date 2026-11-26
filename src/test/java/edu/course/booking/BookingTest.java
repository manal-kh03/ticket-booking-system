
package edu.course.booking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookingTest {

    @Test
    void shouldCreateBookingSuccessfully() {

        // Arrange
        BookingRequest request = new BookingRequest(
                "C101",
                "E201",
                3,
                2500
        );

        // Act
        Booking booking = new Booking(request, "TXN-123");

        // Assert
        assertEquals("C101", booking.getCustomerId());
        assertEquals("E201", booking.getEventId());
        assertEquals(3, booking.getTicketQuantity());
        assertEquals(2500, booking.getPricePerTicketInCents());
        assertEquals(7500, booking.getTotalAmountInCents());
        assertEquals("TXN-123", booking.getTransactionId());
    }

    @Test
    void shouldRejectNullBookingRequest() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Booking(null, "TXN-123")
        );
    }

    @Test
    void shouldRejectNullTransactionId() {

        BookingRequest request = new BookingRequest(
                "C101",
                "E201",
                2,
                2500
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new Booking(request, null)
        );
    }

    @Test
    void shouldRejectBlankTransactionId() {

        BookingRequest request = new BookingRequest(
                "C101",
                "E201",
                2,
                2500
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new Booking(request, "   ")
        );
    }
}
