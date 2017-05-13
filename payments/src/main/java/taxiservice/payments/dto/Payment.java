package taxiservice.payments.dto;

public class Payment {
    long clientId;
    long orderId;
    
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

    @Override
    public String toString() {
        return "Payment [clientId=" + clientId + ", orderId=" + orderId + "]";
    }

}
