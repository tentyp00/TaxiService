package taxiservice.order.services;

import taxiservice.order.dto.CancelOrderDto;
import taxiservice.order.exceptions.*;

/**
 * Created by monikanowakowicz on 13/05/2017.
 */
public interface IOrderService {
    int createOrder(int userID);

    void cancelOrder(CancelOrderDto cancelOrderDto) throws NonExistingOrderException, NotCancellableStatusException;

    void assignOrder(int orderId, int shiftId, int driverId) throws NonExistingOrderException, NonExistingShiftException, NotAssignableStatusException;

    void endOrderTravel(int orderId, int shiftId, int driverId) throws NonExistingOrderException, NonExistingShiftException, NotInProgressStatusException;
}
