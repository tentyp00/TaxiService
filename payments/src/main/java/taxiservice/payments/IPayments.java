package taxiservice.payments;

import javax.ws.rs.core.Response;

import taxiservice.payments.models.Payment;

public interface IPayments {

	Response getAccountStatus(long clientId);
	Response pay(Payment payment);
	Response addCredit(long clientId, double amount);
	Response getAccountHistory(long clientId);
}
