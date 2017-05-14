package taxiservice.order.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by monikanowakowicz on 13/05/2017.
 */
@Entity
@Table(name = "taxi_drivers", schema = "taxiservice", catalog = "taxiservice")
public class TaxiDriversEntity {
    private int driverId;
    private Integer userId;
    private String drivingLicense;
    private Date birthDate;
    private String contractNumber;
    private Boolean isEmployed;

    @Id
    @Column(name = "driver_id")
    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "driving_license")
    public String getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    @Basic
    @Column(name = "birth_date")
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Basic
    @Column(name = "contract_number")
    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    @Basic
    @Column(name = "is_employed")
    public Boolean getEmployed() {
        return isEmployed;
    }

    public void setEmployed(Boolean employed) {
        isEmployed = employed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaxiDriversEntity that = (TaxiDriversEntity) o;

        if (driverId != that.driverId) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (drivingLicense != null ? !drivingLicense.equals(that.drivingLicense) : that.drivingLicense != null)
            return false;
        if (birthDate != null ? !birthDate.equals(that.birthDate) : that.birthDate != null) return false;
        if (contractNumber != null ? !contractNumber.equals(that.contractNumber) : that.contractNumber != null)
            return false;
        if (isEmployed != null ? !isEmployed.equals(that.isEmployed) : that.isEmployed != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = driverId;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (drivingLicense != null ? drivingLicense.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (contractNumber != null ? contractNumber.hashCode() : 0);
        result = 31 * result + (isEmployed != null ? isEmployed.hashCode() : 0);
        return result;
    }
}
