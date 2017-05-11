package taxiservice.login.services;

import taxiservice.login.model.SystemUser;

/**
 * Created by bartl on 09.05.2017.
 */
public interface ILoginService {

    SystemUser getUserLogin(String login);
}
