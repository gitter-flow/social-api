package com.gitter.socialapi.modules.code.exposition.payload.request.code_api;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class RunCodeAPIRequest {
    @SerializedName("mod") private final String mod;
    @SerializedName("data") private final String data;
}
