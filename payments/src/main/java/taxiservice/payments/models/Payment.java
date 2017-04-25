package taxiservice.payments.models;

public class Payment {
	long clientId;
	long orderId;
	double cost;
	public long getClientId() {
		return clientId;
	}
	public void setClientId(long clientId) {
		this.clientId = clientId;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	@Override
	public String toString() {
		return "Payment [clientId=" + clientId + ", orderId=" + orderId + ", cost=" + cost + "]";
	}
	
}
