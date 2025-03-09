package com.edu.consul.mapper;

import com.edu.consul.dto.UserData;
import com.edu.consul.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User userObjectToModel(UserData userData) {
        if (userData != null) {
            User user = new User();
            user.setName(userData.getName());
            user.setEmail(userData.getEmail());
            user.setRole(userData.getRole());
            user.setPassword(userData.getPassword());
            return user;
        }
        return null;
    }
}
