package taxiservice.payments.services;

import org.hibernate.Query;
import org.hibernate.Session;
import taxiservice.payments.dto.ChargeAmount;
import taxiservice.payments.dto.Payment;
import taxiservice.payments.exceptions.NonExistingClientException;
import taxiservice.payments.exceptions.NonExistingOrderException;
import taxiservice.payments.exceptions.WalletAmountTooLowException;
import taxiservice.payments.models.Client;
import taxiservice.payments.models.Order;
import taxiservice.payments.models.PaymentsHistory;
import taxiservice.payments.models.Wallet;
import taxiservice.payments.utils.HibernateUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class PaymentService {

	Session session;

	public double getCreditForClient(long clientId) throws NonExistingClientException {
		openSession();
		String hql = "FROM Client WHERE client_Id =" + clientId;
		Query query = session.createQuery(hql);
		List<Client> result = query.list();
		closeSession();
		if (result.isEmpty()) {
			throw new NonExistingClientException(clientId);
		} else {
			return result.get(0).getWallet().getAmount();
		}

	}

	public Wallet getClientWallet(long clientId) throws NonExistingClientException {

		String hql = "FROM Client WHERE client_Id =" + clientId;
		Query query = session.createQuery(hql);
		List<Client> result = query.list();

		if (result.isEmpty()) {
			throw new NonExistingClientException(clientId);
		} else {
			return result.get(0).getWallet();
		}

	}

	public double addCreditForClient(ChargeAmount chargeAmount) throws NonExistingClientException {
		openSession();
		Wallet clientWallet = getClientWallet(chargeAmount.getClientId());

		double currentAmount = clientWallet.getAmount() + chargeAmount.getAmount();
		addPaymentHistory(clientWallet.getId(), chargeAmount.getAmount(), chargeAmount.getCurrency(),
				chargeAmount.getPayment_type());
		Query query = session.createQuery("update Wallet set amount = :updateAmount" + " where wallet_Id = :wallet_Id");
		query.setParameter("updateAmount", currentAmount);
		query.setParameter("wallet_Id", clientWallet.getId());
		query.executeUpdate();
		closeSession();
		return currentAmount;
	}

	public List<PaymentsHistory> getPaymentsForClients(long clientId) throws NonExistingClientException {
		openSession();
		Wallet clientWallet = getClientWallet(clientId);
		String hql = "FROM PaymentsHistory WHERE wallet =" + clientWallet.getId();
		Query query = session.createQuery(hql);
		List<PaymentsHistory> result = query.list();
		closeSession();
		return result;
	}

	public void payForOrder(Payment payment)
			throws NonExistingClientException, WalletAmountTooLowException, NonExistingOrderException {
		openSession();
		Wallet clientWallet = getClientWallet(payment.getClientId());
		double orderCost = getOrderCost(payment.getOrderId());
		double walletAmountAferPay = clientWallet.getAmount() - orderCost;
		if (walletAmountAferPay < 0) {
			throw new WalletAmountTooLowException(payment.getClientId(), clientWallet.getAmount());
		}
		addPaymentHistory(clientWallet.getId(), orderCost, "EUR", "PAY");
		Query query = session.createQuery("update Wallet set amount = :updateAmount" + " where wallet_Id = :wallet_Id");
		query.setParameter("updateAmount", walletAmountAferPay);
		query.setParameter("wallet_Id", clientWallet.getId());
		query.executeUpdate();

		Query orderQuery = session.createQuery("update Order set status = :status" + " where order_id = :order_id");
		orderQuery.setParameter("status", "PAID");
		orderQuery.setParameter("order_id", payment.getOrderId());
		orderQuery.executeUpdate();
		closeSession();
	}

	private double getOrderCost(long orderId) throws NonExistingOrderException {
		String hql = "FROM Order WHERE order_id =" + orderId;
		Query query = session.createQuery(hql);
		List<Order> result = query.list();

		if (result.isEmpty() || result.get(0).getStatus().equals("PAID")) {
			throw new NonExistingOrderException(orderId);
		} else {
			return result.get(0).getCost();
		}
	}

	public void addPaymentHistory(long walletId, double amount, String currency, String payment_type) {
		Date data = new Date();

		Query query = session
				.createSQLQuery("INSERT INTO Payments_History (wallet_Id, payment_time,amount,currency,payment_type) "
						+ "VALUES ( :wallet_Id, :payment_time,:amount,:currency,:payment_type)");
		query.setParameter("wallet_Id", walletId);
		query.setParameter("payment_time", new Timestamp(data.getTime()));
		query.setParameter("amount", amount);
		query.setParameter("currency", currency);
		query.setParameter("payment_type", payment_type);
		query.executeUpdate();
	}

	public void openSession() {
		if (session == null || !session.isOpen()) {

			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
		}
	}

	public void closeSession() {
		if (session != null && session.isOpen()) {

			session.getTransaction().commit();
			session.close();
		}
	}
}
