package edu.course.booking;

import static java.util.Objects.requireNonNull;

public class BookingManager {

    private final IPaymentGateway paymentGateway;
    private final INotificationService notificationService;
    private final IEventRepository eventRepository;

    public BookingManager(
            IPaymentGateway paymentGateway,
            INotificationService notificationService,
            IEventRepository eventRepository) {

        this.paymentGateway = requireNonNull(
                paymentGateway, "Payment gateway cannot be null");

        this.notificationService = requireNonNull(
                notificationService, "Notification service cannot be null");

        this.eventRepository = requireNonNull(
                eventRepository, "Event repository cannot be null");
    }

    public BookingResult book(BookingRequest request)
            throws PaymentFailedException {

        if (request == null) {
            throw new IllegalArgumentException(
                    "Booking request cannot be null");
        }

        if (eventRepository.isSoldOut(request.getEventId())) {
            return BookingResult.soldOut();
        }

        int totalAmountInCents = Math.multiplyExact(
                request.getTicketQuantity(),
                request.getPricePerTicketInCents());

        String transactionId = processPayment(totalAmountInCents);

        Booking booking = new Booking(request, transactionId);

        eventRepository.saveBooking(booking);

        notificationService.sendConfirmation(booking);

        return BookingResult.success(transactionId);
    }

    private String processPayment(int amountInCents)
            throws PaymentFailedException {

        String transactionId =
                paymentGateway.processPayment(amountInCents);

        if (transactionId == null || transactionId.isBlank()) {
            throw new PaymentFailedException(
                    "Payment gateway returned an invalid transaction ID");
        }

        return transactionId;
    }
}