package com.gitter.socialapi.user.application;
import com.gitter.socialapi.kernel.exceptions.InvalidParameterException;
import com.gitter.socialapi.kernel.exceptions.NoSuchEntityException;
import com.gitter.socialapi.user.exposition.payload.response.GetUserPublicationsResponse;
import com.gitter.socialapi.user.exposition.payload.request.*;
import com.gitter.socialapi.user.exposition.payload.response.CreateUserResponse;
import com.gitter.socialapi.user.infrastructure.UserRepository;
import com.gitter.socialapi.user.domain.User;
import com.gitter.socialapi.user.exposition.payload.response.RetrieveUserByIdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService {

    private final UserRepository userRepository;
    
    private final String baseURL;
    
    @Autowired
    public UserService(UserRepository userRepository, @Value("${application.url}") String baseURL) {
        this.userRepository = userRepository;
        this.baseURL = baseURL;
    }

    public User getUserFromStringId(String idStr) throws InvalidParameterException {
        long id;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException nfe) {
            throw InvalidParameterException.forField("id", idStr);
        }
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()){
            throw NoSuchEntityException.withId(User.class.getSimpleName(), id);
        }
        return user.get();
    }
    
    public CreateUserResponse createUser(CreateUserRequest userRequest){
        User user = CreateUserMapper.getUser(userRequest);
        userRepository.save(user);
        return CreateUserResponse.of(user);
    }

    public RetrieveUserByIdResponse getById(String id) throws InvalidParameterException {
        User user = getUserFromStringId(id);
        return RetrieveUserMapper.toGetUserByIdResponse(user);
    }
    
    public GetUserPublicationsResponse getUserPublications(String userId) throws InvalidParameterException {
        User user = getUserFromStringId(userId);
        GetUserPublicationMapper mapper = new GetUserPublicationMapper(baseURL);
        return mapper.getResponse(user);
    }

    public void deleteUser(DeleteUserRequest deleteUserRequest) throws InvalidParameterException {
        long id;
        try {
            id = Long.parseLong(deleteUserRequest.getId());
        } catch (NumberFormatException nfe) {
            throw InvalidParameterException.forField("id", deleteUserRequest.getId());
        }
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw NoSuchEntityException.withId(User.class.getSimpleName(), id);
        }
        userRepository.delete(user.get());
    }
    
    public void updateUser(UpdateUserRequest userRequest) throws InvalidParameterException {
        User user = UpdateUserMapper.getUser(userRequest, getUserFromStringId(userRequest.getId()));
        userRepository.save(user);
    }
    
    public void follow(UpdateFollowUserRequest updateFollowUserRequest) throws InvalidParameterException {
        User user = getUserFromStringId(updateFollowUserRequest.getUserId());
        User userToFollow = getUserFromStringId(updateFollowUserRequest.getUserToFollowId());
        user.getFollows().add(0, userToFollow);
        userRepository.save(user);
    }
    public void unfollow(UpdateUnfollowUserRequest updateUnfollowUserRequest) throws InvalidParameterException {
        User user = getUserFromStringId(updateUnfollowUserRequest.getUserId());
        long id;
        try {
            id = Long.parseLong(updateUnfollowUserRequest.getUserToUnfollowId());
        } catch (NumberFormatException nfe) {
            throw InvalidParameterException.forField("id", updateUnfollowUserRequest.getUserToUnfollowId());
        }
        List<User> newFollows = user.getFollows().stream()
                .filter(u -> u.getId() != id)
                .collect(Collectors.toList());
        user.setFollows(newFollows);
        userRepository.save(user);
    }
}
