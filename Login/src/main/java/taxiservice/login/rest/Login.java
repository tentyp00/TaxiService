package taxiservice.login.rest;


import org.json.simple.JSONObject;
import taxiservice.login.dto.LoginDataDTO;
import taxiservice.login.model.SystemUser;
import taxiservice.login.services.ILoginService;
import taxiservice.login.services.LoginService;
import taxiservice.login.utils.Password;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

@Path("/login")
public class Login {

	ILoginService loginService = new LoginService();

	@GET
	@Path("/verify")
	@Produces(MediaType.TEXT_PLAIN)
	public Response verifyRESTService(InputStream incomingData) {
		String result = "It works!";
		return Response.status(200).entity(result).build();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response loginToApplication(LoginDataDTO loginData) {
		Boolean passOk = false;
		JSONObject jsonUser = new JSONObject();
		String login = loginData.getUserLogin();
		String pass = loginData.getUserPassword();
		try {
			SystemUser user = loginService.getUserLogin(login);
			if (user != null) {
				passOk = Password.checkPassword(pass, user.getPassword());
			} else {
				String response = "{ valid: false, reason:\"login\" }";
				return Response.status(500).entity(response).build();
			}
			if (passOk) {
				jsonUser.put("userid", user.getId());
				jsonUser.put("first_name", user.getFirstName());
				jsonUser.put("last_name", user.getLastName());
				jsonUser.put("email", user.getEmail());
				jsonUser.put("login", user.getLogin());
				jsonUser.put("password", user.getPassword());
				jsonUser.put("join_date", user.getJoinDate());
				jsonUser.put("phone_number", user.getPhoneNumber());

				String response = "{ valid: true, systemUser:\"" + jsonUser.toJSONString() + "\" }";
				return Response.status(200).entity(response).build();
			} else {
				String response = "{ valid: false, reason:\"password\" }";
				return Response.status(500).entity(response).build();
			}

		} catch (Exception e) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("validLogin", false);
			String response = "{ valid: false, reason:\"other\" }";

			return Response.status(500).entity(response).build();

		}
	}

}