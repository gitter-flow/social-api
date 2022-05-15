package com.gitter.socialapi.service;
import com.gitter.socialapi.model.CommentaryEntity;
import com.gitter.socialapi.model.UserEntity;
import com.gitter.socialapi.repository.CommentaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CommentaryService {

    private final CommentaryRepository commentaryRepository;

    @Autowired
    public CommentaryService(CommentaryRepository commentaryRepository) {
        this.commentaryRepository = commentaryRepository;
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
}
