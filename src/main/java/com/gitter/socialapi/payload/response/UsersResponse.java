package com.gitter.socialapi.payload.response;

import com.gitter.socialapi.model.UserEntity;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
public class UsersResponse {
    Long id;

     String firstName;

     String lastName;

     String email;

     String password;

     List<UserEntity> follower = new ArrayList<>();

     List<UserEntity> follow = new ArrayList<>();

}
