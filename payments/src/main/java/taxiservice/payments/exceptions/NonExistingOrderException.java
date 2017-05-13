package taxiservice.payments.exceptions;

public class NonExistingOrderException extends Exception {

    private static final long serialVersionUID = 1L;

    final String message;

    public NonExistingOrderException(long orderId) {
        this.message = "Order with id=" + orderId + " not found.";
    }

    public String getMessage() {
        return message;
    }

}
