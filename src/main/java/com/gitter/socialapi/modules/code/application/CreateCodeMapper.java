package com.gitter.socialapi.modules.code.application;

import com.gitter.socialapi.modules.code.domain.Code;
import com.gitter.socialapi.modules.code.exposition.payload.request.CreateCodeRequest;
import com.gitter.socialapi.modules.code.exposition.payload.response.CreateCodeResponse;
import com.gitter.socialapi.kernel.exceptions.InvalidParameterException;
import com.gitter.socialapi.kernel.exceptions.InvalidCodeTypeException;
import com.gitter.socialapi.modules.code.domain.CodeType;
import com.gitter.socialapi.modules.publication.domain.Publication;

import java.util.List;
import java.util.UUID;

public class CreateCodeMapper {
    public static Code toCode(CreateCodeRequest codeRequest, Publication publication) throws InvalidParameterException, InvalidCodeTypeException {
        CodeType codeType = CodeType.fromString(codeRequest.getCodeType());
        return new Code(
                publication,
                String.format("user-%s/code-%s", publication.getUser().getId(), publication.getId()),
                codeType,
                List.of(UUID.randomUUID().toString())
        );
    }
    public static CreateCodeResponse getResponse(Code code) {
        return new CreateCodeResponse(code.getId());
    }
}
