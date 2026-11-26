package edu.course.booking;

public class Booking {

    private final String customerId;
    private final String eventId;
    private final int ticketQuantity;
    private final int pricePerTicketInCents;
    private final int totalAmountInCents;
    private final String transactionId;

    public Booking(BookingRequest request, String transactionId) {

        if (request == null) {
            throw new IllegalArgumentException(
                    "Booking request cannot be null");
        }

        if (transactionId == null || transactionId.isBlank()) {
            throw new IllegalArgumentException(
                    "Transaction ID cannot be null or blank");
        }

        this.customerId = request.getCustomerId();
        this.eventId = request.getEventId();
        this.ticketQuantity = request.getTicketQuantity();
        this.pricePerTicketInCents =
                request.getPricePerTicketInCents();

        this.totalAmountInCents = Math.multiplyExact(
                ticketQuantity, pricePerTicketInCents);

        this.transactionId = transactionId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getEventId() {
        return eventId;
    }

    public int getTicketQuantity() {
        return ticketQuantity;
    }

    public int getPricePerTicketInCents() {
        return pricePerTicketInCents;
    }

    public int getTotalAmountInCents() {
        return totalAmountInCents;
    }

    public String getTransactionId() {
        return transactionId;
    }
}