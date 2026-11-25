package edu.course.booking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookingRequestTest {

    @Test
    void shouldCreateRequestWhenAllValuesAreValid() {
        BookingRequest request = new BookingRequest(
                "C101",
                "E201",
                3,
                2500
        );

        assertEquals("C101", request.getCustomerId());
        assertEquals("E201", request.getEventId());
        assertEquals(3, request.getTicketQuantity());
        assertEquals(2500, request.getPricePerTicketInCents());
    }

    @Test
    void shouldRejectNullCustomerId() {
        assertThrows(IllegalArgumentException.class, () ->
                new BookingRequest(null, "E201", 2, 2500));
    }

    @Test
    void shouldRejectBlankCustomerId() {
        assertThrows(IllegalArgumentException.class, () ->
                new BookingRequest("   ", "E201", 2, 2500));
    }

    @Test
    void shouldRejectEmptyCustomerId() {
        assertThrows(IllegalArgumentException.class, () ->
                new BookingRequest("", "E201", 2, 2500));
    }

    @Test
    void shouldRejectNullEventId() {
        assertThrows(IllegalArgumentException.class, () ->
                new BookingRequest("C101", null, 2, 2500));
    }

    @Test
    void shouldRejectBlankEventId() {
        assertThrows(IllegalArgumentException.class, () ->
                new BookingRequest("C101", "   ", 2, 2500));
    }

    @Test
    void shouldRejectEmptyEventId() {
        assertThrows(IllegalArgumentException.class, () ->
                new BookingRequest("C101", "", 2, 2500));
    }

    @Test
    void shouldRejectZeroTicketQuantity() {
        assertThrows(IllegalArgumentException.class, () ->
                new BookingRequest("C101", "E201", 0, 2500));
    }

    @Test
    void shouldRejectMoreThanFiveTickets() {
        assertThrows(IllegalArgumentException.class, () ->
                new BookingRequest("C101", "E201", 6, 2500));
    }

    @Test
    void shouldRejectZeroPrice() {
        assertThrows(IllegalArgumentException.class, () ->
                new BookingRequest("C101", "E201", 2, 0));
    }

    @Test
    void shouldRejectNegativePrice() {
        assertThrows(IllegalArgumentException.class, () ->
                new BookingRequest("C101", "E201", 2, -100));
    }

    @Test
    void shouldAcceptMinimumTicketQuantity() {
        BookingRequest request = new BookingRequest(
                "C101", "E201", 1, 2500);

        assertEquals(1, request.getTicketQuantity());
    }

    @Test
    void shouldAcceptMaximumTicketQuantity() {
        BookingRequest request = new BookingRequest(
                "C101", "E201", 5, 2500);

        assertEquals(5, request.getTicketQuantity());
    }
}