package taxiservice.payments.models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wallets", schema = "taxiservice")
public class Wallet {

    private long id;
    private double amount;
    private String currency;
    private boolean isActive;
    private List<PaymentsHistory> paymentsHistoryList = new ArrayList<>();

    public Wallet() {
    }

    public Wallet(double amount, String currency, boolean isActive) {
        this.amount = amount;
        this.currency = currency;
        this.isActive = isActive;
    }

    @Id
    @Column(name = "wallet_id")
    @SequenceGenerator(name="taxiservice.wallets_walletid_seq", sequenceName="taxiservice.wallets_walletid_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taxiservice.wallets_walletid_seq")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "is_active")
    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean is_active) {
        this.isActive = is_active;
    }

    @Column(name = "amount")
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Column(name = "currency")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @OneToMany(fetch = FetchType.LAZY)
    public List<PaymentsHistory> getPaymentsHistoryList() {
        return paymentsHistoryList;
    }

    public void setPaymentsHistoryList(List<PaymentsHistory> paymentsHistoryList) {
        this.paymentsHistoryList = paymentsHistoryList;
    }
}
