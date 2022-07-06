package com.gitter.socialapi.modules.code.exposition.payload.response.code_api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.Payload;

@AllArgsConstructor
@Getter
public final class RunAndSaveCodeResponse {
    @SerializedName(value = "minio_code")
    private final RunAndSaveCodePayload minioCode;
    @SerializedName(value = "minio_result_code")
    private final RunAndSaveCodePayload minioResultCode;
    @SerializedName(value = "result_of_exec")
    private final String resultOfExec;
}
