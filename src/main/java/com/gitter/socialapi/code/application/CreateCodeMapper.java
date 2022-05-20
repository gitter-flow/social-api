package com.gitter.socialapi.code.application;

import com.gitter.socialapi.code.domain.Code;
import com.gitter.socialapi.code.exposition.payload.request.CreateCodeRequest;
import com.gitter.socialapi.code.exposition.payload.response.CreateCodeResponse;
import com.gitter.socialapi.kernel.exceptions.InvalidParameterException;
import com.gitter.socialapi.kernel.exceptions.InvalidTypeCodeException;
import com.gitter.socialapi.kernel.utils.DateGenerator;
import com.gitter.socialapi.publication.domain.Publication;
import com.gitter.socialapi.publication.domain.CodeType;

import java.util.List;

public class CreateCodeMapper {

    public static Code toCode(CreateCodeRequest codeRequest, Publication publication) throws InvalidParameterException, InvalidTypeCodeException {
        DateGenerator date = new DateGenerator();
        CodeType codeType = CodeType.fromString(codeRequest.getCodeType());
        return new Code(
                publication,
                String.format("user-%s/code-%s", publication.getUser().getId(), publication.getId()),
                codeType,
                List.of(date.now())
        );
    }
    public static CreateCodeResponse getResponse(Code code) {
        return new CreateCodeResponse(code.getId());
    }
}
