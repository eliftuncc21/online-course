package org.example.onlinecourse.exception;

import org.springframework.http.HttpStatus;

public class ErrorMessage extends BaseException {
    public ErrorMessage(String message, String title, HttpStatus httpStatus) {
        super(message, title, httpStatus);
    }
}
