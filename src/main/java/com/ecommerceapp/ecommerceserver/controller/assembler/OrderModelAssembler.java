package com.ecommerceapp.ecommerceserver.controller.assembler;

import com.ecommerceapp.ecommerceserver.controller.OrderController;
import com.ecommerceapp.ecommerceserver.model.entity.Order;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {
    @Override
    public EntityModel<Order> toModel(Order order) {
        return new EntityModel<>(order,
                linkTo(methodOn(OrderController.class).getOne(order.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).getAll()).withRel("orders")
        );
    }
}
