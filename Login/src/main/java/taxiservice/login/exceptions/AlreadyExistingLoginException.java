package taxiservice.login.exceptions;

/**
 * Created by bartl on 14.05.2017.
 */
public class AlreadyExistingLoginException extends Exception {

    private static final long serialVersionUID = 1L;

    final String message;

    public AlreadyExistingLoginException(String login) {
        this.message = "Login " +login + " is already used.";
    }

    public String getMessage() {
        return message;
    }
}
