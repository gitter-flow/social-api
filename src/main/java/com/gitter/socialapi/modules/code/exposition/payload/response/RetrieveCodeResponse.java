package com.gitter.socialapi.modules.code.exposition.payload.response;

import com.gitter.socialapi.modules.code.domain.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@Getter
@AllArgsConstructor
public class RetrieveCodeResponse {
    private String publicationId;
    private String codeType;
    private String code;
    private List<Version> versions;
}
