package com.gitter.socialapi.modules.code.exposition.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class RunCodeRequest {
    private String codeType;
    private String code;
}
