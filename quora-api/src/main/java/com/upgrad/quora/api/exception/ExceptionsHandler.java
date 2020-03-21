package com.upgrad.quora.api.exception;

import com.upgrad.quora.api.model.ErrorResponse;
import com.upgrad.quora.service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorResponse> authenticationFiledException(AuthenticationFailedException exe, WebRequest request){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exe.getCode()).message(exe.getErrorMessage()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(QuestionNotValidException.class)
    public ResponseEntity<ErrorResponse> authenticationFiledException(QuestionNotValidException exe, WebRequest request){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exe.getCode()).message(exe.getErrorMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RestrictedSignUpException.class)
    public ResponseEntity<ErrorResponse> authenticationFiledException(RestrictedSignUpException exe, WebRequest request){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exe.getCode()).message(exe.getErrorMessage()),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RestrictedSignOutException.class)
    public ResponseEntity<ErrorResponse> authenticationFiledException(RestrictedSignOutException exe, WebRequest request){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exe.getCode()).message(exe.getErrorMessage()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthorizationFailedException.class)
    public ResponseEntity<ErrorResponse> authenticationFiledException(AuthorizationFailedException exe, WebRequest request){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exe.getCode()).message(exe.getErrorMessage()),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AnswerNotFoundException.class)
    public ResponseEntity<ErrorResponse> authenticationFiledException(AnswerNotFoundException exe, WebRequest request){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exe.getCode()).message(exe.getErrorMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> authenticationFiledException(UserNotFoundException exe, WebRequest request){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exe.getCode()).message(exe.getErrorMessage()),
                HttpStatus.NOT_FOUND);
    }

}
