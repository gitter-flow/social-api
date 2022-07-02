package com.gitter.socialapi.modules.code.exposition.payload.response.code_api;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public final class RunAndSaveCodeResponse {
    @SerializedName("minio_code") private final Payload minioCode;
    @SerializedName("minio_result_code") private final Payload minioResultCode;
    @SerializedName("result_of_exec") private final String resultOfExec;
    
    public class Payload {
        @SerializedName("etag") public String etag;
        @SerializedName("vesionId") public String versionId;
    }
}
