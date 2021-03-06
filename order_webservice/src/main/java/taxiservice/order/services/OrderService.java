package taxiservice.order.services;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import taxiservice.order.dto.CancelOrderDto;
import taxiservice.order.dto.Order;
import taxiservice.order.dto.OrderRouteDto;
import taxiservice.order.exceptions.*;
import taxiservice.order.model.OrdersEntity;
import taxiservice.order.model.ShiftsEntity;
import taxiservice.order.utils.Constants;
import taxiservice.order.utils.HibernateUtil;

import java.util.List;

/**
 * Created by monikanowakowicz on 13/05/2017.
 */
public class OrderService implements IOrderService {
    Session session;

    public int createOrder(Order order) {
        openSession();
        OrdersEntity entity = new OrdersEntity();
        //entity.setShiftId(1); // TODO: set as non mandatory
        entity.setClientId(order.getUserId());
        entity.setLocationStart(order.getLocation_start());
        entity.setLocationEnd(order.getLocation_end());
        entity.setStatus(Constants.ORDERED);
        session.save(entity);
        closeSession();
        return entity.getOrderId();
    }

    public void cancelOrder(CancelOrderDto cancelOrderDto) throws NonExistingOrderException, NotCancellableStatusException {
        openSession();
        OrdersEntity order = getOrder(cancelOrderDto.getUserId(), cancelOrderDto.getOrderId());

        if (order.getStatus() == null || !order.getStatus().equals(Constants.ORDERED))
        {
            throw new NotCancellableStatusException();
        }

        order.setStatus(Constants.CANCELLED);
        session.save(order);
        closeSession();
    }

    public void assignOrder(int orderId, int shiftId, int driverId) throws NonExistingOrderException, NonExistingShiftException, NotAssignableStatusException {
        openSession();
        OrdersEntity order = getOrder(orderId);
        ShiftsEntity shift = getShiftId(shiftId, driverId);

        if (order.getStatus() == null || !order.getStatus().equals(Constants.ORDERED))
        {
            throw new NotAssignableStatusException();
        }

        order.setStatus(Constants.INPROGRESS);
        order.setShiftId(shift);
        session.save(order);

        closeSession();
    }

    @Override
    public String endOrderTravel(int orderId, int shiftId, int driverId) throws NonExistingOrderException, NonExistingShiftException, NotInProgressStatusException {

        openSession();
        OrdersEntity order = getOrderByShiftId(orderId, shiftId);
        getShiftId(shiftId, driverId);

        if (order.getStatus() == null || !order.getStatus().equals(Constants.INPROGRESS))
        {
            throw new NotInProgressStatusException();
        }

        order.setStatus(Constants.TOPAY);
        session.save(order);

        closeSession();

        return "{\"clientId\": "+ order.getClientId() +",\n" +
                "\"orderId\": "+ order.getOrderId() + "}";
    }

    @Override
    public OrdersEntity getOrderDetails(int orderId, int clientId) throws NonExistingOrderException {
        openSession();
        OrdersEntity order = getOrder(orderId, clientId);
        closeSession();
        return order;
    }

    @Override
    public List<OrdersEntity> getOrders(int driverId) {
        openSession();
        List<OrdersEntity> orders = getOrdersForDriver(driverId);
        closeSession();
        return orders;
    }

    @Override
    public String setRouteLength(OrderRouteDto orderRouteDto) {
        openSession();
        Query orderQuery = session.createQuery("update OrdersEntity set routeLength = :routeLength" + " where orderId = :order_id");
        orderQuery.setParameter("routeLength", orderRouteDto.getRouteLength());
        orderQuery.setParameter("order_id", orderRouteDto.getOrderId());
        orderQuery.executeUpdate();
        closeSession();

        return "{\"route_length\": "+ orderRouteDto.getRouteLength() +",\n" +
                "\"orderId\": "+ orderRouteDto.getOrderId() + "}";
    }

    @Override
    public String setOrderCost(int orderId, double routeCost) {
        openSession();
        Query orderQuery = session.createQuery("update OrdersEntity set cost = :cost" + " where orderId = :order_id");
        orderQuery.setParameter("cost", routeCost);
        orderQuery.setParameter("order_id", orderId);
        orderQuery.executeUpdate();
        closeSession();
        return "{\"cost\": "+ routeCost +",\n" +
                "\"orderId\": "+ orderId + "}";
    }

    private List<OrdersEntity> getOrdersForDriver(int driverId) {
        Criteria criteria = session.createCriteria(OrdersEntity.class);
        criteria.add(Restrictions.eq("status", Constants.ORDERED));
        return criteria.list();
    }

    private OrdersEntity getOrderByShiftId(int orderId, int shiftId) throws NonExistingOrderException {
        Criteria criteria = session.createCriteria(OrdersEntity.class);
        //TODO: po co tutaj getByShiftID skoro mamy juz orderId?
        //criteria.add(Restrictions.eq("shiftId", shiftId));
        criteria.add(Restrictions.eq("orderId", orderId));
        List<OrdersEntity> result = criteria.list();

        if (result.isEmpty()) {
            throw new NonExistingOrderException(orderId);
        } else {
            return result.get(0);
        }
    }

    private ShiftsEntity getShiftId(int shiftId, int driverId) throws NonExistingShiftException {
        Criteria criteria = session.createCriteria(ShiftsEntity.class);
        criteria.add(Restrictions.eq("driverId", driverId));
        criteria.add(Restrictions.eq("shiftId", shiftId));
        List<ShiftsEntity> result = criteria.list();


        if (result.isEmpty()) {
            throw new NonExistingShiftException(shiftId, driverId);
        } else {
            return result.get(0);
        }
    }

    private OrdersEntity getOrder(int userId, int orderId) throws NonExistingOrderException {
        Criteria criteria = session.createCriteria(OrdersEntity.class);
        criteria.add(Restrictions.eq("clientId", userId));
        criteria.add(Restrictions.eq("orderId", orderId));
        List<OrdersEntity> result = criteria.list();

        if (result.isEmpty()) {
            throw new NonExistingOrderException(userId, orderId);
        } else {
            return result.get(0);
        }
    }


    private OrdersEntity getOrder(int orderId) throws NonExistingOrderException {
//        Criteria criteria = session.createCriteria(OrdersEntity.class);
//        criteria.add(Restrictions.eq("orderId", orderId));
//        List<OrdersEntity> result = criteria.list();

        String hql = "FROM OrdersEntity WHERE orderId =" + orderId;
        Query query = session.createQuery(hql);
        List<OrdersEntity> result = query.list();

        if (result.isEmpty()) {
            throw new NonExistingOrderException(orderId);
        } else {
            return result.get(0);
        }
    }

    private void openSession() {
        if (session == null || !session.isOpen()) {

            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
        }
    }

    private void closeSession() {
        if (session != null && session.isOpen()) {

            session.getTransaction().commit();
            session.close();
        }
    }
}
