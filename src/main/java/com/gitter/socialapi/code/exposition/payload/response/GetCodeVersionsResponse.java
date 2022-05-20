package com.gitter.socialapi.code.exposition.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class GetCodeVersionsResponse {
    private List<String> versions;
}
