package com.cornershop.android.kata.cornerbook.data.responses;

import static com.example.data.commons.Utils.checkStringNotNullOrEmpty;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class APIErrorResponse {

    @SerializedName("error")
    private String error;

    @SerializedName("user_error_msg")
    private String userMessage;

    @Nullable
    public String getHumanMessageError() {
        if (checkStringNotNullOrEmpty(userMessage)) {
            return userMessage;
        } else if (checkStringNotNullOrEmpty(error)) {
            return error;
        }
        return null;
    }

    public String getError() {
        return error;
    }

    public String getUserMessage() {
        return userMessage;
    }

}