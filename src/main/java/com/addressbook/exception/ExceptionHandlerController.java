package com.addressbook.exception;

import com.addressbook.model.Errors;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler implements ErrorController {

    private static final String PATH = "/error";

    @Override
    public String getErrorPath() {
        return PATH;
    }

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseEntity<Errors> exception(Exception ex, WebRequest request) {
//        request.getDescription(false);
//
//        Errors errors = Errors.builder()
//                .code(500)
//                .message("Server Error")
//                .details(null)
//                .build();
//
//        return new ResponseEntity(errors, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Errors> responseStatusException(Exception ex, WebRequest request) {
        request.getDescription(false);

        Errors errors = Errors.builder()
                .code(404)
                .message("Not Found")
                .details(Arrays.asList(ex.getMessage()))
                .build();

        return new ResponseEntity(errors, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Errors errors = Errors.builder()
            .code(400)
            .message("Bad Request")
            .details(getErrors(ex))
            .build();

        return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
    }

    private List<String> getErrors(BindException ex) {
        return ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());
    }
}

