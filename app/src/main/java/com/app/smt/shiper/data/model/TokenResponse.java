package com.app.smt.shiper.data.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class TokenResponse implements Parcelable {
    public abstract String token();

    public static TokenResponse create(String token) {
        return new AutoValue_TokenResponse(token);
    }

    public static TypeAdapter<TokenResponse> typeAdapter(Gson gson) {
        return new AutoValue_TokenResponse.GsonTypeAdapter(gson);
    }

}