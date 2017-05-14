package taxiservice.login.rest;


import org.json.simple.JSONObject;
import taxiservice.login.dto.LoginDataDTO;
import taxiservice.login.exceptions.NonExistingUserException;
import taxiservice.login.model.SystemUser;
import taxiservice.login.services.ILoginService;
import taxiservice.login.services.LoginService;
import taxiservice.login.utils.Constants;
import taxiservice.login.utils.Password;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class Login {

    ILoginService loginService = new LoginService();
    SystemUser user = new SystemUser();

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginToApplication(LoginDataDTO loginData) {
        Boolean passOk = true;
        JSONObject jsonUser = new JSONObject();
        JSONObject responseJsonObject = new JSONObject();
        String login = loginData.getUserLogin();
        String pass = loginData.getUserPassword();

        try {
            user = loginService.getUserLogin(login);
            passOk = Password.checkPassword(pass, user.getPassword());

            if (!passOk) {
                String response = "{ valid: false, reason:\"password\" }";
                return Response.status(500).entity(response).build();
            }

        } catch (NonExistingUserException e) {
            responseJsonObject.put(Constants.ERROR, e.getMessage());
            return Response.status(404).entity(responseJsonObject.toString()).build();
        } catch (Exception e) {
            responseJsonObject.put(Constants.ERROR, e.getClass().getCanonicalName());
            e.printStackTrace();
            return Response.status(400).entity(responseJsonObject.toString()).build();
        }

        createUserJSON(jsonUser, user);
        loginService.addLoginHistory(user.getId());

        String response = "{ valid: true, systemUser:\"" + jsonUser.toJSONString() + "\" }";
        return Response.status(200).entity(response).build();
    }

    private void createUserJSON(JSONObject jsonUser, SystemUser user) {
        jsonUser.put("user_id", user.getId());
        jsonUser.put("first_name", user.getFirstName());
        jsonUser.put("last_name", user.getLastName());
        jsonUser.put("email", user.getEmail());
        jsonUser.put("login", user.getLogin());
        jsonUser.put("password", user.getPassword());
        jsonUser.put("join_date", user.getJoinDate());
        jsonUser.put("phone_number", user.getPhoneNumber());
    }

}