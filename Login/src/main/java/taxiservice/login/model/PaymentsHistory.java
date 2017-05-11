package taxiservice.login.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "payments_history", schema = "taxiservice")
public class PaymentsHistory {

   private long id;
   private Wallet wallet;
   private Timestamp payment_time;
   private double amount;
   private String currency;
   private String payment_type;

    public PaymentsHistory() {
    }

    @Id
    @Column(name = "payment_id")
    @SequenceGenerator(name="taxiservice.payments_history_paymentid_seq", sequenceName="taxiservice.payments_history_paymentid_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taxiservice.payments_history_paymentid_seq")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    @Column(name = "payment_time")
    public Timestamp getPayment_time() {
        return payment_time;
    }

    public void setPayment_time(Timestamp payment_time) {
        this.payment_time = payment_time;
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

    @Column(name = "payment_type")
    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

}
