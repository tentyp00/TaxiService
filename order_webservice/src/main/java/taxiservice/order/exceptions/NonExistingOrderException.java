package taxiservice.order.exceptions;

/**
 * Created by monikanowakowicz on 14/05/2017.
 */
public class NonExistingOrderException extends Exception {
    private static final long serialVersionUID = 1L;

    final String message;

    public NonExistingOrderException(int clientId, int orderId) {
        this.message = "Order with id= " + orderId + " for client= " + clientId + " not found.";
    }

    public NonExistingOrderException(int orderId) {
        this.message = "Order with id= " + orderId + " not found.";
    }

    public String getMessage() {
        return message;
    }
}
