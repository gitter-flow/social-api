package com.gitter.socialapi.publication.exposition.payload.request;

import lombok.Getter;

@Getter
public class UpdatePublicationRequest {
    private String id;
    private String content;
    private String codeId;
}