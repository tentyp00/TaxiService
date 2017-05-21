package taxiservice.order.dto;

/**
 * Created by monikanowakowicz on 13/05/2017.
 */
public class Order {
    private int userId;
    private String location_start;
    private String location_end;

    public Order() {
    }

    public Order(int userId) {
        this.userId = userId;
    }

    public Order(int userId, String location_start, String location_end) {
        this.userId = userId;
        this.location_start = location_start;
        this.location_end = location_end;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLocation_start() {
        return location_start;
    }

    public void setLocation_start(String location_start) {
        this.location_start = location_start;
    }

    public String getLocation_end() {
        return location_end;
    }

    public void setLocation_end(String location_end) {
        this.location_end = location_end;
    }


}
