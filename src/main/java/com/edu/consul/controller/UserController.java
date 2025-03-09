package com.edu.consul.controller;

import com.edu.consul.dto.UserData;
import com.edu.consul.mapper.UserMapper;
import com.edu.consul.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private static final Log LOG = LogFactory.getLog(UserController.class);

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/test")
    ResponseEntity<String> test(@RequestParam("msg") String msg) {
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserData user) {
        try {
            userService.registerUser(userMapper.userObjectToModel(user));
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
