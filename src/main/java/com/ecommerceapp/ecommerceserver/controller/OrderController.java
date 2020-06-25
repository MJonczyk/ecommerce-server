package com.ecommerceapp.ecommerceserver.controller;

import com.ecommerceapp.ecommerceserver.controller.assembler.OrderModelAssembler;
import com.ecommerceapp.ecommerceserver.controller.service.OrderService;
import com.ecommerceapp.ecommerceserver.model.entity.Order;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/orders")
    public ResponseEntity add(@RequestBody Order order) {
        EntityModel<Order> orderEntityModel = orderModelAssembler.toModel(orderService.save(order));

        return ResponseEntity.created(orderEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(orderEntityModel);
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/orders/{id}")
    public ResponseEntity<?> edit(@RequestBody Order order, @PathVariable Long id) {
        EntityModel<Order> orderEntityModel = orderModelAssembler.toModel(orderService.edit(order, id));

        return ResponseEntity.created(orderEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(orderEntityModel);
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        orderService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
