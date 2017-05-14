package taxiservice.order.dto;

/**
 * Created by monikanowakowicz on 14/05/2017.
 */
public class CancelOrderDto {
    private int orderId;
    private int userId;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
