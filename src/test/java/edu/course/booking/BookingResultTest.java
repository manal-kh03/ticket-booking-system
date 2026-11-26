
package edu.course.booking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookingResultTest {

    @Test
    void shouldCreateSuccessfulBookingResult() {

        // Act
        BookingResult result = BookingResult.success("TXN-123");

        // Assert
        assertEquals(
                BookingResult.Status.SUCCESS,
                result.getStatus()
        );

        assertEquals("TXN-123", result.getTransactionId());
        assertTrue(result.isSuccessful());
    }

    @Test
    void shouldRejectNullTransactionId() {

        assertThrows(
                IllegalArgumentException.class,
                () -> BookingResult.success(null)
        );
    }

    @Test
    void shouldRejectBlankTransactionId() {

        assertThrows(
                IllegalArgumentException.class,
                () -> BookingResult.success("   ")
        );
    }
}
