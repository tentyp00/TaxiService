package taxiservice.localization;

import org.codehaus.jettison.json.JSONException;
import org.json.simple.JSONObject;
import taxiservice.localization.services.LocalizationService;
import taxiservice.localization.utils.Constants;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/localization")
public class LocalizationImpl implements ILocalization{

	LocalizationService localizationService = new LocalizationService();
	public Response getCurrentPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@GET
	@Path("/getDistance")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDistance(@QueryParam("originAddress")String from, @QueryParam("destinationAddress")String to) {
		JSONObject responseDetailsJson= new JSONObject();
		String distance="";
		try {
			distance = localizationService.getDistance(from, to);
		}  catch (IOException e) {
			responseDetailsJson.put(Constants.ERROR, e.getClass().getSimpleName());
			return Response.status(400).entity(responseDetailsJson.toString()).build();
		} catch (JSONException e) {
			responseDetailsJson.put(Constants.ERROR, e.getClass().getSimpleName());
			return Response.status(400).entity(responseDetailsJson.toString()).build();
		}
		return Response.status(200).entity(distance).build();
	}
	@SuppressWarnings("unchecked")
	@GET
	@Path("/getTravelTime")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTravelTime(@QueryParam("originAddress")String from, @QueryParam("destinationAddress")String to) {
		JSONObject responseDetailsJson= new JSONObject();
		String travelTime="";
		try {
			travelTime = localizationService.getTravelTime(from, to);
		}  catch (IOException e) {
			responseDetailsJson.put(Constants.ERROR, e.getClass().getSimpleName());
			return Response.status(400).entity(responseDetailsJson.toString()).build();
		} catch (JSONException e) {
			responseDetailsJson.put(Constants.ERROR, e.getClass().getSimpleName());
			return Response.status(400).entity(responseDetailsJson.toString()).build();
		}
		return Response.status(200).entity(travelTime).build();
	}

	public Response getAllDriversCoordinate() {
		// TODO Auto-generated method stub
		return null;
	}

}
