package com.upgrad.quora.service.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * QuestionNotValidException is thrown when the question is not found in the database.
 */
public class QuestionNotValidException extends Exception {
    private final String text;
    private final String error;

    public QuestionNotValidException(final String text, final String error) {
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

