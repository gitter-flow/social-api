package com.gitter.socialapi.user.exposition;


import com.gitter.socialapi.kernel.exceptions.InvalidParameterException;
import com.gitter.socialapi.user.application.UserService;
import com.gitter.socialapi.user.exposition.payload.request.*;
import com.gitter.socialapi.user.exposition.payload.response.CreateUserResponse;
import com.gitter.socialapi.user.exposition.payload.response.RetrieveUserByIdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( value = "/user")
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
    @GetMapping
    public ResponseEntity<RetrieveUserByIdResponse> retrieveUserById(@RequestBody RetrieveUserByIdRequest userRequest){
        RetrieveUserByIdResponse userResponse = userService.getById(userRequest.getId());
        return ResponseEntity.ok(userResponse);
    }
    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody UpdateUserRequest updateUserRequest) throws InvalidParameterException {
        userService.updateUser(updateUserRequest);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/follow")
    public ResponseEntity<Void> follow(@RequestBody UpdateFollowUserRequest updateFollowUserRequest) throws InvalidParameterException {
        userService.follow(updateFollowUserRequest);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/unfollow")
    public ResponseEntity<Void> unfollow(@RequestBody UpdateUnfollowUserRequest editFollowUserRequest) throws InvalidParameterException {
        userService.unfollow(editFollowUserRequest);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestBody DeleteUserRequest deleteUserRequest) throws InvalidParameterException {
        userService.deleteUser(deleteUserRequest);
        return ResponseEntity.ok().build();
    }
}
