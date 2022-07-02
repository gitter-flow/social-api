package com.gitter.socialapi.modules.code.exposition.payload.response;

import com.gitter.socialapi.modules.code.domain.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class RetrieveCodeVersionsResponse {
    private List<Version> versions;
}
