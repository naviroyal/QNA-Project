package com.upgrad.quora.service.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * UserNotFoundException is thrown when the user is not found in the database.
 */
public class UserNotFoundException extends Exception {
    private final String text;
    private final String error;

    public UserNotFoundException(final String text, final String error) {
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
