package taxiservice.order.exceptions;

/**
 * Created by monikanowakowicz on 14/05/2017.
 */
public class NonExistingShiftException extends Exception {
    private static final long serialVersionUID = 1L;

    final String message;

    public NonExistingShiftException(int shiftId, int driverId) {
        this.message = "Shift with id= " + shiftId + " for driver= " + driverId + " not found.";
    }

    public String getMessage() {
        return message;
    }
}
