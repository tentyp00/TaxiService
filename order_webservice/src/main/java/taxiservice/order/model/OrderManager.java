package taxiservice.order.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import taxiservice.order.dto.*;
import taxiservice.order.exceptions.*;
import taxiservice.order.services.IOrderService;
import taxiservice.order.services.OrderService;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by monikanowakowicz on 13/05/2017.
 */
public class OrderManager {

    private static final double PAYMENT_FACTOR = 5.0;


    public Response order(Order order) {
        int userId = order.getUserId();

        if (userId == 0) {
            String response = "{ valid: false, reason:\"login\" }";
            return Response.status(400).entity(response).build();
        }

        try
        {
            IOrderService service = new OrderService();
            int orderId = service.createOrder(order);

            MuleClient client = new MuleClient(true);
            Map<String, Object> properties = new HashMap<String, Object>();
            Map<String, String> query = new HashMap<>();
            query.put("originAddress", order.getLocation_start());
            query.put("destinationAddress", order.getLocation_end());
            properties.put("Content-Type", "application/json");
            properties.put("http.method", "POST");
            properties.put("http.request.path", "/localization/taxiservice/localization/getDistance");
            properties.put("http.query.params", query);
//            MuleMessage result = client.send
//                    ("http://localhost:8081/localization/taxiservice/localization/getDistance?originAddress=" +order.getLocation_start()+ "&destinationAddress=" +order.getLocation_end(),null, properties);
            String url = "http://localhost:8081/localization/taxiservice/localization/getDistance?originAddress="+order.getLocation_start()+"&destinationAddress="+order.getLocation_end();
            MuleMessage result = client.send
                    (url,null, properties);


            String payload = result.getPayloadForLogging();
            double route = Double.valueOf(payload.replace(" km",""));

            setRouteLength(new OrderRouteDto(orderId, route));

            setOrderCost(orderId, route);
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
            e.printStackTrace();
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

            client.dispatch("http://localhost:8081/payment/taxiservice/payment/pay", paymentDetails, null);

            return Response.status(202).entity("Successfully ended").build();
        } catch (NonExistingOrderException e) {
            return CreateBadRequestWithMsgResponse(e.getMessage());
        } catch (NonExistingShiftException e) {
            return CreateBadRequestWithMsgResponse(e.getMessage());
        } catch (NotInProgressStatusException e) {
            return CreateBadRequestWithMsgResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
            return Response.status(500).build();
        }
    }

    private Response CreateBadRequestWithMsgResponse(String message) {
        return Response.status(400).entity(message).build();
    }

    public Response setOrderCost(int orderId, double routeLength) {
        if(routeLength == 0) {
            String response = "{ valid: false, reason:\"route null value\" }";
            return Response.status(400).entity(response).build();
        }
        double cost = routeLength * PAYMENT_FACTOR;
        try {
            IOrderService service = new OrderService();
            String responsedCost = service.setOrderCost(orderId,cost);

            JSONObject responseDetailsJson = new JSONObject();
            responseDetailsJson.put("cost", responsedCost);


            return Response.status(200).entity(responseDetailsJson.toString()).build();

        } catch (Exception e) {
            return Response.status(500).build();
        }

    }

    public Response setRouteLength(OrderRouteDto orderRouteDto) {
        if (orderRouteDto == null) {
            String response = "{ valid: false, reason:\"route null value\" }";
            return Response.status(400).entity(response).build();
        }

        try {
            IOrderService service = new OrderService();
            String route = service.setRouteLength(orderRouteDto);

            JSONObject responseDetailsJson = new JSONObject();
            responseDetailsJson.put("route", route);


            return Response.status(200).entity(responseDetailsJson.toString()).build();

        } catch (Exception e) {
            return Response.status(500).build();
        }


    }
}