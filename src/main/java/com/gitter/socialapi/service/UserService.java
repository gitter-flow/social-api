package com.gitter.socialapi.service;

import com.gitter.socialapi.payload.request.UserRequest;
import com.gitter.socialapi.payload.response.UsersResponse;
import com.gitter.socialapi.mapper.UserMapper;
import com.gitter.socialapi.model.UserEntity;
import com.gitter.socialapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@Component
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UsersResponse> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        List<UsersResponse> usersResponse = userMapper.mapUsersToUsersResponse(users);
        return usersResponse;
    }
    public void addUsers(UserRequest userDto){
        UserEntity userEntity = userMapper.mapUserDtoToUser(userDto);
        userRepository.save(userEntity);
    }




}
