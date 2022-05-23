package com.gitter.socialapi.code.application;

import com.gitter.socialapi.code.domain.Code;
import com.gitter.socialapi.code.exposition.payload.response.RetrieveCodeResponse;

public class RetrieveCodeMapper {
    private final String baseURL;

    public RetrieveCodeMapper(String baseURL) {
        this.baseURL = baseURL;
    }

    public RetrieveCodeResponse getResponse(Code code) {
        return new RetrieveCodeResponse(
                String.format("%s/publication/%d", baseURL, code.getPublication().getId()),
                code.getBucketLocation(),
                code.getCodeType().getText(),
                code.getVersions()
        );
    }
}
