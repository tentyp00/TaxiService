package taxiservice.order.model;

import org.json.simple.JSONObject;
import taxiservice.order.dto.CancelOrderDto;
import taxiservice.order.dto.EndTravelDto;
import taxiservice.order.dto.Order;
import taxiservice.order.dto.OrderAssignmentDto;
import taxiservice.order.exceptions.NonExistingOrderException;
import taxiservice.order.exceptions.NonExistingShiftException;
import taxiservice.order.exceptions.NotCancellableStatusException;
import taxiservice.order.exceptions.NotInProgressStatusException;
import taxiservice.order.services.IOrderService;
import taxiservice.order.services.OrderService;

import javax.ws.rs.core.Response;

/**
 * Created by monikanowakowicz on 13/05/2017.
 */
public class OrderManager {
    public Response order(Order order) {
        int userId = order.getUserId();

        if (userId == 0) {
            String response = "{ valid: false, reason:\"login\" }";
            return Response.status(500).entity(response).build();
        }

        try
        {
            IOrderService service = new OrderService();
            int orderId = service.createOrder(userId);
            String response = "{ orderId:\"" + orderId + "\" }";

            return Response.status(200).entity(response).build();
        }
        catch (Exception e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("validLogin", false);
            String response = "{ valid: false, reason:\"other\" }";
            e.printStackTrace();

            return Response.status(500).entity(response).build();
        }
    }

    public Response cancelOrder(CancelOrderDto cancelOrderDto) {
        if (cancelOrderDto.getOrderId() == 0 || cancelOrderDto.getUserId() == 0) {
            String response = "{ valid: false, reason:\"parameters\" }";
            return Response.status(500).entity(response).build();
        }

        try {
            IOrderService service = new OrderService();
            service.cancelOrder(cancelOrderDto);
            return Response.status(200).entity("Successfully cancelled").build();
        }
        catch (NotCancellableStatusException e) {
            e.printStackTrace();
        } catch (NonExistingOrderException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Response assignToOrder(OrderAssignmentDto assignmentDto) {
        if (assignmentDto.getOrderId() == 0 || assignmentDto.getShiftId() == 0 || assignmentDto.getDriverId() == 0) {
            String response = "{ valid: false, reason:\"parameters\" }";
            return Response.status(500).entity(response).build();
        }

        try {
            IOrderService service = new OrderService();
            service.assignOrder(assignmentDto.getOrderId(), assignmentDto.getShiftId(), assignmentDto.getDriverId());
            return Response.status(200).entity("Successfully assigned").build();
        } catch (NonExistingShiftException e) {
            e.printStackTrace();
        } catch (NonExistingOrderException e) {
            e.printStackTrace();
        } catch (Exception e) {

        }

        return null;
    }

    public Response endTravel(EndTravelDto endTravelDto) {
        if (endTravelDto.getOrderId() == 0 ||
                endTravelDto.getShiftId() == 0 ||
                endTravelDto.getDriverId() == 0) {
            String response = "{ valid: false, reason:\"parameters\" }";
            return Response.status(500).entity(response).build();
        }

        try {
            IOrderService service = new OrderService();
            service.endOrderTravel(endTravelDto.getOrderId(), endTravelDto.getShiftId(), endTravelDto.getDriverId());
            return Response.status(200).entity("Successfully ended").build();
        } catch (NonExistingOrderException e) {
            e.printStackTrace();
        } catch (NonExistingShiftException e) {
            e.printStackTrace();
        } catch (NotInProgressStatusException e) {
            e.printStackTrace();
        }

        return null;
    }
}
