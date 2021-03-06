package taxiservice.login.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bartl on 07.05.2017.
 */

@Entity
@Table(name = "system_users", schema = "taxiservice")
public class SystemUser {

    private long id;
    private String first_name;
    private String last_name;
    private String email;
    private String login;
    private String password;
    private Timestamp join_date;
    private String phone_number;
    private List<LoginHistory> loginHistory = new ArrayList<>();

    public SystemUser() {
    }

    public SystemUser(String first_name, String last_name, String email, String login, String password, Timestamp join_date, String phone_number) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.join_date = join_date;
        this.phone_number = phone_number;
    }

    @Id
    @Column(name = "user_id")
    @SequenceGenerator(name="taxiservice.system_users_userid_seq", sequenceName="taxiservice.system_users_userid_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taxiservice.system_users_userid_seq")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Column(name = "email", unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "login", unique = true)
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "systemUser")
    public List<LoginHistory> getLoginHistory() {
        return loginHistory;
    }

    public void setLoginHistory(List<LoginHistory> loginHistory) {
        this.loginHistory = loginHistory;
    }
}
