package org.example.onlinecourse.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException{
    private final String title;
    private final String message;
    private final HttpStatus httpStatus;

    public BaseException(String message,String title, HttpStatus httpStatus) {
        super(message);
        this.title = title;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
