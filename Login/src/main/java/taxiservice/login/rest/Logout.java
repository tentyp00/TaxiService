package taxiservice.login.rest;

import taxiservice.login.model.LoginHistory;
import taxiservice.login.services.ILoginService;
import taxiservice.login.services.LoginService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by bartl on 13.05.2017.
 */
@Path("/logout")
public class Logout {

    ILoginService loginService = new LoginService();

    @POST
    @Path("/")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response logoutFromApplication(String userID) {

        try {
            List<LoginHistory> loginHistories = loginService.getLoginHistory(Long.valueOf(userID));

            Collections.sort(loginHistories, new Comparator<LoginHistory>() {
                public int compare(LoginHistory o1, LoginHistory o2) {
                    if (o1.getLogin_time() == null || o2.getLogin_time() == null)
                        return 0;
                    return o2.getLogin_time().compareTo(o1.getLogin_time());
                }
            });


            loginService.addLogoutHistory(loginHistories.get(0).getId(), Long.valueOf(userID));

            String response = "Logged out!";
            return Response.status(200).entity(response).build();
        } catch (Exception e) {
            String response = "Not logged out!";
            e.printStackTrace();

            return Response.status(500).entity(response).build();

        }


    }
}
