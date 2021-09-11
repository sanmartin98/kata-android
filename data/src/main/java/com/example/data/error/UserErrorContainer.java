package com.example.data.error;

import androidx.annotation.NonNull;

import com.example.data.error.UserError;

public interface UserErrorContainer {

    @NonNull
    UserError getUserError();

}
