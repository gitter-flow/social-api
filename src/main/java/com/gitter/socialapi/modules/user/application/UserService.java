package com.gitter.socialapi.modules.user.application;
import com.gitter.socialapi.kernel.exceptions.InvalidParameterException;
import com.gitter.socialapi.kernel.exceptions.NoSuchEntityException;
import com.gitter.socialapi.modules.publication.domain.Publication;
import com.gitter.socialapi.modules.user.exposition.payload.request.*;
import com.gitter.socialapi.modules.user.exposition.payload.response.*;
import com.gitter.socialapi.modules.user.infrastructure.UserRepository;
import com.gitter.socialapi.modules.user.exposition.payload.request.RetrieveUserFollowersRequest;
import com.gitter.socialapi.modules.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public User getUserFromStringId(String id) throws InvalidParameterException {
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

    
    public List<SearchUserResponse> searchUser(String username, int pageNumber, int numberPerPage) {
        PageRequest page = PageRequest.of(pageNumber, numberPerPage);
        List<User> userList = userRepository.selectWhereUsernameLike(username, page);
        return SearchUserMapper.toResponse(userList);
    }
    
    public List<RetrieveUserFollowersResponse> retrieveUserFollowers(RetrieveUserFollowersRequest request) throws InvalidParameterException {
        List<User> followers = new ArrayList<>(getUserFromStringId(request.getUserId()).getFollowedBy());
        Page<User> pageFollowers = new PageImpl<>(followers, PageRequest.of(request.getPageNumber(), request.getNumberPerPage()), followers.size());
        RetrieveUserFollowersMapper mapper = new RetrieveUserFollowersMapper(baseURL);
        return mapper.toResponse(pageFollowers);
    }

    public List<RetrieveUserFollowsResponse> retrieveUserFollows(RetrieveUserFollowsRequest request) throws InvalidParameterException {
        List<User> follows = new ArrayList<>(getUserFromStringId(request.getUserId()).getFollows());
        Page<User> pageFollows = new PageImpl<>(follows, PageRequest.of(request.getPageNumber(), request.getNumberPerPage()), follows.size());
        RetrieveUserFollowsMapper mapper = new RetrieveUserFollowsMapper(baseURL);
        return mapper.toResponse(pageFollows);
    }
    

    public void deleteUser(DeleteUserRequest deleteUserRequest) throws InvalidParameterException {
        User user = getUserFromStringId(deleteUserRequest.getId());
        userRepository.delete(user);
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

        List<User> newFollows = user.getFollows().stream()
                .filter(u -> !Objects.equals(u.getId(), updateUnfollowUserRequest.getUserToUnfollowId()))
                .collect(Collectors.toList());
        user.setFollows(newFollows);
        userRepository.save(user);
    }

    
}
