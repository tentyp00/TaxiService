package taxiservice.login.dto;

/**
 * Created by bartl on 07.05.2017.
 */
public class LoginDataDTO {

    String userLogin;
    String userPassword;

    public LoginDataDTO() {
    }

    public LoginDataDTO(String userLogin, String userPassword) {
        this.userLogin = userLogin;
        this.userPassword = userPassword;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
