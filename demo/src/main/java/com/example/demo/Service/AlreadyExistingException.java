package com.example.demo.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExistingException extends RuntimeException{
    public AlreadyExistingException(String s){
        super(s);
    }
}
