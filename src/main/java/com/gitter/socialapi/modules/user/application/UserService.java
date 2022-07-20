package com.gitter.socialapi.modules.user.application;
import com.gitter.socialapi.kernel.exceptions.InvalidParameterException;
import com.gitter.socialapi.kernel.exceptions.NoProfilePictureException;
import com.gitter.socialapi.kernel.exceptions.NoSuchEntityException;
import com.gitter.socialapi.modules.user.exposition.payload.request.*;
import com.gitter.socialapi.modules.user.exposition.payload.response.*;
import com.gitter.socialapi.modules.user.infrastructure.UserPictureRepository;
import com.gitter.socialapi.modules.user.infrastructure.UserRepository;
import com.gitter.socialapi.modules.user.exposition.payload.request.RetrieveUserFollowersRequest;
import com.gitter.socialapi.modules.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserService {

    private final UserRepository userRepository;
    
    private final UserPictureRepository pictureRepository;
    private final String baseURL;
    
    @Autowired
    public UserService(UserRepository userRepository, UserPictureRepository pictureRepository, @Value("${application.url}") String baseURL) {
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
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
    
    public void uploadUserPicture(byte[] image, String userId, String format) throws IOException, InvalidParameterException {
        User user = getUserFromStringId(userId);
        String filename = String.format("user-pic-%s.%s", userId, format);
        pictureRepository.uploadPicture(image, filename, format);
        user.setPictureFileName(filename);
        userRepository.save(user);
    }
    
    public byte[] getUserPicture(String userId, HttpHeaders headers) throws IOException, InvalidParameterException, NoProfilePictureException {
        User user = getUserFromStringId(userId);
        if(user.getPictureFileName() == null) throw NoProfilePictureException.forUser(userId);
        InputStream stream = pictureRepository.getPicture(String.format(user.getPictureFileName(), userId));
        String[] splitFilename =user.getPictureFileName().split("\\.");
        headers.set("File-Extension", splitFilename[splitFilename.length - 1]);
        return Base64.getEncoder().encode(stream.readAllBytes());
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
