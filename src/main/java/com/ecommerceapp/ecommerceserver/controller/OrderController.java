package com.ecommerceapp.ecommerceserver.controller;

import com.ecommerceapp.ecommerceserver.controller.assembler.OrderModelAssembler;
import com.ecommerceapp.ecommerceserver.controller.service.OrderService;
import com.ecommerceapp.ecommerceserver.model.entity.Order;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class OrderController {
    private OrderService orderService;
    private OrderModelAssembler orderModelAssembler;

    public OrderController(OrderService orderService, OrderModelAssembler orderModelAssembler) {
        this.orderService = orderService;
        this.orderModelAssembler = orderModelAssembler;
    }

    @GetMapping("/orders")
    public CollectionModel<EntityModel<Order>> getAll() {
        List<EntityModel<Order>> orders = orderService.getAll()
                .stream()
                .map(orderModelAssembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(orders, linkTo(methodOn(OrderController.class).getAll()).withSelfRel());
    }

    @GetMapping("/orders/{id}")
    public EntityModel<Order> getOne(@PathVariable Long id) {
        return orderModelAssembler.toModel(orderService.getOne(id));
    }
}
