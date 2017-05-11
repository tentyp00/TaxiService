package taxiservice.payments;

import taxiservice.payments.dto.ChargeAmount;
import taxiservice.payments.dto.Payment;

import javax.ws.rs.core.Response;

public interface IPayments {

	Response getAccountStatus(long clientId);
	Response pay(Payment payment);
	Response addCredit(ChargeAmount chargeAmount);
	Response getAccountHistory(long clientId);
}
