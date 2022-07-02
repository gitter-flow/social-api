package com.gitter.socialapi.modules.code.application;

import com.gitter.socialapi.modules.code.domain.Code;
import com.gitter.socialapi.modules.code.exposition.payload.response.RetrieveCodeResponse;

public class RetrieveCodeMapper {
    private final String baseURL;

    public RetrieveCodeMapper(String baseURL) {
        this.baseURL = baseURL;
    }

    public RetrieveCodeResponse getResponse(Code code) {
        return new RetrieveCodeResponse(
                code.getPublication().getId(),
                code.getBucketLocation(),
                code.getCodeType().getText(),
                code.getVersions()
        );
    }
}
