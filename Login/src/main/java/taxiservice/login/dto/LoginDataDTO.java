package taxiservice.login.dto;

/**
 * Created by bartl on 07.05.2017.
 */
public class LoginDataDTO {

    String login;
    String password;

    public LoginDataDTO(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getUserLogin() {
        return login;
    }

    public void setUserLogin(String login) {
        this.login = login;
    }

    public String getUserPassword() {
        return password;
    }

    public void setUserPassword(String password) {
        this.password = password;
    }
}
