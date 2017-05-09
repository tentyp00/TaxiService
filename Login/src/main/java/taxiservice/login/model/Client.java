package taxiservice.login.model;

import javax.persistence.*;

/**
 * Created by bartl on 07.05.2017.
 */
@Entity
@Table(name = "clients", schema = "taxiservice")
public class Client {

    private long clientid;
    private SystemUser user_id;
    private boolean is_active;
    private Wallet wallet;

    public Client(SystemUser user_id, boolean is_active, Wallet wallet) {
        this.user_id = user_id;
        this.is_active = is_active;
        this.wallet = wallet;
    }

    @Id
    @GeneratedValue
    @Column(name = "clientid")
    public long getClientId() {
        return clientid;
    }

    public void setClientId(long clientid) {
        this.clientid = clientid;
    }


    @OneToOne(cascade = CascadeType.ALL)
    public SystemUser getUserId() {
        return user_id;
    }

    public void setUserId(SystemUser user_id) {
        this.user_id = user_id;
    }

    @Column(name = "is_active")
    public boolean isActive() {
        return is_active;
    }

    public void setActive(boolean is_active) {
        this.is_active = is_active;
    }


    @OneToOne(cascade = CascadeType.ALL)
    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

}
