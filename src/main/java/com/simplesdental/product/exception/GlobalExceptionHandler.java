package com.simplesdental.product.exception;

import com.simplesdental.product.model.dto.response.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponseDTO error =
                new ErrorResponseDTO(HttpStatus.BAD_REQUEST, request.getRequestURI(), errors);

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDTO> handleExceptions(
            DataIntegrityViolationException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();

        String message = ex.getMostSpecificCause().getMessage();
        Pattern pattern = Pattern.compile("Key \\((.*?)\\)=\\(.*?\\)(.*)$");
        Matcher matcher = pattern.matcher(message);

        if (matcher.find()) {
            String campo = matcher.group(1);
            String valor = matcher.group(2);
            errors.put(campo, valor.trim());
        }
        ErrorResponseDTO error =
                new ErrorResponseDTO(HttpStatus.BAD_REQUEST, request.getRequestURI(), errors);

        return ResponseEntity.badRequest().body(error);
    }

}
