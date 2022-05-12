package com.gitter.socialapi.controller;

import com.gitter.socialapi.payload.request.UserCreationRequest;
import com.gitter.socialapi.payload.response.UserReponse;
import com.gitter.socialapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users")
    List<UserReponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/add")
    void addUsers(@RequestBody UserCreationRequest userCreationRequest) {
         userService.addUsers(userCreationRequest);
    }
}
