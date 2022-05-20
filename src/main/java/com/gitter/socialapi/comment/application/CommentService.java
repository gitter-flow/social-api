package com.gitter.socialapi.comment.application;
import com.gitter.socialapi.comment.domain.CommentEntity;
import com.gitter.socialapi.comment.infrastructure.CommentRepository;
import com.gitter.socialapi.user.domain.User;
import com.gitter.socialapi.comment.exposition.payload.request.UpdateCommentRequest;
import com.gitter.socialapi.comment.exposition.payload.request.LikeCommentRequest;
import com.gitter.socialapi.comment.exposition.payload.request.GetCommentRequest;
import com.gitter.socialapi.user.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public Long addComment(CommentEntity commentEntity){
        commentRepository.save(commentEntity);
        return commentEntity.getId();
    }

    public List<CommentEntity> getComments(){
        return commentRepository.findAll();
    }

    public void updateComment(CommentEntity comment){
        Optional<CommentEntity> commentFound = commentRepository.findById(comment.getId());
        if (commentFound.isPresent()) {
            commentFound.get().setContent(comment.getContent());
            commentFound.get().setPublication(comment.getPublication());
            commentFound.get().setUser(comment.getUser());
            List<User> usersLiked = commentFound.get().getLikedBy();
            comment.getLikedBy().forEach(user -> usersLiked.add(user));
            commentFound.get().setLikedBy(usersLiked);
        }
        else {
            throw new NullPointerException("Code not found");
        }
    }

    public void deleteComment(Long id){
        Optional<CommentEntity> commentFound = commentRepository.findById(id);
        if(commentFound.isPresent()){
            commentRepository.delete(commentFound.get());
        }
        else {
            throw new NullPointerException("Code not found");
        }
    }

    public void likeComment(LikeCommentRequest editLikeCommentRequest) {
        Optional<CommentEntity> commentFound = commentRepository.findById(Long.parseLong(editLikeCommentRequest.getCommentId()));
        if (commentFound.isPresent()) {
            List<User> likedBy = commentFound.get().getLikedBy();
            Optional<User> userFound = userRepository.findById(Long.parseLong(editLikeCommentRequest.getUserId()));
            if(userFound.isPresent()){
                likedBy.add(userFound.get());
                commentRepository.save(commentFound.get());
            }else {
                throw new NullPointerException("User not found");
            }
        }
        else {
            throw new NullPointerException("Publication not found");
        }
    }

    public void unlikeComment(LikeCommentRequest editLikeCommentRequest) {
        Optional<CommentEntity> commentFound = commentRepository.findById(Long.parseLong(editLikeCommentRequest.getCommentId()));
        if (commentFound.isPresent()) {
            List<User> likedBy = commentFound.get().getLikedBy();
            Optional<User> userFound = userRepository.findById(Long.parseLong(editLikeCommentRequest.getUserId()));
            if(userFound.isPresent()){
                likedBy.remove(userFound.get());
                commentRepository.save(commentFound.get());
            }else {
                throw new NullPointerException("User not found");
            }
        }
        else {
            throw new NullPointerException("Publication not found");
        }
    }

    public void updateContentComment(UpdateCommentRequest contentCommentRequest) {
        Optional<CommentEntity> commentFound = commentRepository.findById(Long.parseLong(contentCommentRequest.getId()));
        if (commentFound.isPresent()) {
            commentFound.get().setContent(contentCommentRequest.getContent());
            commentRepository.save(commentFound.get());
        }
        else {
            throw new NullPointerException("Publication not found");
        }
    }


    public  List<CommentEntity> getCommentPublication(GetCommentRequest getCommentPublicationRequest) {
        return commentRepository.getCommentEntitiesByPublication_Id(Long.valueOf(getCommentPublicationRequest.getPublicationId()));
    }
}
