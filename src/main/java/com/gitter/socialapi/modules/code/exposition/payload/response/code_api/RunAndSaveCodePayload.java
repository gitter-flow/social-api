package com.gitter.socialapi.modules.code.exposition.payload.response.code_api;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class RunAndSaveCodePayload {
    @SerializedName(value = "etag")
    private String etag;
    @SerializedName(value = "versionId")
    private String versionId;
}
