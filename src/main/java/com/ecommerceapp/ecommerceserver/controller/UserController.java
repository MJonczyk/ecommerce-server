package com.ecommerceapp.ecommerceserver.controller;

import com.ecommerceapp.ecommerceserver.controller.assembler.UserModelAssembler;
import com.ecommerceapp.ecommerceserver.controller.service.ConfirmationTokenService;
import com.ecommerceapp.ecommerceserver.controller.service.EmailSenderService;
import com.ecommerceapp.ecommerceserver.controller.service.UserService;
import com.ecommerceapp.ecommerceserver.model.dto.UserDto;
import com.ecommerceapp.ecommerceserver.model.entity.ConfirmationToken;
import com.ecommerceapp.ecommerceserver.model.entity.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UserController {
    private UserService userService;
    private ConfirmationTokenService confirmationTokenService;
    private EmailSenderService emailSenderService;
    private UserModelAssembler userModelAssembler;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserService userService, ConfirmationTokenService confirmationTokenService,
                          EmailSenderService emailSenderService, UserModelAssembler userModelAssembler,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.confirmationTokenService = confirmationTokenService;
        this.emailSenderService = emailSenderService;
        this.userModelAssembler = userModelAssembler;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    public ResponseEntity add(@RequestBody UserDto userDto) {
        User user = new User(userDto.getUsername(), bCryptPasswordEncoder.encode(userDto.getPassword()),
                userDto.getEmail(), userDto.getRole());
        EntityModel<User> userEntityModel = userModelAssembler.toModel(userService.save(user));

        ConfirmationToken token = confirmationTokenService.save(user);
//        emailSenderService.sendConfirmationMail(user.getEmail(), token.getConfirmationToken());

        return ResponseEntity.created(userEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(userEntityModel);
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getByConfirmationToken(token);

        if(confirmationToken != null) {
            User user = confirmationToken.getUser();
            user.setEnabled(true);
            userService.edit(user, user.getId());
            return "Success";
        }
        else {
            return "Failure";
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> edit(@RequestBody User user, @PathVariable Long id) {
        EntityModel<User> userEntityModel = userModelAssembler.toModel(userService.edit(user, id));

        return ResponseEntity.created(userEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(userEntityModel);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
