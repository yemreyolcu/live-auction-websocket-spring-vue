package com.kartaca.kartacaoffer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class JWTExpiredHandler extends RuntimeException {
    public JWTExpiredHandler(String message) {
        super(message);
    }
}
