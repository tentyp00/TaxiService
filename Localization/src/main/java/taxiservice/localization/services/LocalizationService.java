package taxiservice.localization.services;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import taxiservice.localization.utils.Constants;

import java.io.IOException;

public class LocalizationService {

	OkHttpClient client = new OkHttpClient();

	public String run(String url) throws IOException {
		Request request = new Request.Builder()
				.url(url)
				.build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}

	public String getDistance(String originAddress, String destinationAddress) throws IOException, JSONException{
		String url = Constants.URL_REQUEST.replace("from", originAddress).replace("to", destinationAddress);
		String response = run(url);
		JSONObject json = new JSONObject(response);
		JSONObject result = new JSONObject();

		JSONArray arr = json.getJSONArray("rows");
		String status = ((JSONArray) arr.getJSONObject(0).get("elements")).getJSONObject(0).get("status").toString();

		if(status.equals("OK"))
		{JSONObject distance = (JSONObject) ((JSONArray) arr.getJSONObject(0).get("elements")).getJSONObject(0).get("distance");
			result.put("status",((JSONArray) arr.getJSONObject(0).get("elements")).getJSONObject(0).get("status"));
			result.put("originAddress", json.get("origin_addresses"));
			result.put("destinationAddress", json.get("destination_addresses"));
			result.put("distance", distance.get("text"));
		}
		else {
			result.put("status", status);
		}


		return result.toString();

	}

	public String getTravelTime(String originAddress, String destinationAddress) throws IOException, JSONException {
		String url = Constants.URL_REQUEST.replace("from", originAddress).replace("to", destinationAddress);
		String response = run(url);
		JSONObject json = new JSONObject(response);
		JSONObject result = new JSONObject();

		JSONArray arr = json.getJSONArray("rows");
		String status = ((JSONArray) arr.getJSONObject(0).get("elements")).getJSONObject(0).get("status").toString();

		if(status.equals("OK"))
		{JSONObject duration = (JSONObject) ((JSONArray) arr.getJSONObject(0).get("elements")).getJSONObject(0).get("duration");
			result.put("status",((JSONArray) arr.getJSONObject(0).get("elements")).getJSONObject(0).get("status"));
			result.put("originAddress", json.get("origin_addresses"));
			result.put("destinationAddress", json.get("destination_addresses"));
			result.put("duration", duration.get("text"));
		}
		else {
			result.put("status", status);
		}


		return result.toString();
	}


}
