package com.gitter.socialapi.payload.request;
import com.gitter.socialapi.model.UserEntity;
import lombok.Getter;
import org.springframework.lang.Nullable;

import java.util.List;
@Getter
public class CreatePublicationRequest {

    private String userId;

    @Nullable
    private String publicationId;

    private String content;

    private String codeId;

    private List<UserEntity> likedBy;
}
