package com.gitter.socialapi.service;
import com.gitter.socialapi.model.PublicationEntity;
import com.gitter.socialapi.model.UserEntity;
import com.gitter.socialapi.payload.request.EditFollowUserRequest;
import com.gitter.socialapi.repository.PublicationRepository;
import com.gitter.socialapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void addUser(UserEntity user){
        userRepository.save(user);
    }

    public List<UserEntity> getUsers(){
        return userRepository.findAll();
    }

    public void updateUser(UserEntity user){
        Optional<UserEntity> userFound = userRepository.findById(user.getId());
        if (userFound.isPresent()) {
            userFound.get().setKeycloakId(user.getKeycloakId());
            userFound.get().setUsername(user.getUsername());

            List<UserEntity> usersFollowed = userFound.get().getFollowedBy();
            usersFollowed.forEach(followed ->  usersFollowed.add(followed));
            userFound.get().setFollowedBy(usersFollowed);

            List<UserEntity> usersFollows = userFound.get().getFollows();
            usersFollows.forEach(follows ->  usersFollowed.add(follows));
            userFound.get().setFollows(usersFollows);

            List<PublicationEntity> usersPublications = userFound.get().getPublications();
            usersPublications.forEach(publication ->  usersPublications.add(publication));
            userFound.get().setPublications(usersPublications);
        }
        else {
            throw new NullPointerException("User not found");
        }
    }

    public void deleteUser(Long id){
        Optional<UserEntity> publicationFound = userRepository.findById(id);
        if(publicationFound.isPresent()){
            userRepository.delete(publicationFound.get());
        }
        else {
            throw new NullPointerException("User not found");
        }
    }

    public void follow(EditFollowUserRequest editFollowUserRequest){
        Optional<UserEntity> userFound = userRepository.findById(Long.valueOf(editFollowUserRequest.getUserToFollowId()));
        if (userFound.isEmpty()){
            throw new NullPointerException("user not found");
        }
        Optional<UserEntity> followingUser = userRepository.findById(Long.valueOf(editFollowUserRequest.getUserId()));
        if (followingUser.isEmpty()){
            throw new NullPointerException("user not found");
        }

        List<UserEntity> follow = userFound.get().getFollowedBy();
        if(follow.contains(followingUser.get())){
            throw new NullPointerException("User followed already");
        }
        follow.add(followingUser.get());
        userFound.get().setFollowedBy(follow);
        userRepository.save(userFound.get());
    }
    public void unfollow(EditFollowUserRequest editFollowUserRequest){
        Optional<UserEntity> userFound = userRepository.findById(Long.valueOf(editFollowUserRequest.getUserToFollowId()));
        if (userFound.isEmpty()){
            throw new NullPointerException("user not found");
        }
        Optional<UserEntity> followingUser = userRepository.findById(Long.valueOf(editFollowUserRequest.getUserId()));
        if (followingUser.isEmpty()){
            throw new NullPointerException("user not found");
        }

        List<UserEntity> follow = userFound.get().getFollowedBy();
        if(!follow.contains(followingUser.get())){
            throw new NullPointerException("User have been already unfollow");
        }
        follow.remove(followingUser.get());
        userFound.get().setFollowedBy(follow);
        userRepository.save(userFound.get());
    }
}
