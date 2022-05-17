package com.gitter.socialapi.service;
import com.gitter.socialapi.model.CommentaryEntity;
import com.gitter.socialapi.model.PublicationEntity;
import com.gitter.socialapi.model.UserEntity;
import com.gitter.socialapi.payload.request.EditContentCommentaryRequest;
import com.gitter.socialapi.payload.request.EditLikeCommentaryRequest;
import com.gitter.socialapi.payload.request.EditLikePublicationRequest;
import com.gitter.socialapi.payload.request.GetCommentaryPublicationRequest;
import com.gitter.socialapi.repository.CommentaryRepository;
import com.gitter.socialapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CommentaryService {

    private final CommentaryRepository commentaryRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentaryService(CommentaryRepository commentaryRepository, UserRepository userRepository) {
        this.commentaryRepository = commentaryRepository;
        this.userRepository = userRepository;
    }


    public void addCommentary(CommentaryEntity commentaryEntity){
        commentaryRepository.save(commentaryEntity);
    }

    public List<CommentaryEntity> getCommentarys(){
        return commentaryRepository.findAll();
    }

    public void updateCommentary(CommentaryEntity commentary){
        Optional<CommentaryEntity> commentaryFound = commentaryRepository.findById(commentary.getId());
        if (commentaryFound.isPresent()) {
            commentaryFound.get().setContent(commentary.getContent());
            commentaryFound.get().setPublication(commentary.getPublication());
            commentaryFound.get().setUser(commentary.getUser());
            List<UserEntity> usersLiked = commentaryFound.get().getLikedBy();
            commentary.getLikedBy().forEach(user -> usersLiked.add(user));
            commentaryFound.get().setLikedBy(usersLiked);
        }
        else {
            throw new NullPointerException("Code not found");
        }
    }

    public void deleteCommentary(Long id){
        Optional<CommentaryEntity> commentaryFound = commentaryRepository.findById(id);
        if(commentaryFound.isPresent()){
            commentaryRepository.delete(commentaryFound.get());
        }
        else {
            throw new NullPointerException("Code not found");
        }
    }

    public void likeCommentary(EditLikeCommentaryRequest editLikeCommentaryRequest) {
        Optional<CommentaryEntity> commentaryFound = commentaryRepository.findById(Long.parseLong(editLikeCommentaryRequest.getCommentaryId()));
        if (commentaryFound.isPresent()) {
            List<UserEntity> likedBy = commentaryFound.get().getLikedBy();
            Optional<UserEntity> userFound = userRepository.findById(Long.parseLong(editLikeCommentaryRequest.getUserId()));
            if(userFound.isPresent()){
                likedBy.add(userFound.get());
                commentaryRepository.save(commentaryFound.get());
            }else {
                throw new NullPointerException("User not found");
            }
        }
        else {
            throw new NullPointerException("Publication not found");
        }
    }

    public void unlikeCommentary(EditLikeCommentaryRequest editLikeCommentaryRequest) {
        Optional<CommentaryEntity> commentaryFound = commentaryRepository.findById(Long.parseLong(editLikeCommentaryRequest.getCommentaryId()));
        if (commentaryFound.isPresent()) {
            List<UserEntity> likedBy = commentaryFound.get().getLikedBy();
            Optional<UserEntity> userFound = userRepository.findById(Long.parseLong(editLikeCommentaryRequest.getUserId()));
            if(userFound.isPresent()){
                likedBy.remove(userFound.get());
                commentaryRepository.save(commentaryFound.get());
            }else {
                throw new NullPointerException("User not found");
            }
        }
        else {
            throw new NullPointerException("Publication not found");
        }
    }

    public void updateContentCommentary(EditContentCommentaryRequest contentCommentaryRequest) {
        Optional<CommentaryEntity> commentaryFound = commentaryRepository.findById(Long.parseLong(contentCommentaryRequest.getId()));
        if (commentaryFound.isPresent()) {
            commentaryFound.get().setContent(contentCommentaryRequest.getContent());
            commentaryRepository.save(commentaryFound.get());
        }
        else {
            throw new NullPointerException("Publication not found");
        }
    }


    public  List<CommentaryEntity> getCommentaryPublication(GetCommentaryPublicationRequest getCommentaryPublicationRequest) {
        return commentaryRepository.getCommentaryEntitiesByPublication_Id(Long.valueOf(getCommentaryPublicationRequest.getPublicationId()));
    }
}
