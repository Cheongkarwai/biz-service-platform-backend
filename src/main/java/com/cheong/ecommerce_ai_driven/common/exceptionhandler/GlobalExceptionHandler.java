package com.cheong.ecommerce_ai_driven.common.exceptionhandler;

import com.cheong.ecommerce_ai_driven.account.exception.AccountStateInvalidException;
import com.cheong.ecommerce_ai_driven.common.dto.ValidationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public HttpEntity<ObjectNode> handleValidationException(ValidationException e) {
        ObjectNode body = new ObjectMapper().createObjectNode();
        body.put("message", e.getMessage());
        body.put("code", e.getCode());
        body.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(body, HttpStatusCode.valueOf(e.getHttpStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public HttpEntity<ObjectNode> handleValidationException(MethodArgumentNotValidException e) {
        ObjectNode body = new ObjectMapper().createObjectNode();
        body.put("message", e.getMessage());
        body.put("code", 400);
        body.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public HttpEntity<ObjectNode> handleWebExchangeBindException(WebExchangeBindException e) {
        ObjectNode body = new ObjectMapper().createObjectNode();

        // Get the first validation error
        String errorMessage = e.getBindingResult().getAllErrors().isEmpty()
                ? "Validation failed"
                : e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        body.put("message", errorMessage);
        body.put("code", "VALIDATION_FAILED");
        body.put("timestamp", System.currentTimeMillis());

        // Add detailed field errors
        ObjectNode fieldErrors = new ObjectMapper().createObjectNode();
        e.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );
        body.set("fieldErrors", fieldErrors);

        return new ResponseEntity<>(body, HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(AccountStateInvalidException.class)
    public HttpEntity<ObjectNode> handleAccountStateInvalidException(AccountStateInvalidException e) {
        ObjectNode body = new ObjectMapper().createObjectNode();
        body.put("message", e.getMessage());
        body.put("code", 400);
        body.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.badRequest().body(body);
    }

}
