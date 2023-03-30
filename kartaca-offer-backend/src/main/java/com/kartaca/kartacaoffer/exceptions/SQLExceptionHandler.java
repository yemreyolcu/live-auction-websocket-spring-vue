package com.kartaca.kartacaoffer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SQLExceptionHandler extends RuntimeException{
    public SQLExceptionHandler(String message) {
        super(message);
    }
}