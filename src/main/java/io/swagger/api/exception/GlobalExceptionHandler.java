package io.swagger.api.exception;

import io.swagger.model.dto.ExceptionDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    // Created methods here to handle certain Exceptions!


    // Method to handle ResponseStatusExceptions
    @ExceptionHandler(value = {ResponseStatusException.class})
    protected ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
        ExceptionDTO dto = new ExceptionDTO(ex.getReason());
        return handleExceptionInternal(ex, dto, new HttpHeaders(), ex.getStatus(), request);
    }
}
