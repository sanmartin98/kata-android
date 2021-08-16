package com.cornershop.android.kata.cornerbook.data.error;

import com.cornershop.android.kata.cornerbook.data.responses.APIErrorResponse;

import androidx.annotation.NonNull;

import retrofit.RetrofitError;

public class ErrorFactory {

    @NonNull
    public static UserErrorContainer make(RetrofitError retrofitError) {
        try {
            APIErrorResponse body = (APIErrorResponse) retrofitError.getBodyAs(APIErrorResponse.class);
            if (body != null) {
                return new HTTPUserErrorContainer(retrofitError.getResponse().getStatus(), retrofitError.getResponse().getReason(), body.getError(), body);
            } else if (retrofitError.getKind() == RetrofitError.Kind.NETWORK) {
                return new GenericUserErrorContainer(UserError.Type.NETWORK);
            } else {
                return new GenericUserErrorContainer(UserError.Type.EXCEPTION, retrofitError.getMessage());
            }
        } catch (RuntimeException e) {
            return new GenericUserErrorContainer(UserError.Type.UNHANDLED);
        }
    }

    @NonNull
    public UserErrorContainer makeError(RetrofitError retrofitError) {
        try {
            APIErrorResponse body = (APIErrorResponse) retrofitError.getBodyAs(APIErrorResponse.class);
            if (body != null) {
                return new HTTPUserErrorContainer(retrofitError.getResponse().getStatus(), retrofitError.getResponse().getReason(), body.getError(), body);
            } else if (retrofitError.getKind() == RetrofitError.Kind.NETWORK) {
                return new GenericUserErrorContainer(UserError.Type.NETWORK);
            } else {
                return new GenericUserErrorContainer(UserError.Type.EXCEPTION, retrofitError.getMessage());
            }
        } catch (RuntimeException e) {
            return new GenericUserErrorContainer(UserError.Type.UNHANDLED);
        }
    }

}
