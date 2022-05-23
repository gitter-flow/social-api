package com.gitter.socialapi.user.exposition;


import com.gitter.socialapi.kernel.exceptions.InvalidParameterException;
import com.gitter.socialapi.user.application.UserService;
import com.gitter.socialapi.user.exposition.payload.request.*;
import com.gitter.socialapi.user.exposition.payload.response.CreateUserResponse;
import com.gitter.socialapi.user.exposition.payload.response.RetrieveUserByIdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserRequest updateUserRequest) throws InvalidParameterException {
        userService.updateUser(updateUserRequest);
        return ResponseEntity.ok(String.format("User %s updated", updateUserRequest.getId()));
    }
    @PutMapping("/follow")
    public ResponseEntity<String> follow(@RequestBody UpdateFollowUserRequest followUserRequest) throws InvalidParameterException {
        userService.follow(followUserRequest);
        return ResponseEntity.ok(String.format(
                "User %s followed by user %s", 
                followUserRequest.getUserToFollowId(), 
                followUserRequest.getUserId()));
    }
    @PutMapping("/unfollow")
    public ResponseEntity<String> unfollow(@RequestBody UpdateUnfollowUserRequest unfollowUserRequest) throws InvalidParameterException {
        userService.unfollow(unfollowUserRequest);
        return ResponseEntity.ok(String.format(
                "User %s followed by user %s",
                unfollowUserRequest.getUserToUnfollowId(),
                unfollowUserRequest.getUserId()));
    }
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestBody DeleteUserRequest deleteUserRequest) throws InvalidParameterException {
        userService.deleteUser(deleteUserRequest);
        return ResponseEntity.ok(String.format("User %s deleted", deleteUserRequest.getId()));
    }
}
