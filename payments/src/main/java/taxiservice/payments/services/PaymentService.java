package taxiservice.payments.services;

import org.hibernate.Session;

import taxiservice.payments.utils.HibernateUtil;

public class PaymentService {

	Session session;

	public double getCreditForClient(long clientId) {
		//openSession();
		//closeSession();
		return 10.0;
	}

	public void openSession() {
		if (!session.isOpen()) {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
		}
	}

	public void closeSession() {
		if (session.isOpen()) {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
		}
	}
}
