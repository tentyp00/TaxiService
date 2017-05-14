package taxiservice.order.dto;

/**
 * Created by monikanowakowicz on 14/05/2017.
 */
public class OrderAssignmentDto {
    private int orderId;
    private int shiftId;
    private int driverId;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }
}
