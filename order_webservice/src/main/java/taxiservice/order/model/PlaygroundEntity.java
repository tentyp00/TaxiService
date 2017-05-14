package taxiservice.order.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by monikanowakowicz on 13/05/2017.
 */
@Entity
@Table(name = "playground", schema = "taxiservice", catalog = "taxiservice")
public class PlaygroundEntity {
    private int equipId;
    private String type;
    private String color;
    private String location;
    private Date installDate;

    @Id
    @Column(name = "equip_id")
    public int getEquipId() {
        return equipId;
    }

    public void setEquipId(int equipId) {
        this.equipId = equipId;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "color")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Basic
    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Basic
    @Column(name = "install_date")
    public Date getInstallDate() {
        return installDate;
    }

    public void setInstallDate(Date installDate) {
        this.installDate = installDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlaygroundEntity that = (PlaygroundEntity) o;

        if (equipId != that.equipId) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (color != null ? !color.equals(that.color) : that.color != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (installDate != null ? !installDate.equals(that.installDate) : that.installDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = equipId;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (installDate != null ? installDate.hashCode() : 0);
        return result;
    }
}
