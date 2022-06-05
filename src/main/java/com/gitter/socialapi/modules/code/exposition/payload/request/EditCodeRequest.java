package com.gitter.socialapi.modules.code.exposition.payload.request;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditCodeRequest {
    private String id;
    private String bucket;


    private String publication;

    private String codeType;
}
