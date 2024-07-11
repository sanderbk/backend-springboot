package io.swagger.api.exception;

import io.swagger.model.dto.ExceptionDTO;
import org.modelmapper.MappingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    // Created methods here to handle certain Exceptions!
    @ExceptionHandler(value = {MappingException.class})
    protected ResponseEntity<ErrorResponse> handleNotFoundException(MappingException ex) {
        var eR = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), MappingException.class.getName());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(eR);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        var eR = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), NotFoundException.class.getName());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(eR);
    }

    // Method to handle ResponseStatusExceptions
    @ExceptionHandler(value = {ResponseStatusException.class})
    protected ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
        ExceptionDTO dto = new ExceptionDTO(ex.getReason());
        return handleExceptionInternal(ex, dto, new HttpHeaders(), ex.getStatus(), request);
    }
}
