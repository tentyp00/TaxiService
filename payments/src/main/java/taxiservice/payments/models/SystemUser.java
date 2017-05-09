package taxiservice.payments.models;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by bartl on 07.05.2017.
 */

@Entity
@Table(name = "system_users", schema = "taxiservice")
public class SystemUser {

    private long userid;
    private String first_name;
    private String last_name;
    private String email;
    private String login;
    private String password;
    private Timestamp join_date;
    private String phone_number;


    @Id
    @GeneratedValue
    @Column(name = "userid")
    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "join_date")
    public Timestamp getJoinDate() {
        return join_date;
    }

    public void setJoinDate(Timestamp join_date) {
        this.join_date = join_date;
    }

    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return phone_number;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }
}
