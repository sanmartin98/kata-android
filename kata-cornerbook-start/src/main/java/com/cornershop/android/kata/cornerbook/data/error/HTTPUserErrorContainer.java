package com.cornershop.android.kata.cornerbook.data.error;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cornershop.android.kata.cornerbook.data.responses.APIErrorResponse;


public class HTTPUserErrorContainer implements UserErrorContainer {

    private final int status;
    private final String reason;
    @Nullable
    private final String code;
    @NonNull
    private final APIErrorResponse body;

    public HTTPUserErrorContainer(int status, String reason, @Nullable String code, @NonNull APIErrorResponse body) {
        this.status = status;
        this.reason = reason;
        this.code = code;
        this.body = body;
    }

    @Nullable
    public String getCode() {
        return code;
    }

    @NonNull
    @Override
    public UserError getUserError() {
        String errorMessage;
        if (body.getHumanMessageError() != null) {
            errorMessage = body.getHumanMessageError();
        } else {
            errorMessage = status + " " + reason + " " + body.getError();
        }
        return new UserError(UserError.Type.HTTP, errorMessage);
    }

    @NonNull
    public APIErrorResponse getBody() {
        return body;
    }

    public int getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }
}
