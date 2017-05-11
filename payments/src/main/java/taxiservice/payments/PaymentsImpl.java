package taxiservice.payments;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import taxiservice.payments.dto.ChargeAmount;
import taxiservice.payments.dto.Payment;
import taxiservice.payments.exceptions.NonExistingClientException;
import taxiservice.payments.models.PaymentsHistory;
import taxiservice.payments.services.PaymentService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Path("/payment")
public class PaymentsImpl implements IPayments {

    PaymentService paymentService = new PaymentService();

    @SuppressWarnings("unchecked")
    @GET
    @Path("/getAccountStatus/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountStatus(@PathParam("param") long clinetId) {
        double credits;
        JSONObject responseDetailsJson = new JSONObject();
        try {
            credits = paymentService.getCreditForClient(clinetId);
            responseDetailsJson.put("credits", credits);
        } catch (NonExistingClientException e) {
            responseDetailsJson.put("error", e.getMessage());
            return Response.status(400).entity(responseDetailsJson.toString()).build();

        }
        return Response.status(200).entity(responseDetailsJson.toString()).build();

    }

    @POST
    @Path("/pay")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response pay(Payment payment) {

        return Response.status(200).entity(payment.toString()).build();
    }

    @SuppressWarnings("unchecked")
    @POST
    @Path("/addCredit")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCredit(ChargeAmount chargeAmount) {
        double currentAmount;
        JSONObject responseDetailsJson = new JSONObject();
        try {
            currentAmount = paymentService.addCreditForClient(chargeAmount);
        } catch (NonExistingClientException e) {
            responseDetailsJson.put("error", e.getMessage());
            return Response.status(404).entity(responseDetailsJson.toString()).build();
        }

        responseDetailsJson.put("CurrentAmount", currentAmount);
        return Response.status(200).entity(responseDetailsJson.toString()).build();
    }

    @SuppressWarnings("unchecked")
    @GET
    @Path("/getAccountHistory/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountHistory(@PathParam("param") long clientId) {
        List<PaymentsHistory> paymentsList;
        try {
            paymentsList = paymentService.getPaymentsForClients(clientId);
        } catch (NonExistingClientException e) {
            return Response.status(404).entity(e.getMessage()).build();
        }

        JSONObject responseDetailsJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (PaymentsHistory p : paymentsList) {
            JSONObject formDetailsJson = new JSONObject();
            formDetailsJson.put("paymentid", p.getPaymentId());
            formDetailsJson.put("wallet_id", p.getWallet().getWalletid());
            Timestamp timestamp = p.getPayment_time();
            Date date = new Date(timestamp.getTime());
            formDetailsJson.put("payment_time", date);
            formDetailsJson.put("amount", p.getAmount());
            formDetailsJson.put("currency", p.getCurrency());
            formDetailsJson.put("payment_type", p.getPayment_type());

            jsonArray.add(formDetailsJson);
        }
        responseDetailsJson.put("paymentsHistory", jsonArray);

        return Response.status(200).entity(responseDetailsJson.toString()).build();
    }

}