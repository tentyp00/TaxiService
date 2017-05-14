package taxiservice.login.services;

import org.hibernate.Query;
import org.hibernate.Session;
import taxiservice.login.exceptions.NonExistingUserException;
import taxiservice.login.model.LoginHistory;
import taxiservice.login.model.SystemUser;
import taxiservice.login.utils.HibernateUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by bartl on 09.05.2017.
 */
public class LoginService implements ILoginService {

    Session session;

    public SystemUser getUserLogin(String login) throws NonExistingUserException {
        openSession();
        Query query = session.createQuery("from SystemUser where login = :login");
        query.setParameter("login", login);
        List<SystemUser> users = query.list();
        if (users.isEmpty()) {
            throw new NonExistingUserException(login);
        }
        closeSession();
        return users.get(0);

    }

    public void addLoginHistory(long userID) {

        Date data = new Date();
        openSession();
        Query query = session.createSQLQuery("INSERT INTO taxiservice.login_history (user_id, login_time) "
                + "VALUES ( :user_id, :login_time)");
        query.setParameter("user_id", userID);
        query.setParameter("login_time", new Timestamp(data.getTime()));
        query.executeUpdate();
        closeSession();
    }

    public void addLogoutHistory(long loginID, long userID) {

        Date data = new Date();
        openSession();
        Query query = session.createQuery("UPDATE LoginHistory set logout_time = :logout_time " +
                "where login_id = :loginID and user_id = :userID");
        query.setParameter("loginID", loginID);
        query.setParameter("userID", userID);
        query.setParameter("logout_time", new Timestamp(data.getTime()));
        query.executeUpdate();
        closeSession();
    }

    public List<LoginHistory> getLoginHistory(long userID) {
        openSession();
        Query query = session.createQuery("from LoginHistory where user_id = :userID");
        query.setParameter("userID", userID);
        List<LoginHistory> loginHistories = query.list();
        closeSession();
        return loginHistories;
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
