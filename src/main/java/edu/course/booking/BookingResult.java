package edu.course.booking;

public class BookingResult {

    public enum Status {
        SUCCESS,
        SOLD_OUT
    }

    private final Status status;
    private final String transactionId;

    private BookingResult(Status status, String transactionId) {
        this.status = status;
        this.transactionId = transactionId;
    }

    public static BookingResult success(String transactionId) {
        if (transactionId == null || transactionId.isBlank()) {
            throw new IllegalArgumentException(
                    "Transaction ID cannot be null or blank");
        }

        return new BookingResult(Status.SUCCESS, transactionId);
    }

    public static BookingResult soldOut() {
        return new BookingResult(Status.SOLD_OUT, null);
    }

    public Status getStatus() {
        return status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public boolean isSuccessful() {
        return status == Status.SUCCESS;
    }
}