package edu.course.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingManagerTest {

    @Mock
    private IPaymentGateway paymentGateway;

    @Mock
    private INotificationService notificationService;

    @Mock
    private IEventRepository eventRepository;

    private BookingManager bookingManager;

    @BeforeEach
    void setUp() {
        bookingManager = new BookingManager(
                paymentGateway,
                notificationService,
                eventRepository
        );
    }

    @Test
    void shouldCompleteBookingSuccessfully() throws Exception {

        BookingRequest request = new BookingRequest(
                "C101",
                "E201",
                3,
                2500
        );

        when(eventRepository.isSoldOut("E201"))
                .thenReturn(false);

        when(paymentGateway.processPayment(7500))
                .thenReturn("TXN-123");

        BookingResult result = bookingManager.book(request);

        assertNotNull(result);
        assertEquals(
                BookingResult.Status.SUCCESS,
                result.getStatus()
        );
        assertEquals("TXN-123", result.getTransactionId());

        verify(eventRepository, times(1))
                .isSoldOut("E201");

        verify(paymentGateway, times(1))
                .processPayment(7500);

        ArgumentCaptor<Booking> bookingCaptor =
                ArgumentCaptor.forClass(Booking.class);

        verify(eventRepository, times(1))
                .saveBooking(bookingCaptor.capture());

        verify(notificationService, times(1))
                .sendConfirmation(any(Booking.class));

        Booking savedBooking = bookingCaptor.getValue();

        assertEquals("C101", savedBooking.getCustomerId());
        assertEquals("E201", savedBooking.getEventId());
        assertEquals(3, savedBooking.getTicketQuantity());
        assertEquals(7500, savedBooking.getTotalAmountInCents());
        assertEquals("TXN-123", savedBooking.getTransactionId());

        verifyNoMoreInteractions(
                paymentGateway,
                notificationService,
                eventRepository
        );
    }
}