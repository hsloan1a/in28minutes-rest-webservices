package com.in28minutes.rest.webservices.restfulwebservices.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class UserDaoService {

    private static List<User> users = new ArrayList<>();

    private static Integer userIdIncrementer = 4;

    static {
        users.add(new User(1, "Steve", new Date()));
        users.add(new User(2, "Ted", new Date()));
        users.add(new User(3, "George", new Date()));
    }

    public List<User> getAll() {
        return this.users;
    }

    public User addUser(User user) {
        if (user.getId() == null || user.getId() == 0){
            user.setId(userIdIncrementer++);
        }
        users.add(user);
        return user;
    }

    public User findById(int id) {
        for (User user:users){
            if (user.getId() == id)
                return user;
        }
        return null;
    }

    public User deleteById(int id) {
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId() == id) {
                iterator.remove();
                return user;
            }
        }
        return null;
    }
}
