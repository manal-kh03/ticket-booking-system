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

        // Arrange
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

        // Act
        BookingResult result = bookingManager.book(request);

        // Assert
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

        Booking savedBooking = bookingCaptor.getValue();

        assertEquals("C101", savedBooking.getCustomerId());
        assertEquals("E201", savedBooking.getEventId());
        assertEquals(3, savedBooking.getTicketQuantity());
        assertEquals(7500, savedBooking.getTotalAmountInCents());
        assertEquals("TXN-123", savedBooking.getTransactionId());

        verify(notificationService, times(1))
                .sendConfirmation(savedBooking);

        verifyNoMoreInteractions(
                paymentGateway,
                notificationService,
                eventRepository
        );
    }

    @Test
    void shouldRejectNullRequestWithoutCallingDependencies() {

        // Act & Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> bookingManager.book(null)
        );

        verifyNoInteractions(
                paymentGateway,
                notificationService,
                eventRepository
        );
    }

    @Test
    void shouldReturnSoldOutWithoutProcessingPaymentOrSavingBooking()
            throws PaymentFailedException {

        // Arrange
        BookingRequest request = new BookingRequest(
                "C101",
                "E201",
                2,
                2500
        );

        when(eventRepository.isSoldOut("E201"))
                .thenReturn(true);

        // Act
        BookingResult result = bookingManager.book(request);

        // Assert
        assertNotNull(result);

        assertEquals(
                BookingResult.Status.SOLD_OUT,
                result.getStatus()
        );

        assertFalse(result.isSuccessful());
        assertNull(result.getTransactionId());

        verify(eventRepository, times(1))
                .isSoldOut("E201");

        verifyNoInteractions(
                paymentGateway,
                notificationService
        );

        verifyNoMoreInteractions(eventRepository);
    }

    @Test
    void shouldNotSaveBookingOrSendNotificationWhenPaymentFails()
            throws PaymentFailedException {

        // Arrange
        BookingRequest request = new BookingRequest(
                "C101",
                "E201",
                2,
                2500
        );

        when(eventRepository.isSoldOut("E201"))
                .thenReturn(false);

        when(paymentGateway.processPayment(5000))
                .thenThrow(
                        new PaymentFailedException("Payment declined")
                );

        // Act & Assert
        assertThrows(
                PaymentFailedException.class,
                () -> bookingManager.book(request)
        );

        // Verify
        verify(eventRepository, times(1))
                .isSoldOut("E201");

        verify(paymentGateway, times(1))
                .processPayment(5000);

        verify(eventRepository, never())
                .saveBooking(any(Booking.class));

        verifyNoInteractions(notificationService);

        verifyNoMoreInteractions(
                paymentGateway,
                eventRepository
        );
    }
    @Test
    void shouldRejectNullTransactionIdWithoutSavingBooking()
            throws Exception {

        // Arrange
        BookingRequest request = new BookingRequest(
                "C101",
                "E201",
                2,
                2500
        );

        when(eventRepository.isSoldOut("E201"))
                .thenReturn(false);

        when(paymentGateway.processPayment(5000))
                .thenReturn(null);

        // Act & Assert
        assertThrows(
                PaymentFailedException.class,
                () -> bookingManager.book(request)
        );

        // Verify
        verify(eventRepository, times(1))
                .isSoldOut("E201");

        verify(paymentGateway, times(1))
                .processPayment(5000);

        verifyNoInteractions(notificationService);

        verifyNoMoreInteractions(
                paymentGateway,
                eventRepository
        );
    }

    @Test
    void shouldRejectBlankTransactionIdWithoutSavingBooking()
            throws Exception {

        // Arrange
        BookingRequest request = new BookingRequest(
                "C101",
                "E201",
                2,
                2500
        );

        when(eventRepository.isSoldOut("E201"))
                .thenReturn(false);

        when(paymentGateway.processPayment(5000))
                .thenReturn("   ");

        // Act & Assert
        assertThrows(
                PaymentFailedException.class,
                () -> bookingManager.book(request)
        );

        // Verify
        verify(eventRepository, times(1))
                .isSoldOut("E201");

        verify(paymentGateway, times(1))
                .processPayment(5000);

        verifyNoInteractions(notificationService);

        verifyNoMoreInteractions(
                paymentGateway,
                eventRepository
        );
    }

    @Test
void shouldRejectNullDependencies() {

    assertThrows(
            NullPointerException.class,
            () -> new BookingManager(
                    null,
                    notificationService,
                    eventRepository
            )
    );

    assertThrows(
            NullPointerException.class,
            () -> new BookingManager(
                    paymentGateway,
                    null,
                    eventRepository
            )
    );

    assertThrows(
            NullPointerException.class,
            () -> new BookingManager(
                    paymentGateway,
                    notificationService,
                    null
            )
    );
}
}