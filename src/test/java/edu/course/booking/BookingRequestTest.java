
package edu.course.booking;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import static org.junit.jupiter.api.Assertions.*;

class BookingRequestTest {

    static Stream<Arguments> invalidRequests() {
        return Stream.of(
                Arguments.of(null, "E201", 2, 2500),
                Arguments.of("", "E201", 2, 2500),
                Arguments.of("   ", "E201", 2, 2500),

                Arguments.of("C101", null, 2, 2500),
                Arguments.of("C101", "", 2, 2500),
                Arguments.of("C101", "   ", 2, 2500),

                Arguments.of("C101", "E201", 0, 2500),
                Arguments.of("C101", "E201", 6, 2500),

                Arguments.of("C101", "E201", 2, 0),
                Arguments.of("C101", "E201", 2, -100)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidRequests")
    void shouldRejectInvalidBookingRequests(
            String customerId,
            String eventId,
            int ticketQuantity,
            int pricePerTicketInCents) {

        assertThrows(
                IllegalArgumentException.class,
                () -> new BookingRequest(
                        customerId,
                        eventId,
                        ticketQuantity,
                        pricePerTicketInCents
                )
        );
    }

    @Test
    void shouldCreateRequestWhenAllValuesAreValid() {
        BookingRequest request = new BookingRequest(
                "C101", "E201", 3, 2500
        );

        assertEquals("C101", request.getCustomerId());
        assertEquals("E201", request.getEventId());
        assertEquals(3, request.getTicketQuantity());
        assertEquals(2500, request.getPricePerTicketInCents());
    }

    @Test
    void shouldAcceptMinimumTicketQuantity() {
        BookingRequest request = new BookingRequest(
                "C101", "E201", 1, 2500
        );

        assertEquals(1, request.getTicketQuantity());
    }

    @Test
    void shouldAcceptMaximumTicketQuantity() {
        BookingRequest request = new BookingRequest(
                "C101", "E201", 5, 2500
        );

        assertEquals(5, request.getTicketQuantity());
    }
}
