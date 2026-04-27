package com.kodebytes.acasado.infraestructure.advice;


import com.kodebytes.acasado.domain.error.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class KafkaDemoExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        log.error("Problems with message, details: {} ", ex);
        HttpStatus unprocessableEntity = HttpStatus.UNPROCESSABLE_ENTITY;
        List<String> errors = new ArrayList<>();
        for (org.springframework.validation.FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        return handleExceptionInternal(ex,
                getApiError(unprocessableEntity, null, errors, unprocessableEntity.getReasonPhrase()),
                headers,
                unprocessableEntity,
                request);
    }

    private ApiError getApiError(HttpStatus status, String detail, List<String> errors, String title) {
        return ApiError.builder()
                .status(status.value())
                .detail(detail)
                .errors(errors)
                .title(title)
                .build();
    }

}
