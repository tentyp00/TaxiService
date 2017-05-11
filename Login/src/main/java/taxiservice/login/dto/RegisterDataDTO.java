package taxiservice.login.dto;

import java.sql.Timestamp;

/**
 * Created by bartl on 04.05.2017.
 */
public class RegisterDataDTO {

    private long userid;
    private String first_name;
    private String last_name;
    private String email;
    private String login;
    private String password;
    private Timestamp join_date;
    private String phone_number;

    public RegisterDataDTO() {
    }

    public RegisterDataDTO(long userid, String first_name, String last_name, String email, String login, String password,
                           Timestamp join_date, String phone_number) {
        this.userid = userid;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.join_date = join_date;
        this.phone_number = phone_number;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getJoin_date() {
        return join_date;
    }

    public void setJoin_date(Timestamp join_date) {
        this.join_date = join_date;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
