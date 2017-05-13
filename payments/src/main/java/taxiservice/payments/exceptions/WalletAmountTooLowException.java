package taxiservice.payments.exceptions;

public class WalletAmountTooLowException extends Exception {

    private static final long serialVersionUID = 1L;

    final String message;

    public WalletAmountTooLowException(long clientId, double amount) {
        this.message = "Wallet amount for client with id=" + clientId + " is too low. Please add creadit first. Current amount = "+amount;
    }

    public String getMessage() {
        return message;
    }

}
