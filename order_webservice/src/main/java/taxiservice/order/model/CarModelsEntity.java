package taxiservice.order.model;

import javax.persistence.*;

/**
 * Created by monikanowakowicz on 13/05/2017.
 */
@Entity
@Table(name = "car_models", schema = "taxiservice", catalog = "taxiservice")
public class CarModelsEntity {
    private int carmodelId;
    private String carModelName;

    @Id
    @Column(name = "carmodel_id")
    public int getCarmodelId() {
        return carmodelId;
    }

    public void setCarmodelId(int carmodelId) {
        this.carmodelId = carmodelId;
    }

    @Basic
    @Column(name = "car_model_name")
    public String getCarModelName() {
        return carModelName;
    }

    public void setCarModelName(String carModelName) {
        this.carModelName = carModelName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarModelsEntity that = (CarModelsEntity) o;

        if (carmodelId != that.carmodelId) return false;
        if (carModelName != null ? !carModelName.equals(that.carModelName) : that.carModelName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = carmodelId;
        result = 31 * result + (carModelName != null ? carModelName.hashCode() : 0);
        return result;
    }
}
