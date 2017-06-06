package taxiservice.localization.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "cars", schema = "taxiservice", catalog = "taxiservice")
public class CarsEntity {
    
    private int carId;
    private CarModelsEntity carModelId;
    private Integer ownerId;
    private String modelType;
    private String carType;
    private String licensePlate;
    private String insuranceNumber;
    private Integer manufactureYear;

    @Id
    @Column(name = "car_id")
    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    
    @Column(name = "car_model_id")
    public CarModelsEntity getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(CarModelsEntity carModelId) {
        this.carModelId = carModelId;
    }

    
    @Column(name = "owner_id")
    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    
    @Column(name = "model_type")
    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    
    @Column(name = "car_type")
    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    
    @Column(name = "license_plate")
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    
    @Column(name = "insurance_number")
    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    
    @Column(name = "manufacture_year")
    public Integer getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(Integer manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarsEntity that = (CarsEntity) o;

        if (carId != that.carId) return false;
        if (carModelId != null ? !carModelId.equals(that.carModelId) : that.carModelId != null) return false;
        if (ownerId != null ? !ownerId.equals(that.ownerId) : that.ownerId != null) return false;
        if (modelType != null ? !modelType.equals(that.modelType) : that.modelType != null) return false;
        if (carType != null ? !carType.equals(that.carType) : that.carType != null) return false;
        if (licensePlate != null ? !licensePlate.equals(that.licensePlate) : that.licensePlate != null) return false;
        if (insuranceNumber != null ? !insuranceNumber.equals(that.insuranceNumber) : that.insuranceNumber != null)
            return false;
        if (manufactureYear != null ? !manufactureYear.equals(that.manufactureYear) : that.manufactureYear != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = carId;
        result = 31 * result + (carModelId != null ? carModelId.hashCode() : 0);
        result = 31 * result + (ownerId != null ? ownerId.hashCode() : 0);
        result = 31 * result + (modelType != null ? modelType.hashCode() : 0);
        result = 31 * result + (carType != null ? carType.hashCode() : 0);
        result = 31 * result + (licensePlate != null ? licensePlate.hashCode() : 0);
        result = 31 * result + (insuranceNumber != null ? insuranceNumber.hashCode() : 0);
        result = 31 * result + (manufactureYear != null ? manufactureYear.hashCode() : 0);
        return result;
    }
}
