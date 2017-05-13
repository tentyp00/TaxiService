package taxiservice.localization;

import javax.ws.rs.core.Response;

public interface ILocalization {

	Response getCurrentPosition();
	Response getDistance(String from, String to);
	Response getTravelTime(String from, String to);
	Response getAllDriversCoordinate();
}
