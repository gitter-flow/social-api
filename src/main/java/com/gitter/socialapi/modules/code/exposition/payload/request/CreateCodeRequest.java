package com.gitter.socialapi.modules.code.exposition.payload.request;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCodeRequest {
    private String bucket;
    private String publicationId;
    private String codeType;
}
