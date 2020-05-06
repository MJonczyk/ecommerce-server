package com.ecommerceapp.ecommerceserver.controller;

import com.ecommerceapp.ecommerceserver.controller.assembler.UserModelAssembler;
import com.ecommerceapp.ecommerceserver.controller.service.UserService;
import com.ecommerceapp.ecommerceserver.model.entity.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UserController {
    private UserService userService;
    private UserModelAssembler userModelAssembler;

    public UserController(UserService userService, UserModelAssembler userModelAssembler) {
        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
    }

    @GetMapping("/users")
    public CollectionModel<EntityModel<User>> getAll() {
        List<EntityModel<User>> users = userService.getAll()
                .stream()
                .map(userModelAssembler::toModel)
                .collect(Collectors.toList());
        return new CollectionModel<>(users, linkTo(methodOn(UserController.class).getAll()).withSelfRel());
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> getOne(@PathVariable Long id) {
        return userModelAssembler.toModel(userService.getOne(id));
    }

    @PostMapping("/register")
    public ResponseEntity add(@RequestBody User user) {
        EntityModel<User> userEntityModel = userModelAssembler.toModel(userService.save(user));
        return ResponseEntity.created(userEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(userEntityModel);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> edit(@RequestBody User user, @PathVariable Long id) {
        EntityModel<User> userEntityModel = userModelAssembler.toModel(userService.edit(user, id));
        return ResponseEntity.created(userEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(userEntityModel);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
