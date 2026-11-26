package edu.course.booking;

public interface IPaymentGateway {

    /**
     * Processes a payment.
     *
     * @param amountInCents the payment amount in cents
     * @return a non-blank transaction ID if payment succeeds
     * @throws PaymentFailedException if payment fails
     */
String processPayment(int amountInCents)
        throws PaymentFailedException;
}