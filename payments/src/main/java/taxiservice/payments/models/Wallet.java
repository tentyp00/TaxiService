package taxiservice.payments.models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Wallets", schema = "taxiservice")
public class Wallet {

    private long walletid;
    private double amount;
    private String currency;
    private boolean isActive;
    private List<PaymentsHistory> paymentsHistoryList = new ArrayList<>();


    @Id
    @GeneratedValue
    @Column(name = "walletid")
    public long getWalletid() {
        return walletid;
    }

    public void setWalletid(long walletid) {
        this.walletid = walletid;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "payments_history")
    public List<PaymentsHistory> getPaymentsHistoryList() {
        return paymentsHistoryList;
    }

    public void setPaymentsHistoryList(List<PaymentsHistory> paymentsHistoryList) {
        this.paymentsHistoryList = paymentsHistoryList;
    }
}
