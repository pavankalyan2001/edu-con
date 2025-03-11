package com.edu.consul.controller;

import com.edu.consul.model.User;
import com.edu.consul.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private static final Log LOG = LogFactory.getLog(UserController.class);

    private final UserService userService;

    @GetMapping("/test")
    ResponseEntity<String> test(@RequestParam("msg") String msg) {
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.registerUser(user));
    }

    @GetMapping("/consultants")
    public ResponseEntity<?> getConsultants() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getConsultants());
    }

    @GetMapping("/fetchAll")
    public Page<User> getUsers(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size) {
        return userService.getUsersExceptAdmin(page, size);
    }

    @DeleteMapping
    public void deleteUser(@RequestParam String userId, @RequestParam String role){
        userService.deleteUser(userId,role);
    }
}
