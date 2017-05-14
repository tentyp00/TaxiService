package taxiservice.order.exceptions;

/**
 * Created by monikanowakowicz on 14/05/2017.
 */
public class NotAssignableStatusException extends Exception {
    private static final long serialVersionUID = 1L;

    final String message;

    public NotAssignableStatusException() {
        this.message = "Order status does not allow to assign order.";
    }

    public String getMessage() {
        return message;
    }
}
