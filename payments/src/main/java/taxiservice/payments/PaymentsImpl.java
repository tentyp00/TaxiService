package taxiservice.payments;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import taxiservice.payments.dto.ChargeAmount;
import taxiservice.payments.dto.Payment;
import taxiservice.payments.exceptions.NonExistingClientException;
import taxiservice.payments.exceptions.NonExistingOrderException;
import taxiservice.payments.exceptions.WalletAmountTooLowException;
import taxiservice.payments.models.PaymentsHistory;
import taxiservice.payments.services.PaymentService;
import taxiservice.payments.utils.Constants;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Path("/payment")
public class PaymentsImpl implements IPayments {

	PaymentService paymentService = new PaymentService();

	@SuppressWarnings("unchecked")
	@GET
	@Path("/getAccountCredit/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAccountStatus(@PathParam("param") long clinetId) {
		String response="";
		JSONObject responseDetailsJson = new JSONObject();
		try {
			response = paymentService.getCreditForClient(clinetId);
			
		} catch (NonExistingClientException e) {
			responseDetailsJson.put(Constants.ERROR, e.getMessage());
			return Response.status(400).entity(responseDetailsJson.toString()).build();
		} catch (JsonGenerationException e) {
			responseDetailsJson.put(Constants.ERROR, e.getClass().getCanonicalName());
			return Response.status(400).entity(responseDetailsJson.toString()).build();
		} catch (JsonMappingException e) {
			responseDetailsJson.put(Constants.ERROR, e.getClass().getCanonicalName());
			return Response.status(400).entity(responseDetailsJson.toString()).build();
		} catch (IOException e) {
			responseDetailsJson.put(Constants.ERROR, e.getClass().getCanonicalName());
			return Response.status(400).entity(responseDetailsJson.toString()).build();
		}catch (Exception e) {
			responseDetailsJson.put(Constants.ERROR, e.getClass().getCanonicalName());
			return Response.status(400).entity(responseDetailsJson.toString()).build();
		}
		return Response.status(200).entity(response).build();

	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/pay")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response pay(Payment payment) {
		String response;
		JSONObject responseDetailsJson = new JSONObject();
		try {
			response = paymentService.payForOrder(payment);	
			
		} catch (NonExistingClientException | WalletAmountTooLowException | NonExistingOrderException e) {
			responseDetailsJson.put(Constants.ERROR, e.getMessage());
			return Response.status(400).entity(responseDetailsJson.toString()).build();
		} catch (JsonGenerationException e) {
			responseDetailsJson.put(Constants.ERROR, e.getClass().getCanonicalName());
			return Response.status(400).entity(responseDetailsJson.toString()).build();
		} catch (JsonMappingException e) {
			responseDetailsJson.put(Constants.ERROR, e.getClass().getCanonicalName());
			return Response.status(400).entity(responseDetailsJson.toString()).build();
		} catch (IOException e) {
			responseDetailsJson.put(Constants.ERROR, e.getClass().getCanonicalName());
			return Response.status(400).entity(responseDetailsJson.toString()).build();
		}catch (Exception e) {
			responseDetailsJson.put(Constants.ERROR, e.getClass().getCanonicalName());
			e.printStackTrace();
			return Response.status(400).entity(responseDetailsJson.toString()).build();
		}
		return Response.status(200).entity(response).build();

	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/addCredit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCredit(ChargeAmount chargeAmount) {
		String response;
		JSONObject responseDetailsJson = new JSONObject();
		try {
			response = paymentService.addCreditForClient(chargeAmount);
		} catch (NonExistingClientException e) {
			responseDetailsJson.put(Constants.ERROR, e.getMessage());
			return Response.status(404).entity(responseDetailsJson.toString()).build();
		} catch (JsonGenerationException e) {
			responseDetailsJson.put(Constants.ERROR, e.getClass().getCanonicalName());
			return Response.status(400).entity(responseDetailsJson.toString()).build();
		} catch (JsonMappingException e) {
			responseDetailsJson.put(Constants.ERROR, e.getClass().getCanonicalName());
			return Response.status(400).entity(responseDetailsJson.toString()).build();
		} catch (IOException e) {
			responseDetailsJson.put(Constants.ERROR, e.getClass().getCanonicalName());
			return Response.status(400).entity(responseDetailsJson.toString()).build();
		} catch (Exception e) {
			responseDetailsJson.put(Constants.ERROR, e.getClass().getCanonicalName());
			return Response.status(400).entity(responseDetailsJson.toString()).build();
		}
		return Response.status(200).entity(response).build();
	}

	@SuppressWarnings("unchecked")
	@GET
	@Path("/getAccountHistory/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAccountHistory(@PathParam("param") long clientId) {
		List<PaymentsHistory> paymentsList;
		JSONObject responseDetailsJson = new JSONObject();
		try {
			paymentsList = paymentService.getPaymentsForClients(clientId);
		} catch (NonExistingClientException e) {
			responseDetailsJson.put(Constants.ERROR, e.getMessage());
			return Response.status(400).entity(responseDetailsJson.toString()).build();
		}catch (Exception e) {
			responseDetailsJson.put(Constants.ERROR, e.getClass().getCanonicalName());
			return Response.status(400).entity(responseDetailsJson.toString()).build();
		}

		
		JSONArray jsonArray = new JSONArray();
		
		int counter = 0;
		for (PaymentsHistory p : paymentsList) {
			counter++;
			JSONObject formDetailsJson = new JSONObject();
			formDetailsJson.put("paymentid", p.getId());
			formDetailsJson.put("wallet_id", p.getWallet().getId());
			Timestamp timestamp = p.getPayment_time();
			Date date = new Date(timestamp.getTime());
			formDetailsJson.put("payment_time", date);
			formDetailsJson.put("amount", p.getAmount());
			formDetailsJson.put("currency", p.getCurrency());
			formDetailsJson.put("payment_type", p.getPayment_type());

			jsonArray.add(formDetailsJson);
		}
		
		responseDetailsJson.put("size", counter);
		responseDetailsJson.put("paymentsHistory", jsonArray);

		return Response.status(200).entity(responseDetailsJson.toString()).build();
	}

}
