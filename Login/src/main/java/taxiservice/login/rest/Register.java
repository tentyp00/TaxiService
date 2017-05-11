package taxiservice.login.rest;


import org.hibernate.Session;
import org.json.simple.JSONObject;
import taxiservice.login.dto.RegisterDataDTO;
import taxiservice.login.model.Client;
import taxiservice.login.model.SystemUser;
import taxiservice.login.model.Wallet;
import taxiservice.login.utils.HibernateUtil;
import taxiservice.login.utils.Password;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/register")
public class Register {

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerToApplication(RegisterDataDTO registerDataDTO)  {
		try {
			String computed_hash = Password.hashPassword(registerDataDTO.getPassword());

			SystemUser user = new SystemUser(registerDataDTO.getFirst_name(), registerDataDTO.getLast_name(),
					registerDataDTO.getEmail(), registerDataDTO.getLogin(),computed_hash, registerDataDTO.getJoin_date(), registerDataDTO.getPhone_number());

			Wallet clientWallet  = new Wallet( 0.0, "PLN", true);

			Client client = new Client(user, true, clientWallet);

			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(client);
			session.getTransaction().commit();
			session.close();

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("isSuccess", true);
			String response = "{ isSuccess: true }";
			return Response.status(200).entity(response).build();
			
			
		} catch (Exception  e) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("isSuccess", false);
			String response = "{ isSuccess: false }" + e;
			e.printStackTrace();
			
			return Response.status(500).entity(response).build();
		
		}
	}
}
