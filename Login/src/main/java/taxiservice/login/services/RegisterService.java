package taxiservice.login.services;

import org.hibernate.Query;
import org.hibernate.Session;
import taxiservice.login.dto.RegisterDataDTO;
import taxiservice.login.exceptions.AlreadyExistingEmailException;
import taxiservice.login.exceptions.AlreadyExistingLoginException;
import taxiservice.login.model.Client;
import taxiservice.login.model.SystemUser;
import taxiservice.login.model.Wallet;
import taxiservice.login.utils.HibernateUtil;
import taxiservice.login.utils.Password;

import java.util.List;

/**
 * Created by bartl on 14.05.2017.
 */
public class RegisterService implements IRegisterService {

    Session session;
    List<SystemUser> users;
    Query query;

    public void addClient(RegisterDataDTO registerDataDTO) throws AlreadyExistingLoginException, AlreadyExistingEmailException {

        String computed_hash = Password.hashPassword(registerDataDTO.getPassword());

        SystemUser user = new SystemUser(registerDataDTO.getFirst_name(), registerDataDTO.getLast_name(),
                registerDataDTO.getEmail(), registerDataDTO.getLogin(), computed_hash, registerDataDTO.getJoin_date(), registerDataDTO.getPhone_number());

        openSession();
        query = session.createQuery("from SystemUser where login = :login");
        query.setParameter("login", registerDataDTO.getLogin());
        users = query.list();
        if (!users.isEmpty()) {
            throw new AlreadyExistingLoginException(registerDataDTO.getLogin());
        }

        query = session.createQuery("from SystemUser where email = :email");
        query.setParameter("email", registerDataDTO.getEmail());
        users = query.list();
        if (!users.isEmpty()) {
            throw new AlreadyExistingEmailException(registerDataDTO.getEmail());
        }

        Wallet clientWallet = new Wallet(0.0, "PLN", true);
        Client client = new Client(user, true, clientWallet);

        session.save(client);
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
