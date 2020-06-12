package com.ecommerceapp.ecommerceserver.controller.service;

import com.ecommerceapp.ecommerceserver.model.entity.Order;
import com.ecommerceapp.ecommerceserver.model.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Order getOne(Long id) {
        return orderRepository.getOne(id);
    }

    public Order save(Order newOrder) {
        if (newOrder != null) {
            return orderRepository.save(newOrder);
        }
        return null;
    }

    public Order edit(Order editedOrder, Long id) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setStatus(editedOrder.getStatus());
                    order.setTotalPrice(editedOrder.getTotalPrice());
                    return orderRepository.save(order);
                })
                .orElseGet(() -> {
                    editedOrder.setId(id);
                    return orderRepository.save(editedOrder);
                });
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
