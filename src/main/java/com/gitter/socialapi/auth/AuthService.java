package com.gitter.socialapi.auth;

import com.gitter.socialapi.kernel.exceptions.InvalidParameterException;
import com.gitter.socialapi.modules.code.application.CodeService;
import com.gitter.socialapi.modules.comment.application.CommentService;
import com.gitter.socialapi.modules.publication.application.PublicationService;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthService {
    private final PublicationService publicationService;
    private final CommentService commentService;
    private final CodeService codeService;

    @Autowired
    public AuthService(PublicationService publicationService, CommentService commentService, CodeService codeService) {
        this.publicationService = publicationService;
        this.commentService = commentService;
        this.codeService = codeService;
    }
    public static boolean tokenIsValidForUserWithId(String userId, KeycloakAuthenticationToken authentication) {
        return Objects.equals(userId, authentication.getPrincipal().toString());
    }
    public boolean tokenIsValidForPublicationWithId(String publicationId, KeycloakAuthenticationToken authentication) throws InvalidParameterException {
        String userId = publicationService.getPublicationByID(publicationId).getUserId();
        return Objects.equals(userId, authentication.getPrincipal().toString());
    }
    public boolean tokenIsValidForCommentWithId(String commentId, KeycloakAuthenticationToken authentication) throws InvalidParameterException {
        String userId = commentService.getCommentFromIdString(commentId).getUser().getId();
        return Objects.equals(userId, authentication.getPrincipal().toString());
    }
    
    public boolean tokenIsValidForCodeWithId(String codeId, KeycloakAuthenticationToken authentication) throws InvalidParameterException {
        String userId = codeService.getCodeFromIdString(codeId).getPublication().getUser().getId();
        return Objects.equals(userId, authentication.getPrincipal().toString());
    }
}
