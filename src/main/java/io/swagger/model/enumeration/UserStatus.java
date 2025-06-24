package io.swagger.model.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum UserStatus {
    PENDING("pending"),
    APPROVED("approved"),
    CLOSED("closed");

    private final String value;

    UserStatus(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return value;
    }

    @JsonCreator
    public static UserStatus fromValue(String text) {
        return Arrays.stream(UserStatus.values())
                .filter(status -> status.value.equalsIgnoreCase(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown user status: " + text));
    }

    /**
     * Determines if the status blocks login access.
     */
    public boolean isBlockedFromLogin() {
        return this == PENDING || this == CLOSED;
    }

    public String getBlockMessage() {
        switch (this) {
            case PENDING:
                return "Your account is pending approval.";
            case CLOSED:
                return "Your account has been rejected.";
            default:
                return "";
        }
    }
}
