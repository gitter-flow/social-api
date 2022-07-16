package com.gitter.socialapi.modules.user.exposition;


import com.gitter.socialapi.auth.AuthService;
import com.gitter.socialapi.kernel.exceptions.InvalidParameterException;
import com.gitter.socialapi.modules.user.application.UserService;
import com.gitter.socialapi.modules.user.exposition.payload.request.*;
import com.gitter.socialapi.modules.user.exposition.payload.response.*;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( 
        value = "/user",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )

@Slf4j
public class UserController {

    private UserService userService;
    
    private AuthService authService;
    @Autowired
    UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }
    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest user){
        log.info(String.format("Creating user with info : %s", user.toString()));
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
    @GetMapping("/me")
    public ResponseEntity<RetrieveUserByIdResponse> retrieveUserFromToken(KeycloakAuthenticationToken authentication) {
        SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
        AccessToken token = account.getKeycloakSecurityContext().getToken();
        //Username, other way
        System.out.println(authentication.getPrincipal().toString());

        return ResponseEntity.ok(null);
    }
    
    @GetMapping(
            value = "/search/{username}",
            params = {"page", "size"}
    )
    public ResponseEntity<List<SearchUserResponse>> searchUser(
            @PathVariable(value= "username") String username,
            @RequestParam("page") int page,
            @RequestParam("size") int size
          ) {
        return ResponseEntity.ok(userService.searchUser(username, page, size));
    }
    @PutMapping
    @PreAuthorize("@authService.tokenIsValidForUserWithId(#updateUserRequest.id, #authentication)")
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserRequest updateUserRequest, KeycloakAuthenticationToken authentication) throws InvalidParameterException {
        userService.updateUser(updateUserRequest);
        return ResponseEntity.ok(String.format("User %s updated", updateUserRequest.getId()));
    }
    @PutMapping("/follow")
    @PreAuthorize("@authService.tokenIsValidForUserWithId(#followUserRequest.userId, #authentication)")
    public ResponseEntity<String> follow(@RequestBody UpdateFollowUserRequest followUserRequest, KeycloakAuthenticationToken authentication) throws InvalidParameterException {
        userService.follow(followUserRequest);
        return ResponseEntity.ok(String.format(
                "User %s follows user %s",
                followUserRequest.getUserId(),
                followUserRequest.getUserToFollowId()
        ));
    }

    @PutMapping("/unfollow")
    @PreAuthorize("@authService.tokenIsValidForUserWithId(#unfollowUserRequest.userId, #authentication)")
    public ResponseEntity<String> unfollow(@RequestBody UpdateUnfollowUserRequest unfollowUserRequest, KeycloakAuthenticationToken authentication) throws InvalidParameterException {
        userService.unfollow(unfollowUserRequest);
        return ResponseEntity.ok(String.format(
                "User %s unfollows user %s",
                unfollowUserRequest.getUserId(),
                unfollowUserRequest.getUserToUnfollowId()
        ));
    }
    @DeleteMapping
    @PreAuthorize("@authService.tokenIsValidForUserWithId(#deleteUserRequest.id, #authentication)")
    public ResponseEntity<String> deleteUser(@RequestBody DeleteUserRequest deleteUserRequest, KeycloakAuthenticationToken authentication) throws InvalidParameterException {
        userService.deleteUser(deleteUserRequest);
        return ResponseEntity.ok(String.format("User %s deleted", deleteUserRequest.getId()));
    }
}
