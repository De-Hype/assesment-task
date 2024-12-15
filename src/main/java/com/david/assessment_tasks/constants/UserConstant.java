package com.david.assessment_tasks.constants;

import lombok.Getter;

public class UserConstant {

    private UserConstant() {

    }
    public enum StatusCode {
        CREATED("201"),
        OK("200"),
        NO_CONTENT("204"),
        INTERNAL_SERVER_ERROR("500");


        private final String code;

        StatusCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    public static final String USER_CREATED = "User successfully created.";
    public static final String USER_LOGGED_IN = "Login successful. Welcome!";
    public static final String USER_DELETED = "User has been successfully deleted.";
    public static final String USER_UPDATED = "User details updated successfully.";

    public static final String USER_NOT_FOUND = "User not found.";
    public static final String EMAIL_ALREADY_REGISTERED = "The email address is already registered.";
    public static final String INVALID_CREDENTIALS = "Invalid username or password. Please try again.";
    public static final String INTERNAL_SERVER_ERROR_MSG = "An unexpected error occurred. Please try again or contact support.";


    public static String getMessageForStatus(StatusCode statusCode) {
        switch (statusCode) {
            case CREATED:
                return USER_CREATED;
            case OK:
                return USER_LOGGED_IN;
            case NO_CONTENT:
                return USER_DELETED;
            case INTERNAL_SERVER_ERROR:
                return INTERNAL_SERVER_ERROR_MSG;
            default:
                throw new IllegalArgumentException("Unrecognized status code");
        }
    }

    public static String getErrorMessage(String errorCode) {
        switch (errorCode) {
            case "USER_NOT_FOUND":
                return USER_NOT_FOUND;
            case "EMAIL_ALREADY_REGISTERED":
                return EMAIL_ALREADY_REGISTERED;
            case "INVALID_CREDENTIALS":
                return INVALID_CREDENTIALS;
            default:
                return "An unknown error occurred.";
        }
    }
}
