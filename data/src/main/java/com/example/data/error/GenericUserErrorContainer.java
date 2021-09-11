package com.example.data.error;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GenericUserErrorContainer implements UserErrorContainer {

    @NonNull
    private UserError.Type errorType;
    @Nullable
    private String message;

    public GenericUserErrorContainer(@NonNull UserError.Type errorType) {
        this.errorType = errorType;
    }

    public GenericUserErrorContainer(@NonNull UserError.Type errorType, @Nullable String message) {
        this.errorType = errorType;
        this.message = message;
    }

    @NonNull
    @Override
    public UserError getUserError() {
        return new UserError(errorType, message);
    }

    @NonNull
    public UserError.Type getErrorType() {
        return errorType;
    }

    @Nullable
    public String getMessage() {
        return message;
    }
}
