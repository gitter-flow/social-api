package com.gitter.socialapi.service;

import com.gitter.socialapi.payload.request.UserCreationRequest;
import com.gitter.socialapi.payload.response.UserReponse;
import com.gitter.socialapi.mapper.UserMapper;
import com.gitter.socialapi.model.UserEntity;
import com.gitter.socialapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserReponse> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return userMapper.mapUsersToUsersResponse(users);
    }
    public void addUsers(UserCreationRequest userCreationRequest){
        UserEntity userEntity = userMapper.mapUserDtoToUser(userCreationRequest);
        userRepository.save(userEntity);
    }

    UserEntity findUser(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent()){
            return user.get();
        }
        else {
            throw new NullPointerException("User not found");
        }
    }


}
