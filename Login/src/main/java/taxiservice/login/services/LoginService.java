package taxiservice.login.services;

import org.hibernate.Session;
import taxiservice.login.model.SystemUser;
import taxiservice.login.utils.HibernateUtil;

/**
 * Created by bartl on 09.05.2017.
 */
public class LoginService implements ILoginService {

    public SystemUser getUserLogin(String login) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String hql = "from SystemUser where login =" + login;

        SystemUser user = (SystemUser) session.createQuery(hql).list().get(0);
        session.getTransaction().commit();
        session.close();
        return user;
    }
}
