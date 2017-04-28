package taxiservice.payments.services;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import taxiservice.payments.dto.PaymentsHistory;
import taxiservice.payments.dto.Wallet;
import taxiservice.payments.exceptions.NonExistingClientException;
import taxiservice.payments.utils.HibernateUtil;

public class PaymentService {

	Session session;

	public double getCreditForClient(long clientId) throws NonExistingClientException {
		openSession();
		String hql = "FROM Wallet WHERE clientId =" + clientId;
		Query query = session.createQuery(hql);
		List<Wallet> result = query.list();
		closeSession();
		if (result.isEmpty()) {
			throw new NonExistingClientException(clientId);
		} else {
			return result.get(0).getAmount();
		}

	}
	public Wallet getClientWallet(long clientId) throws NonExistingClientException{
		openSession();
		String hql = "FROM Wallet WHERE clientId =" + clientId;
		Query query = session.createQuery(hql);
		List<Wallet> result = query.list();
		closeSession();
		if (result.isEmpty()) {
			throw new NonExistingClientException(clientId);
		} else {
			return result.get(0);
		}
		
	}
	public double addCreditForClient(long clientId, double amount) throws NonExistingClientException {
		Wallet clientWallet = getClientWallet(clientId);
		double currentAmount = clientWallet.getAmount() + amount;
		openSession();
		addPaymentHistory(clientWallet.getWalletid(),amount,"EUR","TEST");
		Query query = session.createQuery("update Wallet set amount = :updateAmount" + " where clientId = :clientId");
		query.setParameter("updateAmount", currentAmount);
		query.setParameter("clientId", clientId);
		query.executeUpdate();
		closeSession();
		return currentAmount;
	}
	
	public List<PaymentsHistory> getPaymentsForClients(long clientId) throws NonExistingClientException{
		Wallet clientWallet = getClientWallet(clientId);
		openSession();
		String hql = "FROM PaymentsHistory WHERE wallet_id =" + clientWallet.getWalletid();
		Query query = session.createQuery(hql);
		List<PaymentsHistory> result = query.list();
		closeSession();
		return result;
	}
	
	public void payForOrder(long clientId, long orderId){
		
		
	}

	public void addPaymentHistory(long walletId,double amount,String currency,String payment_type){
		Date data = new Date();
		
		Query query = session.createSQLQuery("INSERT INTO Payments_History (wallet_id, payment_time,amount,currency,payment_type) "
				+ "VALUES (:walletId, :payment_time,:amount,:currency,:payment_type)");
		query.setParameter("walletId", walletId);
		query.setParameter("payment_time", new Timestamp(data.getTime()));
		query.setParameter("amount", amount);
		query.setParameter("currency", currency);
		query.setParameter("payment_type", payment_type);
		query.executeUpdate();
	}
	
	public void openSession() {
		if (session==null || !session.isOpen()) {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
		}
	}

	public void closeSession() {
		if (session!=null && session.isOpen()) {
			session.getTransaction().commit();
			session.close();
		}
	}
}
