package taxiservice.order.exceptions;

/**
 * Created by monikanowakowicz on 14/05/2017.
 */
public class NotCancellableStatusException extends Throwable {
    private static final long serialVersionUID = 1L;

    final String message;

    public NotCancellableStatusException() {
        this.message = "Order status does not allow to cancel order.";
    }

    public String getMessage() {
        return message;
    }
}
