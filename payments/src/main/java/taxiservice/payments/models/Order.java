package taxiservice.payments.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "orders", schema = "taxiservice")
public class Order {

	private long id;
	private long shiftId;
	private Client client;
	private Timestamp rideStartTime;
	private Timestamp rideEndTime;
	private String locationStart;
	private String locationEnd;
	private double routeLength;
	private String status;
	private double cost;

	@Id
	@Column(name = "order_id")
	@SequenceGenerator(name = "taxiservice.orders_orderid_seq", sequenceName = "taxiservice.orders_orderid_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taxiservice.orders_orderid_seq")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
    @Column(name = "shift_id")
	public long getShiftId() {
		return shiftId;
	}

	public void setShiftId(long shift) {
		this.shiftId = shift;
	}
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	@Column(name = "ride_start_time")
	public Timestamp getRideStartTime() {
		return rideStartTime;
	}

	public void setRideStartTime(Timestamp rideStartTime) {
		this.rideStartTime = rideStartTime;
	}
	@Column(name = "ride_end_time")
	public Timestamp getRideEndTime() {
		return rideEndTime;
	}

	public void setRideEndTime(Timestamp rideEndTime) {
		this.rideEndTime = rideEndTime;
	}
	@Column(name = "location_start")
	public String getLocationStart() {
		return locationStart;
	}

	public void setLocationStart(String locationStart) {
		this.locationStart = locationStart;
	}
	@Column(name = "location_end")
	public String getLocationEnd() {
		return locationEnd;
	}

	public void setLocationEnd(String locationEnd) {
		this.locationEnd = locationEnd;
	}
	@Column(name = "route_length")
	public double getRouteLength() {
		return routeLength;
	}

	public void setRouteLength(double routeLength) {
		this.routeLength = routeLength;
	}
	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "cost")
	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

}
