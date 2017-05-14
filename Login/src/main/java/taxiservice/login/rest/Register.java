package taxiservice.login.rest;


import org.json.simple.JSONObject;
import taxiservice.login.dto.RegisterDataDTO;
import taxiservice.login.exceptions.AlreadyExistingEmailException;
import taxiservice.login.exceptions.AlreadyExistingLoginException;
import taxiservice.login.services.IRegisterService;
import taxiservice.login.services.RegisterService;
import taxiservice.login.utils.Constants;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/register")
public class Register {

	IRegisterService registerService = new RegisterService();

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerToApplication(RegisterDataDTO registerDataDTO) {
		JSONObject responseJsonObject = new JSONObject();
		try {
			registerService.addClient(registerDataDTO);

		} catch (AlreadyExistingEmailException e) {
			responseJsonObject.put(Constants.ERROR, e.getMessage());
			return Response.status(400).entity(responseJsonObject.toString()).build();
		}
		catch (AlreadyExistingLoginException e) {
			responseJsonObject.put(Constants.ERROR, e.getMessage());
			return Response.status(40).entity(responseJsonObject.toString()).build();
		}
		catch (Exception e) {
			responseJsonObject.put(Constants.ERROR, e.getClass().getCanonicalName());
			e.printStackTrace();
			return Response.status(400).entity(responseJsonObject.toString()).build();
		}
		responseJsonObject.put("isSuccess", true);
		return Response.status(200).entity(responseJsonObject.toString()).build();
	}
}
