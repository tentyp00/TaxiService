package taxiservice.login.services;

import org.hibernate.Query;
import org.hibernate.Session;
import taxiservice.login.model.SystemUser;
import taxiservice.login.utils.HibernateUtil;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by bartl on 09.05.2017.
 */
public class LoginService implements ILoginService {

    Session session;

    public SystemUser getUserLogin(String login) {
        openSession();
        Query query = session.createQuery("from SystemUser where login = :login");
        query.setParameter("login", login);
        SystemUser user = (SystemUser) query.list().get(0);
        closeSession();
        return user;
    }

    public void addLoginHistory(long userID) {

        Date data = new Date();
        openSession();
        Query query = session.createSQLQuery("INSERT INTO Login_History (user_id, login_time, logout_time) "
                + "VALUES ( :user_id, :login_time,:logout_time)");
        query.setParameter("user_id", userID);
        query.setParameter("login_time", new Timestamp(data.getTime()));
        query.setParameter("logout_time", null);
        query.executeUpdate();
        closeSession();
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
