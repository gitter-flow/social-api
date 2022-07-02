package com.gitter.socialapi.modules.code.exposition.payload.request.code_api;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public final class RunAndSaveCodeRequest {
    @SerializedName("namefile") private final String fileName;
    @SerializedName("data") private final Data data;
    @AllArgsConstructor
    public static class Data {
        @SerializedName("data") public  String data;
        @SerializedName("mod") public String mod;
    }
}
