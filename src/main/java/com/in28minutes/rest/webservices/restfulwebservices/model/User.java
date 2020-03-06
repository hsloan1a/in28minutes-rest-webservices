package com.in28minutes.rest.webservices.restfulwebservices.model;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

public class User {

    /*********getters and setters************/
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    /*********************/
    private Integer id;

    @Size(min=2, message = "Name should have at least 2 characters")
    private String name;

    @Past
    private Date birthDate;

    private List<Post> posts;

    public User(int id, String name, Date birthDate) {
        super();
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }
}
