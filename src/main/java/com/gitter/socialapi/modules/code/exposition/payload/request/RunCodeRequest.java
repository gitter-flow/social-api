package com.gitter.socialapi.modules.code.exposition.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RunCodeRequest {
    private String codeType;
    private String code;
}
