package taxiservice.payments.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "Playground", schema = "public")
public class Playground {

    public long equip_id;
    public String type;
    public String color;
    public String location;
    public Date install_date;

    public Playground() {

    }

    @Id
    public long getEquip_id() {
        return equip_id;
    }

    public void setEquip_id(long equip_id) {
        this.equip_id = equip_id;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "color")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "install_date")
    public Date getInstall_date() {
        return install_date;
    }

    public void setInstall_date(Date install_date) {
        this.install_date = install_date;
    }

    @Override
    public String toString() {
        return "Playground [equip_id=" + equip_id + ", type=" + type + ", color=" + color + ", location=" + location
                + ", install_date=" + install_date + "]";
    }


}
