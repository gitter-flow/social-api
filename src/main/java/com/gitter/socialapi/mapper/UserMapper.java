package com.gitter.socialapi.mapper;

import com.gitter.socialapi.payload.request.UserCreationRequest;
import com.gitter.socialapi.payload.response.UsersResponse;
import com.gitter.socialapi.model.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public List<UsersResponse> mapUsersToUsersResponse(List<UserEntity> users){
        List<UsersResponse>usersResponse = users.stream()
                .map(user -> modelMapper.map(user, UsersResponse.class))
                .collect(Collectors.toList());
       return usersResponse;
    }
    public UserEntity mapUserDtoToUser(UserCreationRequest user){
        UserEntity userEntity = modelMapper.map(user,UserEntity.class);
        return userEntity;
    }
}
