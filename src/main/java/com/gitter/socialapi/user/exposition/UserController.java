package com.gitter.socialapi.user.exposition;


import com.gitter.socialapi.kernel.exceptions.InvalidParameterException;
import com.gitter.socialapi.user.application.UserService;
import com.gitter.socialapi.user.exposition.payload.request.*;
import com.gitter.socialapi.user.exposition.payload.response.CreateUserResponse;
import com.gitter.socialapi.user.exposition.payload.response.RetrieveUserByIdResponse;
import com.gitter.socialapi.user.exposition.payload.response.RetrieveUserFollowersResponse;
import com.gitter.socialapi.user.exposition.payload.response.RetrieveUserFollowsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( 
        value = "/user",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
public class UserController {

    private UserService userService;
    private 
    
    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest user){
        return ResponseEntity.ok(userService.createUser(user));
    }
    @GetMapping("/{id}")
    public ResponseEntity<RetrieveUserByIdResponse> retrieveUserById(@PathVariable String id) throws InvalidParameterException {
        RetrieveUserByIdResponse userResponse = userService.getById(id);
        return ResponseEntity.ok(userResponse);
    }
    @GetMapping("/followers")
    public ResponseEntity<List<RetrieveUserFollowersResponse>> retrieveUserFollowersByUserID(@RequestBody RetrieveUserFollowersRequest getRequest) throws InvalidParameterException {
        List<RetrieveUserFollowersResponse> followersResponses = userService.retrieveUserFollowers(getRequest);
        return ResponseEntity.ok(followersResponses);
    }
    @GetMapping("/follows")
    public ResponseEntity<List<RetrieveUserFollowsResponse>> retrieveUserFollowersByUserID(@RequestBody RetrieveUserFollowsRequest getRequest) throws InvalidParameterException {
        List<RetrieveUserFollowsResponse> followsResponses = userService.retrieveUserFollows(getRequest);
        return ResponseEntity.ok(followsResponses);
    }
    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserRequest updateUserRequest) throws InvalidParameterException {
        userService.updateUser(updateUserRequest);
        return ResponseEntity.ok(String.format("User %s updated", updateUserRequest.getId()));
    }
    @PutMapping("/follow")
    public ResponseEntity<String> follow(@RequestBody UpdateFollowUserRequest followUserRequest) throws InvalidParameterException {
        userService.follow(followUserRequest);
        return ResponseEntity.ok(String.format(
                "User %s follows user %s",
                followUserRequest.getUserId(),
                followUserRequest.getUserToFollowId()
        ));
    }
    @PutMapping("/unfollow")
    public ResponseEntity<String> unfollow(@RequestBody UpdateUnfollowUserRequest unfollowUserRequest) throws InvalidParameterException {
        userService.unfollow(unfollowUserRequest);
        return ResponseEntity.ok(String.format(
                "User %s unfollows user %s",
                unfollowUserRequest.getUserId(),
                unfollowUserRequest.getUserToUnfollowId()
        ));
    }
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestBody DeleteUserRequest deleteUserRequest) throws InvalidParameterException {
        userService.deleteUser(deleteUserRequest);
        return ResponseEntity.ok(String.format("User %s deleted", deleteUserRequest.getId()));
    }
}
