package taxiservice.order.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by monikanowakowicz on 13/05/2017.
 */
@Entity
@Table(name = "login_history", schema = "taxiservice", catalog = "taxiservice")
public class LoginHistoryEntity {
    private int loginId;
    private int userId;
    private Timestamp loginTime;
    private Timestamp logoutTime;

    @Id
    @Column(name = "login_id")
    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
    }

    @Basic
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "login_time")
    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    @Basic
    @Column(name = "logout_time")
    public Timestamp getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Timestamp logoutTime) {
        this.logoutTime = logoutTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginHistoryEntity that = (LoginHistoryEntity) o;

        if (loginId != that.loginId) return false;
        if (userId != that.userId) return false;
        if (loginTime != null ? !loginTime.equals(that.loginTime) : that.loginTime != null) return false;
        if (logoutTime != null ? !logoutTime.equals(that.logoutTime) : that.logoutTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = loginId;
        result = 31 * result + userId;
        result = 31 * result + (loginTime != null ? loginTime.hashCode() : 0);
        result = 31 * result + (logoutTime != null ? logoutTime.hashCode() : 0);
        return result;
    }
}
