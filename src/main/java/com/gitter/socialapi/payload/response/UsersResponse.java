package com.gitter.socialapi.payload.response;

import com.gitter.socialapi.model.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class UsersResponse {
    private  Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private List<UserEntity> follower = new ArrayList<>();

    private List<UserEntity> follow = new ArrayList<>();

}
