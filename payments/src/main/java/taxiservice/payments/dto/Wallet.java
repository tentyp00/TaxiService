package taxiservice.payments.dto;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Wallets" ,schema="taxiservice")
public class Wallet {

	long walletid;
	double amount;
	String currency;
	boolean is_active;
	long client_Id;
	
	

	@Id
	public long getWalletid() {
		return walletid;
	}
	public void setWalletid(long walletid) {
		this.walletid = walletid;
	}
	@Column(name="is_active")
	public boolean isIs_active() {
		return is_active;
	}
	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}
	@Column(name="amount")
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	@Column(name="currency")
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	@Column(name="client_Id")
	public long getClientId() {
		return client_Id;
	}
	public void setClientId(long clientId) {
		this.client_Id = clientId;
	}
	
}
