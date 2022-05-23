package com.gitter.socialapi.comment.application;
import com.gitter.socialapi.comment.domain.Comment;
import com.gitter.socialapi.comment.exposition.payload.request.*;
import com.gitter.socialapi.comment.exposition.payload.response.CreateCommentResponse;
import com.gitter.socialapi.comment.exposition.payload.response.RetrievePublicationCommentsResponse;
import com.gitter.socialapi.comment.infrastructure.CommentRepository;
import com.gitter.socialapi.kernel.exceptions.InvalidParameterException;
import com.gitter.socialapi.kernel.exceptions.NoSuchEntityException;
import com.gitter.socialapi.publication.application.PublicationService;
import com.gitter.socialapi.publication.domain.Publication;
import com.gitter.socialapi.user.application.UserService;
import com.gitter.socialapi.user.domain.User;
import com.gitter.socialapi.user.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PublicationService publicationService;
    private final String baseURL;
    
    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository, UserService userService, PublicationService publicationService, @Value("${application.url}") String baseURL) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.publicationService = publicationService;
        this.baseURL = baseURL;
    }

    private Long getIdToLong(String idStr) throws InvalidParameterException {
        long id;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException nfe) {
            throw InvalidParameterException.forField("id", idStr);
        }
        return id;
    }
    public Comment getCommentFromIdString(String idStr) throws InvalidParameterException {
        Long id = getIdToLong(idStr);
        Optional<Comment> comment = commentRepository.findById(id);
        if(comment.isEmpty()){
            throw NoSuchEntityException.withId(Publication.class.getSimpleName(), id);
        }
        return comment.get();
    }
    
    public CreateCommentResponse createComment(CreateCommentRequest commentRequest) throws InvalidParameterException {
        User user = userService.getUserFromStringId(commentRequest.getUserId());
        Publication publication = publicationService.getPublicationFromIdString(commentRequest.getPublicationId());
        Comment comment = CreateCommentMapper.toComment(commentRequest, user, publication);
        commentRepository.save(comment);
        return CreateCommentMapper.toResponse(comment);
    }
    
    
    public void updateComment(UpdateCommentRequest updateRequest) throws InvalidParameterException {
        Comment comment = getCommentFromIdString(updateRequest.getId());
        comment.setContent(updateRequest.getContent());
        commentRepository.save(comment);
    }

    public void deleteComment(Long id){
        Optional<Comment> commentFound = commentRepository.findById(id);
        if(commentFound.isPresent()){
            commentRepository.delete(commentFound.get());
        }
        else {
            throw new NullPointerException("Code not found");
        }
    }

    public void likeComment(LikeCommentRequest likeRequest) throws InvalidParameterException {
        Comment comment = getCommentFromIdString(likeRequest.getCommentId());
        User user = userService.getUserFromStringId(likeRequest.getUserId());
        comment.getLikedBy().add(user);
        commentRepository.save(comment);
    }

    public void unlikeComment(UnlikeCommentRequest unlikeRequest) throws InvalidParameterException {
        Comment comment = getCommentFromIdString(unlikeRequest.getCommentId());
        User user = userService.getUserFromStringId(unlikeRequest.getUserId());
        comment.getLikedBy().remove(user);
        commentRepository.save(comment);
    }

    public void updateContentComment(UpdateCommentRequest updateCommentRequest) throws InvalidParameterException {
        Comment comment = getCommentFromIdString(updateCommentRequest.getId());
        comment.setContent(updateCommentRequest.getContent());
        commentRepository.save(comment);
    }
    
    public List<RetrievePublicationCommentsResponse> getCommentPublication(RetrievePublicationCommentsRequest getPublicationCommentRequest) {
        Page<Comment> comments = commentRepository.selectWherePublicationIdEquals(
                getPublicationCommentRequest.getId(), 
                PageRequest.of(
                        getPublicationCommentRequest.getPageNumber(), 
                        getPublicationCommentRequest.getNumberPerPages()
                )
        );
        RetrievePublicationCommentsMapper mapper = new RetrievePublicationCommentsMapper(baseURL);
        return mapper.toResponse(comments);
    }
}
