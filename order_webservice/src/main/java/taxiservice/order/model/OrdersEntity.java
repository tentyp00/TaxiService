package taxiservice.order.model;

import javax.persistence.*;
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
    private double routeLength;
    private String status;
    private double cost;
    private int clientId;
    private ShiftsEntity shiftId;

    @Basic
    @Column(name = "client_id")
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="shift_id", nullable=true, updatable=true)
    public ShiftsEntity getShiftId() {
        return shiftId;
    }

    public void setShiftId(ShiftsEntity shiftId) {
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
    public double getRouteLength() {
        return routeLength;
    }

    public void setRouteLength(double routeLength) {
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
    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }


}
