package com.gitter.socialapi.payload.request;
import com.gitter.socialapi.model.UserEntity;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;
@Getter
public class CreatePublicationRequest {
    @NonNull
    private String userId;
    
    private String publicationId;
    
    @NonNull
    private String content;
    
    private String codeId;
}
