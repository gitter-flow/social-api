package com.gitter.socialapi.payload.request;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeCreationRequest {
    private String bucket;


    private String publication;

    private String typeCode;
}