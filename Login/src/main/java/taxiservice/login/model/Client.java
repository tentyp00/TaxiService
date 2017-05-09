package taxiservice.login.model;

import javax.persistence.*;

/**
 * Created by bartl on 07.05.2017.
 */
@Entity
@Table(name = "clients", schema = "taxiservice")
public class Client {

    private long id;
    private SystemUser user;
    private boolean is_active;
    private Wallet wallet;

    public Client() {
    }

    public Client(SystemUser user, boolean is_active, Wallet wallet) {
        this.user = user;
        this.is_active = is_active;
        this.wallet = wallet;
    }

    @Id
    @Column(name = "client_id")
    @SequenceGenerator(name="taxiservice.clients_clientid_seq", sequenceName="taxiservice.clients_clientid_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taxiservice.clients_clientid_seq")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", nullable=false, updatable=false)
    public SystemUser getUser() {
        return user;
    }

    public void setUser(SystemUser user) {
        this.user = user;
    }

    @Column(name = "is_active")
    public boolean isActive() {
        return is_active;
    }

    public void setActive(boolean is_active) {
        this.is_active = is_active;
    }


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="wallet_id", nullable=false, updatable=false)
    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

}
