package taxiservice.payments.services;

import org.hibernate.Query;
import org.hibernate.Session;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import taxiservice.payments.dto.ChargeAmount;
import taxiservice.payments.dto.Payment;
import taxiservice.payments.exceptions.NonExistingClientException;
import taxiservice.payments.exceptions.NonExistingOrderException;
import taxiservice.payments.exceptions.WalletAmountTooLowException;
import taxiservice.payments.models.Client;
import taxiservice.payments.models.Order;
import taxiservice.payments.models.PaymentsHistory;
import taxiservice.payments.models.Wallet;
import taxiservice.payments.responses.AccountCreditResponse;
import taxiservice.payments.responses.PaymentResponse;
import taxiservice.payments.utils.Constants;
import taxiservice.payments.utils.HibernateUtil;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class PaymentService {

	Session session;

	@SuppressWarnings("unchecked")
	public String getCreditForClient(long clientId)
			throws NonExistingClientException, JsonGenerationException, JsonMappingException, IOException {
		AccountCreditResponse response = new AccountCreditResponse();
		ObjectMapper mapper = new ObjectMapper();
		openSession();
		String hql = "FROM Client WHERE client_Id =" + clientId;
		Query query = session.createQuery(hql);
		List<Client> result = query.list();
		if (result.isEmpty()) {
			throw new NonExistingClientException(clientId);
		}
		response.setFirstName(result.get(0).getUser().getFirstName());
		response.setLastName(result.get(0).getUser().getLastName());
		response.setAmount(result.get(0).getWallet().getAmount());
		response.setClientId(clientId);
		String jsonInString = mapper.writeValueAsString(response);
		closeSession();
		return jsonInString;

	}

	@SuppressWarnings("unchecked")
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

	public String addCreditForClient(ChargeAmount chargeAmount) throws NonExistingClientException, JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		openSession();
		Wallet clientWallet = getClientWallet(chargeAmount.getClientId());

		double currentAmount = clientWallet.getAmount() + chargeAmount.getAmount();
		addPaymentHistory(clientWallet.getId(), chargeAmount.getAmount(), chargeAmount.getCurrency(),
				chargeAmount.getPayment_type());
		Query query = session.createQuery("update Wallet set amount = :updateAmount" + " where wallet_Id = :wallet_Id");
		query.setParameter("updateAmount", currentAmount);
		query.setParameter("wallet_Id", clientWallet.getId());
		query.executeUpdate();
		
		PaymentResponse response = setResponse(chargeAmount.getClientId(),null, clientWallet, chargeAmount.getAmount(),Constants.PAYMENT_TYPE_ADD);
		String jsonInString = mapper.writeValueAsString(response);
		closeSession();
		return jsonInString;
	}

	@SuppressWarnings("unchecked")
	public List<PaymentsHistory> getPaymentsForClients(long clientId) throws NonExistingClientException {
		openSession();
		Wallet clientWallet = getClientWallet(clientId);
		String hql = "FROM PaymentsHistory WHERE wallet =" + clientWallet.getId();
		Query query = session.createQuery(hql);
		List<PaymentsHistory> result = query.list();
		closeSession();
		return result;
	}

	public String payForOrder(Payment payment) throws NonExistingClientException, WalletAmountTooLowException,
			NonExistingOrderException, JsonGenerationException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		openSession();
		Wallet clientWallet = getClientWallet(payment.getClientId());
		double orderCost = getOrderCost(payment.getOrderId());
		double walletAmountAferPay = clientWallet.getAmount() - orderCost;
		if (walletAmountAferPay < 0) {
			throw new WalletAmountTooLowException(payment.getClientId(), clientWallet.getAmount());
		}
		addPaymentHistory(clientWallet.getId(), orderCost, Constants.CURRENCY, Constants.PAYMENT_TYPE_PAY);
		Query query = session.createQuery("update Wallet set amount = :updateAmount" + " where wallet_Id = :wallet_Id");
		query.setParameter("updateAmount", walletAmountAferPay);
		query.setParameter("wallet_Id", clientWallet.getId());
		query.executeUpdate();

		Query orderQuery = session.createQuery("update Order set status = :status" + " where order_id = :order_id");
		orderQuery.setParameter("status", Constants.PAID);
		orderQuery.setParameter("order_id", payment.getOrderId());
		orderQuery.executeUpdate();

		PaymentResponse response = setResponse(payment.getClientId(),payment.getOrderId(), clientWallet, orderCost,Constants.PAYMENT_TYPE_PAY);
		String jsonInString = mapper.writeValueAsString(response);
		closeSession();
		return jsonInString;
	}

	private PaymentResponse setResponse(long clientId,Long orderId, Wallet clientWallet, double orderCost,String type) {
		Client client = (Client) session.get(Client.class, clientId);
		PaymentResponse response = new PaymentResponse();
		response.setFirstName(client.getUser().getFirstName());
		response.setLastName(client.getUser().getLastName());
		response.setAmount(clientWallet.getAmount());
		response.setClientId(clientId);
		response.setOrderId(orderId);
		response.setPaymentType(type);
		response.setPaymentValue(orderCost);
		return response;
	}

	private double getOrderCost(long orderId) throws NonExistingOrderException {
		String hql = "FROM Order WHERE order_id =" + orderId;
		Query query = session.createQuery(hql);
		List<Order> result = query.list();

		if (result.isEmpty() || !result.get(0).getStatus().equals(Constants.TO_PAY)) {
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
