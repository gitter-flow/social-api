package com.gitter.socialapi.code.payload.request;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreationCodeRequest {
    private String bucket;

    private String publication;

    private String typeCode;

    private String version;
}
