package pl.spring.service;

import java.util.List;

import pl.spring.models.Order;

public interface OrderService {
	
    boolean saveOrder(String json);
    List<Order> getOrderByUser(String login);
    Order getOrderById(String orderId);
    boolean payForOrder(String orderId);
}
