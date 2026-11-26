package edu.course.booking;

public class BookingManager {

    private final IPaymentGateway paymentGateway;
    private final INotificationService notificationService;
    private final IEventRepository eventRepository;

    public BookingManager(
            IPaymentGateway paymentGateway,
            INotificationService notificationService,
            IEventRepository eventRepository) {

        this.paymentGateway = paymentGateway;
        this.notificationService = notificationService;
        this.eventRepository = eventRepository;
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

        String transactionId =
                paymentGateway.processPayment(totalAmountInCents);

        if (transactionId == null || transactionId.isBlank()) {
            throw new PaymentFailedException(
                    "Payment gateway returned an invalid transaction ID");
        }

        Booking booking = new Booking(request, transactionId);

        eventRepository.saveBooking(booking);

        notificationService.sendConfirmation(booking);

        return BookingResult.success(transactionId);
    }
}
