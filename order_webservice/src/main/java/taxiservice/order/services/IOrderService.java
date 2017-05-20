package taxiservice.order.services;

import taxiservice.order.dto.CancelOrderDto;
import taxiservice.order.exceptions.*;
import taxiservice.order.model.OrdersEntity;

import java.util.List;

/**
 * Created by monikanowakowicz on 13/05/2017.
 */
public interface IOrderService {
    int createOrder(int userID);

    void cancelOrder(CancelOrderDto cancelOrderDto) throws NonExistingOrderException, NotCancellableStatusException;

    void assignOrder(int orderId, int shiftId, int driverId) throws NonExistingOrderException, NonExistingShiftException, NotAssignableStatusException;

    String endOrderTravel(int orderId, int shiftId, int driverId) throws NonExistingOrderException, NonExistingShiftException, NotInProgressStatusException;

    OrdersEntity getOrderDetails(int orderId, int clientId) throws NonExistingOrderException;

    List<OrdersEntity> getOrders(int driverId);
}
