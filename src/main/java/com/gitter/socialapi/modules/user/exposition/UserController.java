package com.gitter.socialapi.modules.user.exposition;


import com.gitter.socialapi.kernel.exceptions.InvalidParameterException;
import com.gitter.socialapi.modules.user.application.UserService;
import com.gitter.socialapi.modules.user.exposition.payload.request.*;
import com.gitter.socialapi.modules.user.exposition.payload.response.CreateUserResponse;
import com.gitter.socialapi.modules.user.exposition.payload.response.RetrieveUserByIdResponse;
import com.gitter.socialapi.modules.user.exposition.payload.response.RetrieveUserFollowersResponse;
import com.gitter.socialapi.modules.user.exposition.payload.response.RetrieveUserFollowsResponse;
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
    @GetMapping(
            value = "/followers/{userId}",
            params = { "page", "size"}
    )
    public ResponseEntity<List<RetrieveUserFollowersResponse>> retrieveUserFollowersByUserID(
            @PathVariable(value= "userId") String userId,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size
    ) throws InvalidParameterException {
        RetrieveUserFollowersRequest getRequest = new RetrieveUserFollowersRequest(userId, page, size);
        List<RetrieveUserFollowersResponse> followersResponses = userService.retrieveUserFollowers(getRequest);
        return ResponseEntity.ok(followersResponses);
    }
    @GetMapping(
            value = "/follows/{userId}",
            params = {"page", "size"}
    )
    public ResponseEntity<List<RetrieveUserFollowsResponse>> retrieveUserFollowsByUserID(
            @PathVariable("userId") String userId,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) throws InvalidParameterException {
        RetrieveUserFollowsRequest getRequest = new RetrieveUserFollowsRequest(userId, page, size);
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
