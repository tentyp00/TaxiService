package taxiservice.payments;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import taxiservice.payments.models.Payment;
import taxiservice.payments.services.PaymentService;

@Path("/payment")
public class PaymentsImpl implements IPayments {

	PaymentService paymentService= new PaymentService();
	
	@GET
	@Path("/getAccountStatus/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAccountStatus(@PathParam("param") long clinetId){
		Double credits = paymentService.getCreditForClient(10);
		return Response.status(200).entity(credits.toString()).build();
		
	}

	@POST
	@Path("/pay")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response pay(Payment payment) {
		
		return Response.status(200).entity(payment.toString()).build();
	}

	@POST
	@Path("/addCredit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCredit(long clientId, double amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@GET
	@Path("/getAccountHistory/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAccountHistory(long clientId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
