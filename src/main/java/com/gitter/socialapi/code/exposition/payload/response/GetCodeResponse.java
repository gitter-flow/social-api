package com.gitter.socialapi.code.exposition.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@Getter
@AllArgsConstructor
public class GetCodeResponse {
    private String publicationURI;
    private String bucketLocation;
    private String codeType;
    private List<String> versions;
}
