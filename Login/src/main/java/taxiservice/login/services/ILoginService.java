package taxiservice.login.services;

import taxiservice.login.model.LoginHistory;
import taxiservice.login.model.SystemUser;

import java.util.List;

/**
 * Created by bartl on 09.05.2017.
 */
public interface ILoginService {

    SystemUser getUserLogin(String login);
    void addLoginHistory(long userID);
    void addLogoutHistory(long loginID, long userID);
    List<LoginHistory> getLoginHistory(long userID);
}
