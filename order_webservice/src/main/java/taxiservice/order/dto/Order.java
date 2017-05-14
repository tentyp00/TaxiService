package taxiservice.order.dto;

/**
 * Created by monikanowakowicz on 13/05/2017.
 */
public class Order {
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Order() {
    }

    public Order(int userId) {
        this.userId = userId;
    }
}
