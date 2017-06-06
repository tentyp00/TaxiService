package taxiservice.order.rest;

import taxiservice.order.dto.CancelOrderDto;
import taxiservice.order.dto.EndTravelDto;
import taxiservice.order.dto.OrderAssignmentDto;
import taxiservice.order.dto.OrderRouteDto;
import taxiservice.order.model.OrderManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by monikanowakowicz on 13/05/2017.
 */
@Path("/order")
public class Order {
    private OrderManager Manager;


    @POST
    @Path("/order")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response order(taxiservice.order.dto.Order orderDto) {
        return getManager().order(orderDto);
    }

    @GET
    @Path("/getOrderDetails/{orderId}/{clientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderDetails(@PathParam("orderId") int orderId, @PathParam("clientId") int clientId) {
        return getManager().getOrderDetails(orderId, clientId);
    }

    @GET
    @Path("/getOrders/{driverId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrders(@PathParam("driverId") int driverId) {
        return getManager().getOrders(driverId);
    }

    @POST
    @Path("/cancelOrder")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cancelOrder(CancelOrderDto cancelOrderDto) {
        return getManager().cancelOrder(cancelOrderDto);
    }

    @POST
    @Path("/assignToOrder")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response assignToOrder(OrderAssignmentDto assignmentDto) {
        return getManager().assignToOrder(assignmentDto);
    }

    @POST
    @Path("/assignRouteLength")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response assignRouteLength(OrderRouteDto orderRouteDto) {
        return getManager().setRouteLength(orderRouteDto);
    }

    @POST
    @Path("/endTravel")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response endTravel(EndTravelDto endTravelDto) {
        return getManager().endTravel(endTravelDto);
    }

    private OrderManager getManager() {
        return new OrderManager();
    }
}
