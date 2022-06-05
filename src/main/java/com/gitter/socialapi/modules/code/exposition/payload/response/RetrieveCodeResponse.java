package com.gitter.socialapi.modules.code.exposition.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@Getter
@AllArgsConstructor
public class RetrieveCodeResponse {
    private String publicationURI;
    private String bucketLocation;
    private String codeType;
    private List<String> versions;
}
