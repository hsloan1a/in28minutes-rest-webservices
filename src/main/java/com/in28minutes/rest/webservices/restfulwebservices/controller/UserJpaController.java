package com.in28minutes.rest.webservices.restfulwebservices.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.in28minutes.rest.webservices.restfulwebservices.exception.UserNotFoundException;
import com.in28minutes.rest.webservices.restfulwebservices.model.Post;
import com.in28minutes.rest.webservices.restfulwebservices.model.User;
import com.in28minutes.rest.webservices.restfulwebservices.repository.PostRepository;
import com.in28minutes.rest.webservices.restfulwebservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/JPA/")
public class UserJpaController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public UserJpaController(PostRepository postRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/users")
    MappingJacksonValue getAllUsers() {
        List<User> users = userRepository.findAll();
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name");
        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter("AllUserFilter", simpleBeanPropertyFilter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(users);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    @GetMapping("/users/{id}")
    public Resource<User> getUser(@PathVariable int id) {
        Optional<User> foundUser = userRepository.findById(id);
        if (!foundUser.isPresent())
            throw new UserNotFoundException("id=" + id);
        Resource<User> resource = new Resource<User>(foundUser.get());

        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllUsers());

        resource.add(linkTo.withRel("all-users"));

        return resource;
    }

    @GetMapping("/users/{id}/posts")
    public Resources<List<Post>> getAllUserPosts(@PathVariable int id) {
        Optional<User> foundUser = userRepository.findById(id);
        if (!foundUser.isPresent())
            throw new UserNotFoundException("id=" + id);
        Resources<List<Post>> resource = new Resources<List<Post>>(Collections.singleton(foundUser.get().getPosts()));

        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllUserPosts(id));

        resource.add(linkTo.withRel("all-users-posts"));

        return resource;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);

    }

    @PostMapping("/users")
    public ResponseEntity<Object> addUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Object> addUserPost(@PathVariable int id, @RequestBody Post post) {
        Optional<User> foundUser = userRepository.findById(id);
        if (!foundUser.isPresent())
            throw new UserNotFoundException("id=" + id);

        User savedUser = foundUser.get();
        post.setUser(savedUser);
        postRepository.save(post);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

}
