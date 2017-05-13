package taxiservice.login.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by bartl on 13.05.2017.
 */
@Entity
@Table(name = "login_history", schema = "taxiservice")
public class LoginHistory {

    private long id;
    private SystemUser systemUser;
    private Timestamp login_time;
    private Timestamp logout_time;


    public LoginHistory() {
    }

    @Id
    @Column(name = "login_id")
    @SequenceGenerator(name="taxiservice.login_history_loginhistoryid_seq", sequenceName="taxiservice.login_history_loginhistoryid_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taxiservice.login_history_loginhistoryid_seq")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public SystemUser getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
    }

    @Column(name = "login_time")
    public Timestamp getLogin_time() {
        return login_time;
    }

    public void setLogin_time(Timestamp login_time) {
        this.login_time = login_time;
    }

    @Column(name = "logout_time")
    public Timestamp getLogout_time() {
        return logout_time;
    }

    public void setLogout_time(Timestamp logout_time) {
        this.logout_time = logout_time;
    }
}
