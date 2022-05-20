package com.gitter.socialapi.publication.exposition.payload.request;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class CreatePublicationRequest {
    @NonNull
    private String userId;
    private String content;
    private String parentPublicationId;
    private String sharedPublicationId;
    private String codeId;
}
