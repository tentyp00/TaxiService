package taxiservice.order.dto;

/**
 * Created by bartl on 20.05.2017.
 */
public class OrderRouteDto {
    private int orderId;
    private double routeLength;

    public OrderRouteDto(int orderId, double routeLength) {
        this.orderId = orderId;
        this.routeLength = routeLength;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getRouteLength() {
        return routeLength;
    }

    public void setRouteLength(double routeLength) {
        this.routeLength = routeLength;
    }
}
