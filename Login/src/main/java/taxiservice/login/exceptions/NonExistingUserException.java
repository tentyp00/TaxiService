package taxiservice.login.exceptions;

/**
 * Created by bartl on 14.05.2017.
 */
public class NonExistingUserException extends Exception{

    private static final long serialVersionUID = 1L;

    final String message;

    public NonExistingUserException(String login) {
        this.message = "User with login= " + login + " not found.";
    }

    public String getMessage() {
        return message;
    }
}
