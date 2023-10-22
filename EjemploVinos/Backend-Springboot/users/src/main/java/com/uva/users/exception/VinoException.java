package com.uva.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class VinoException extends RuntimeException{

    public VinoException(String mensaje) {
        super(mensaje);
    }
}
