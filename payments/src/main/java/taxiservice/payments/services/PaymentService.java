package taxiservice.payments.services;

import org.hibernate.Query;
import org.hibernate.Session;
import taxiservice.payments.dto.ChargeAmount;
import taxiservice.payments.exceptions.NonExistingClientException;
import taxiservice.payments.models.Client;
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
        
        System.out.println(clientWallet.toString());
        
        double currentAmount = clientWallet.getAmount() + chargeAmount.getAmount();
        System.out.println("wchodze");
        addPaymentHistory(clientWallet.getId(), chargeAmount.getAmount(), chargeAmount.getCurrency(), chargeAmount.getPayment_type());
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

    public void payForOrder(long clientId, long orderId) {


    }

    public void addPaymentHistory(long walletId, double amount, String currency, String payment_type) {
        Date data = new Date();

        Query query = session.createSQLQuery("INSERT INTO Payments_History (wallet_Id, payment_time,amount,currency,payment_type) "
                + "VALUES ( :wallet_Id, :payment_time,:amount,:currency,:payment_type)");
        query.setParameter("wallet_Id", walletId);
        query.setParameter("payment_time", new Timestamp(data.getTime()));
        query.setParameter("amount", amount);
        query.setParameter("currency", currency);
        query.setParameter("payment_type", payment_type);
        query.executeUpdate();
    }

    public void openSession() {
    	System.out.println("session == null "+session == null?true:false);
    	if(session!=null)
    	{System.out.println("!session.isOpen() "+!session.isOpen());}
        if (session == null || !session.isOpen()) {
        	System.out.println(("otwieram"));
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
        }
    }

    public void closeSession() {
        if (session != null && session.isOpen()) {
        	System.out.println(("zamykam"));
            session.getTransaction().commit();
            session.close();
        }
    }
}
