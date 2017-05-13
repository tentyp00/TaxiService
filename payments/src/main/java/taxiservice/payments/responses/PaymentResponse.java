package taxiservice.payments.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PaymentResponse extends AccountCreditResponse{
	private String paymentType;
	private double paymentValue;
	private Long orderId;
	
	
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public double getPaymentValue() {
		return paymentValue;
	}
	public void setPaymentValue(double paymentValue) {
		this.paymentValue = paymentValue;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
}
