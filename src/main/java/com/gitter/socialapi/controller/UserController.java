package com.gitter.socialapi.controller;

import com.gitter.socialapi.model.PublicationEntity;
import com.gitter.socialapi.model.UserEntity;
import com.gitter.socialapi.payload.request.EditFollowUserRequest;
import com.gitter.socialapi.service.PublicationService;
import com.gitter.socialapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( value = "/user")
public class UserController {

    private UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public void saveUser(@RequestBody UserEntity user){
        userService.addUser(user);
    }

    @GetMapping("/users")
    List<UserEntity> all() {
        return userService.getUsers();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }

    @PostMapping("/follow")
    public void follow(@RequestBody EditFollowUserRequest editFollowUserRequest){
        userService.follow(editFollowUserRequest);
    }

    @PostMapping("/unfollow")
    public void unfollow(@RequestBody EditFollowUserRequest editFollowUserRequest){
        userService.unfollow(editFollowUserRequest);
    }





}
