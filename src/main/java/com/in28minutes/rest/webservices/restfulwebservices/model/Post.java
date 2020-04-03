package com.in28minutes.rest.webservices.restfulwebservices.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Post {
    @Id
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @GeneratedValue
    private Integer id;
    private String description;

    @ManyToOne(fetch= FetchType.LAZY)
    @JsonIgnore
    private User user;

    public Post() {
    }

    public Post(Integer id, String description, User user) {
        this.description = description;
        this.id = id;
        this.user = user;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
