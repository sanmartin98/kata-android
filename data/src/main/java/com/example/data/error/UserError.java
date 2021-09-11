package com.example.data.error;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class UserError {

    public enum Type {
        EXCEPTION,
        HTTP,
        NETWORK,
        UNHANDLED
    }

    @NonNull
    private Type type;

    @Nullable
    private String message;

    @Nullable
    private Integer resourceId;

    public UserError(@NonNull Type type) {
        this.type = type;
        this.message = null;
    }

    public UserError(@NonNull Type type, @Nullable String message) {
        this.type = type;
        this.message = message;
    }

    public UserError(@NonNull Type type, @Nullable Integer resourceId) {
        this.type = type;
        this.resourceId = resourceId;
    }

    /**
     * This method returns a message ready for being presented to the user.
     *
     * @param messageParser contains the implementation to get messages from context
     * @return A message for presenting to the user. {@code null} if message doesn't exists.
     */
    @Nullable
    public String getMessage(MessageParser messageParser) {
        if (message != null) {
            return message;
        }
        if (resourceId != null) {
            return messageParser.getString(resourceId);
        }
        switch (type) {
            case NETWORK:
                return messageParser.getNetworkErrorMessage();
            case UNHANDLED:
                return messageParser.getUnhandledErrorMessage();
        }
        return null;
    }

    @NonNull
    public Type getType() {
        return type;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    public interface MessageParser {
        String getString(int resourceId);

        String getNetworkErrorMessage();

        String getUnhandledErrorMessage();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserError userError = (UserError) o;
        return type == userError.type &&
                Objects.equals(message, userError.message) &&
                Objects.equals(resourceId, userError.resourceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, message, resourceId);
    }
}
