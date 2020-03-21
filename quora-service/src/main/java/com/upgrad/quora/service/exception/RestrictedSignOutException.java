package com.upgrad.quora.service.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * RestrictedSignOutException is thrown when a user is not signed in the application and tries to sign out of the application.
 */
public class RestrictedSignOutException extends Exception {
    private final String text;
    private final String error;

    public RestrictedSignOutException(final String text, final String error) {
        this.text = text;
        this.error = error;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
    }

    public String getCode() {
        return text;
    }

    public String getErrorMessage() {
        return error;
    }

}
