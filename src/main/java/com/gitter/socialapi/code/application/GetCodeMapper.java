package com.gitter.socialapi.code.application;

import com.gitter.socialapi.code.domain.Code;
import com.gitter.socialapi.code.exposition.payload.response.GetCodeResponse;

public class GetCodeMapper {
    private final String baseURL;

    public GetCodeMapper(String baseURL) {
        this.baseURL = baseURL;
    }

    public GetCodeResponse getResponse(Code code) {
        return new GetCodeResponse(
                String.format("%s/publication/%d", baseURL, code.getPublication().getId()),
                code.getBucketLocation(),
                code.getCodeType().getText(),
                code.getVersions()
        );
    }
}
