package taxiservice.order.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.mule.api.MuleContext;
import org.mule.api.config.ConfigurationBuilder;
import org.mule.api.context.MuleContextBuilder;
import org.mule.api.context.MuleContextFactory;
import org.mule.config.builders.DefaultsConfigurationBuilder;
import org.mule.config.spring.SpringXmlConfigurationBuilder;
import org.mule.context.DefaultMuleContextBuilder;
import org.mule.context.DefaultMuleContextFactory;
import org.mule.module.client.MuleClient;
import taxiservice.order.dto.CancelOrderDto;
import taxiservice.order.dto.EndTravelDto;
import taxiservice.order.dto.Order;
import taxiservice.order.dto.OrderAssignmentDto;
import taxiservice.order.exceptions.*;
import taxiservice.order.services.IOrderService;
import taxiservice.order.services.OrderService;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by monikanowakowicz on 13/05/2017.
 */
public class OrderManager {
    public Response order(Order order) {
        int userId = order.getUserId();

        if (userId == 0) {
            String response = "{ valid: false, reason:\"login\" }";
            return Response.status(400).entity(response).build();
        }

        try
        {
            IOrderService service = new OrderService();
            int orderId = service.createOrder(userId);
            String response = "{ orderId:\"" + orderId + "\" }";

            return Response.status(201).entity(response).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }

    public Response cancelOrder(CancelOrderDto cancelOrderDto) {
        if (cancelOrderDto.getOrderId() == 0 || cancelOrderDto.getUserId() == 0) {
            String response = "{ valid: false, reason:\"orderId\", \"userId\" }";
            return Response.status(400).entity(response).build();
        }

        try {
            IOrderService service = new OrderService();
            service.cancelOrder(cancelOrderDto);
            return Response.status(202).entity("Successfully cancelled").build();
        }
        catch (NotCancellableStatusException e) {
            return CreateBadRequestWithMsgResponse(e.getMessage());
        } catch (NonExistingOrderException e) {
            return CreateBadRequestWithMsgResponse(e.getMessage());
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

    public Response assignToOrder(OrderAssignmentDto assignmentDto) {
        if (assignmentDto.getOrderId() == 0 || assignmentDto.getShiftId() == 0 || assignmentDto.getDriverId() == 0) {
            String response = "{ valid: false, reason:\"orderId\", \"shiftId\", \"driverId\" }";
            return Response.status(400).entity(response).build();
        }

        try {
            IOrderService service = new OrderService();
            service.assignOrder(assignmentDto.getOrderId(), assignmentDto.getShiftId(), assignmentDto.getDriverId());
            return Response.status(202).entity("Successfully assigned").build();
        } catch (NonExistingShiftException e) {
            return CreateBadRequestWithMsgResponse(e.getMessage());
        } catch (NonExistingOrderException e) {
            return CreateBadRequestWithMsgResponse(e.getMessage());
        } catch (NotAssignableStatusException e) {
            return CreateBadRequestWithMsgResponse(e.getMessage());
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

    public Response endTravel(EndTravelDto endTravelDto) {
        if (endTravelDto.getOrderId() == 0 ||
                endTravelDto.getShiftId() == 0 ||
                endTravelDto.getDriverId() == 0) {
            String response = "{ valid: false, reason:\"orderId\", \"shiftId\", \"driverId\" }";
            return Response.status(400).entity(response).build();
        }

        try {
            IOrderService service = new OrderService();
            String paymentDetails = service.endOrderTravel(endTravelDto.getOrderId(), endTravelDto.getShiftId(), endTravelDto.getDriverId());

            MuleClient client = new MuleClient(true);
            // client.request()
            client.dispatch("http://localhost:8081", paymentDetails,null);

            return Response.status(202).entity("Successfully ended").build();
        } catch (NonExistingOrderException e) {
            return CreateBadRequestWithMsgResponse(e.getMessage());
        } catch (NonExistingShiftException e) {
            return CreateBadRequestWithMsgResponse(e.getMessage());
        } catch (NotInProgressStatusException e) {
            return CreateBadRequestWithMsgResponse(e.getMessage());
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

    public Response getOrderDetails(int orderId, int clientId) {
        if (orderId == 0 || clientId == 0) {
        String response = "{ valid: false, reason:\"orderId\", \"clientId\" }";
        return Response.status(400).entity(response).build();
    }

        try {
            IOrderService service = new OrderService();
            OrdersEntity order = service.getOrderDetails(orderId, clientId);

            JSONObject responseDetailsJson = new JSONObject();
            responseDetailsJson.put("orderId", order.getOrderId());
            responseDetailsJson.put("status", order.getStatus());
            responseDetailsJson.put("cost", order.getCost());

            return Response.status(200).entity(responseDetailsJson.toString()).build();
        } catch (NonExistingOrderException e) {
            return CreateBadRequestWithMsgResponse(e.getMessage());
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

    public Response getOrders(int driverId) {
        if (driverId == 0) {
            String response = "{ valid: false, reason:\"driverId\" }";
            return Response.status(400).entity(response).build();
        }

        try {
            IOrderService service = new OrderService();
            List<OrdersEntity> orders = service.getOrders(driverId);

            JSONObject responseObj = new JSONObject();
            JSONArray jsonArray = new JSONArray();

            int counter = 0;
            for (OrdersEntity o : orders) {
                counter++;
                JSONObject formDetailsJson = new JSONObject();
                formDetailsJson.put("orderId", o.getOrderId());
                formDetailsJson.put("startLocation", o.getLocationStart());

                jsonArray.add(formDetailsJson);
            }

            responseObj.put("size", counter);
            responseObj.put("orders", jsonArray);

            return Response.status(200).entity(responseObj.toString()).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

    private Response CreateBadRequestWithMsgResponse(String message) {
        return Response.status(400).entity(message).build();
    }
}