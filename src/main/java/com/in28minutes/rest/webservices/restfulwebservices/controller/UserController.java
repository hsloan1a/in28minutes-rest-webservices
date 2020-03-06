package com.in28minutes.rest.webservices.restfulwebservices.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


import com.in28minutes.rest.webservices.restfulwebservices.exception.UserNotFoundException;
import com.in28minutes.rest.webservices.restfulwebservices.model.User;
import com.in28minutes.rest.webservices.restfulwebservices.model.UserDaoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Api(tags = { "users" })
@RestController
public class UserController {

    private final UserDaoService userDaoService;

    @Autowired
    public UserController(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    @GetMapping("/users")

     List<User> getAllUsers() {
        return userDaoService.getAll();
    }

    @GetMapping("/users/{id}")
    public Resource<User> getUser(@PathVariable int id) {
        User foundUser = userDaoService.findById(id);
        if (foundUser == null)
            throw new UserNotFoundException("id=" + id);
        Resource<User> resource = new Resource<User>(foundUser);

        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllUsers());

        resource.add(linkTo.withRel("all-users"));

        return resource;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User foundUser = userDaoService.deleteById(id);
        if (foundUser == null)
            throw new UserNotFoundException("id=" + id);
    }

    @PostMapping("/users")
    public ResponseEntity<Object> addUser(@Valid @RequestBody User user) {
        User savedUser = userDaoService.addUser(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

}
