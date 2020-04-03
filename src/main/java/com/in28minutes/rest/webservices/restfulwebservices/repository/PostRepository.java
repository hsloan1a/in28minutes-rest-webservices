package com.in28minutes.rest.webservices.restfulwebservices.repository;

import com.in28minutes.rest.webservices.restfulwebservices.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository  extends JpaRepository<Post, Integer> {
}
