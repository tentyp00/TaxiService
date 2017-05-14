package taxiservice.order.exceptions;

/**
 * Created by monikanowakowicz on 14/05/2017.
 */
public class NotInProgressStatusException extends Exception {
    private static final long serialVersionUID = 1L;

    final String message;

    public NotInProgressStatusException() {
        this.message = "Order status does not allow to end order travel.";
    }

    public String getMessage() {
        return message;
    }
}
