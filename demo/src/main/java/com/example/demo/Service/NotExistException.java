package com.example.demo.Service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class NotExistException extends RuntimeException{
    public NotExistException(String message) {
        super(message);
    }
}
