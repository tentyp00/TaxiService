package taxiservice.payments.dto;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="payments_history" ,schema="taxiservice")
public class PaymentsHistory {

long paymentid;
long wallet_id;
Timestamp payment_time;
double amount;
String currency;
String payment_type;

@Id
public long getPaymentid() {
	return paymentid;
}
public void setPaymentid(long paymentid) {
	this.paymentid = paymentid;
}
@Column(name="wallet_id")
public long getWallet_id() {
	return wallet_id;
}
public void setWallet_id(long wallet_id) {
	this.wallet_id = wallet_id;
}
@Column(name="payment_time")
public Timestamp getPayment_time() {
	return payment_time;
}
public void setPayment_time(Timestamp payment_time) {
	this.payment_time = payment_time;
}
@Column(name="amount")
public double getAmount() {
	return amount;
}
public void setAmount(double amount) {
	this.amount = amount;
}
@Column(name="currency")
public String getCurrency() {
	return currency;
}
public void setCurrency(String currency) {
	this.currency = currency;
}
@Column(name="payment_type")
public String getPayment_type() {
	return payment_type;
}
public void setPayment_type(String payment_type) {
	this.payment_type = payment_type;
}

}
