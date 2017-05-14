package taxiservice.login.exceptions;

/**
 * Created by bartl on 14.05.2017.
 */
public class AlreadyExistingEmailException extends Exception {

    private static final long serialVersionUID = 1L;

    final String message;

    public AlreadyExistingEmailException(String email) {
        this.message = "Email " +email + " is already used.";
    }

    public String getMessage() {
        return message;
    }
}
