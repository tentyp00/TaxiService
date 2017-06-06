package taxiservice.localization.model;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "shifts", schema = "taxiservice", catalog = "taxiservice")
public class ShiftsEntity {
    private int shiftId;
    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp loginTime;
    private Timestamp logoutTime;
    private Boolean isFree;
    private String currentLocation;
    private int driverId;
    private CarsEntity car;

    @Id
    @Column(name = "shift_id")
    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    
    @Column(name = "start_time")
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    
    @Column(name = "end_time")
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    
    @Column(name = "login_time")
    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    
    @Column(name = "logout_time")
    public Timestamp getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Timestamp logoutTime) {
        this.logoutTime = logoutTime;
    }

    
    @Column(name = "is_free")
    public Boolean getFree() {
        return isFree;
    }

    public void setFree(Boolean free) {
        isFree = free;
    }

    @Column(name = "driver_id")
    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="car_id", nullable=false, updatable=false)
    public CarsEntity getCarId() {
        return car;
    }

    public void setCarId(CarsEntity car) {
        this.car = car;
    }

    
    @Column(name = "current_location")
    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShiftsEntity that = (ShiftsEntity) o;

        if (shiftId != that.shiftId) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (loginTime != null ? !loginTime.equals(that.loginTime) : that.loginTime != null) return false;
        if (logoutTime != null ? !logoutTime.equals(that.logoutTime) : that.logoutTime != null) return false;
        if (isFree != null ? !isFree.equals(that.isFree) : that.isFree != null) return false;
        if (currentLocation != null ? !currentLocation.equals(that.currentLocation) : that.currentLocation != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = shiftId;
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (loginTime != null ? loginTime.hashCode() : 0);
        result = 31 * result + (logoutTime != null ? logoutTime.hashCode() : 0);
        result = 31 * result + (isFree != null ? isFree.hashCode() : 0);
        result = 31 * result + (currentLocation != null ? currentLocation.hashCode() : 0);
        return result;
    }

    

}
