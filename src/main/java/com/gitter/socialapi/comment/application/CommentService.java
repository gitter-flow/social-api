package com.gitter.socialapi.comment;
import com.gitter.socialapi.comment.domain.CommentEntity;
import com.gitter.socialapi.comment.infrastructure.CommentRepository;
import com.gitter.socialapi.user.UserEntity;
import com.gitter.socialapi.comment.exposition.payload.request.UpdateCommentRequest;
import com.gitter.socialapi.comment.exposition.payload.request.EditLikeCommentaryRequest;
import com.gitter.socialapi.comment.exposition.payload.request.GetCommentRequest;
import com.gitter.socialapi.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CommentService {

    private final CommentRepository commentaryRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentaryRepository, UserRepository userRepository) {
        this.commentaryRepository = commentaryRepository;
        this.userRepository = userRepository;
    }


    public Long addCommentary(CommentEntity commentaryEntity){
        commentaryRepository.save(commentaryEntity);
        return commentaryEntity.getId();
    }

    public List<CommentEntity> getCommentarys(){
        return commentaryRepository.findAll();
    }

    public void updateCommentary(CommentEntity commentary){
        Optional<CommentEntity> commentaryFound = commentaryRepository.findById(commentary.getId());
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
        Optional<CommentEntity> commentaryFound = commentaryRepository.findById(id);
        if(commentaryFound.isPresent()){
            commentaryRepository.delete(commentaryFound.get());
        }
        else {
            throw new NullPointerException("Code not found");
        }
    }

    public void likeCommentary(EditLikeCommentaryRequest editLikeCommentaryRequest) {
        Optional<CommentEntity> commentaryFound = commentaryRepository.findById(Long.parseLong(editLikeCommentaryRequest.getCommentaryId()));
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
        Optional<CommentEntity> commentaryFound = commentaryRepository.findById(Long.parseLong(editLikeCommentaryRequest.getCommentaryId()));
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

    public void updateContentCommentary(UpdateCommentRequest contentCommentaryRequest) {
        Optional<CommentEntity> commentaryFound = commentaryRepository.findById(Long.parseLong(contentCommentaryRequest.getId()));
        if (commentaryFound.isPresent()) {
            commentaryFound.get().setContent(contentCommentaryRequest.getContent());
            commentaryRepository.save(commentaryFound.get());
        }
        else {
            throw new NullPointerException("Publication not found");
        }
    }


    public  List<CommentEntity> getCommentaryPublication(GetCommentRequest getCommentaryPublicationRequest) {
        return commentaryRepository.getCommentaryEntitiesByPublication_Id(Long.valueOf(getCommentaryPublicationRequest.getPublicationId()));
    }
}
