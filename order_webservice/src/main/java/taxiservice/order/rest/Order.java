package taxiservice.order.rest;

import taxiservice.order.dto.CancelOrderDto;
import taxiservice.order.dto.EndTravelDto;
import taxiservice.order.dto.OrderAssignmentDto;
import taxiservice.order.model.OrderManager;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
    @Path("/getOrderDetails")
    public Response getOrderDetails() {
        return Response.status(200).build();
    }

    @GET
    @Path("/getOrders")
    public Response getOrders() {
        return Response.status(200).build();
    }

    @POST
    @Path("/cancelOrder")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cancelOrder(CancelOrderDto cancelOrderDto) {
        return getManager().cancelOrder(cancelOrderDto);
    }

    @POST
    @Path("/assignToOrder")
    public Response assignToOrder(OrderAssignmentDto assignmentDto) {
        return getManager().assignToOrder(assignmentDto);
    }

    @POST
    @Path("/endTravel")
    public Response endTravel(EndTravelDto endTravelDto) {
        return getManager().endTravel(endTravelDto);
    }

    public OrderManager getManager() {
        return new OrderManager();
    }
}
