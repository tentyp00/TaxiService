package taxiservice.order.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by monikanowakowicz on 13/05/2017.
 */
@Entity
@Table(name = "orders", schema = "taxiservice", catalog = "taxiservice")
public class OrdersEntity {
    private int orderId;
    private Timestamp rideStartTime;
    private Timestamp rideEndTime;
    private String locationStart;
    private String locationEnd;
    private Float routeLength;
    private String status;
    private BigDecimal cost;
    private int clientId;
    private int shiftId;

    @Basic
    @Column(name = "client_id")
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Basic
    @Column(name = "shift_id")
    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    @Id
    @SequenceGenerator(name="taxiservice.orders_orderid_seq", sequenceName="taxiservice.orders_orderid_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "taxiservice.orders_orderid_seq")
    @Column(name = "order_id")
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


    @Basic
    @Column(name = "ride_start_time")
    public Timestamp getRideStartTime() {
        return rideStartTime;
    }

    public void setRideStartTime(Timestamp rideStartTime) {
        this.rideStartTime = rideStartTime;
    }

    @Basic
    @Column(name = "ride_end_time")
    public Timestamp getRideEndTime() {
        return rideEndTime;
    }

    public void setRideEndTime(Timestamp rideEndTime) {
        this.rideEndTime = rideEndTime;
    }

    @Basic
    @Column(name = "location_start")
    public String getLocationStart() {
        return locationStart;
    }

    public void setLocationStart(String locationStart) {
        this.locationStart = locationStart;
    }

    @Basic
    @Column(name = "location_end")
    public String getLocationEnd() {
        return locationEnd;
    }

    public void setLocationEnd(String locationEnd) {
        this.locationEnd = locationEnd;
    }

    @Basic
    @Column(name = "route_length")
    public Float getRouteLength() {
        return routeLength;
    }

    public void setRouteLength(Float routeLength) {
        this.routeLength = routeLength;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "cost")
    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrdersEntity that = (OrdersEntity) o;

        if (orderId != that.orderId) return false;
        if (rideStartTime != null ? !rideStartTime.equals(that.rideStartTime) : that.rideStartTime != null)
            return false;
        if (rideEndTime != null ? !rideEndTime.equals(that.rideEndTime) : that.rideEndTime != null) return false;
        if (locationStart != null ? !locationStart.equals(that.locationStart) : that.locationStart != null)
            return false;
        if (locationEnd != null ? !locationEnd.equals(that.locationEnd) : that.locationEnd != null) return false;
        if (routeLength != null ? !routeLength.equals(that.routeLength) : that.routeLength != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (cost != null ? !cost.equals(that.cost) : that.cost != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId;
        result = 31 * result + (rideStartTime != null ? rideStartTime.hashCode() : 0);
        result = 31 * result + (rideEndTime != null ? rideEndTime.hashCode() : 0);
        result = 31 * result + (locationStart != null ? locationStart.hashCode() : 0);
        result = 31 * result + (locationEnd != null ? locationEnd.hashCode() : 0);
        result = 31 * result + (routeLength != null ? routeLength.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        return result;
    }
}
