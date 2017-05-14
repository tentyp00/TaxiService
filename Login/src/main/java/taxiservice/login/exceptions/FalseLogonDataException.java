package taxiservice.login.exceptions;


public class FalseLogonDataException extends Exception {
    private static final long serialVersionUID = 1L;

    final String message;

    public FalseLogonDataException(String orderId) {
        this.message = "Incorrect login or password for user with login " + orderId + ".";
    }

    public String getMessage() {
        return message;
    }
}
