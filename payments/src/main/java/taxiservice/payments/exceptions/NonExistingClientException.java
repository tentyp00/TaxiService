package taxiservice.payments.exceptions;

public class NonExistingClientException extends Exception {

    private static final long serialVersionUID = 1L;

    final String message;

    public NonExistingClientException(long clientId) {
        this.message = "Client with id=" + clientId + " not found.";
    }

    public String getMessage() {
        return message;
    }

}
