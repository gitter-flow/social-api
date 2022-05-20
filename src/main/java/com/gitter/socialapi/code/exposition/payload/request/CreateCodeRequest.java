package com.gitter.socialapi.code.exposition.payload.request;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCodeRequest {
    private String bucket;
    private String publicationId;
    private String codeType;
}
