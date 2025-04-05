package com.simplesdental.product.exception;

import com.simplesdental.product.model.dto.response.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final Marker CRITICAL = MarkerFactory.getMarker("CRITICAL");

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request)  {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponseDTO error =
                new ErrorResponseDTO(HttpStatus.BAD_REQUEST, request.getRequestURI(), errors);
        log.warn("{}", error);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleExceptions(
            DataIntegrityViolationException ex, HttpServletRequest request)  {
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
        log.warn("{}", error);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(RedisConnectionFailureException.class)
    public ResponseEntity<ErrorResponseDTO> handleRedisConnectionFailureException(RedisConnectionFailureException ex, HttpServletRequest request) {
        ErrorResponseDTO error = new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, request.getRequestURI(), ex.getMessage());

        log.error("CRITICAL: {}", error);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<ErrorResponseDTO> handleConnectException(ConnectException ex, HttpServletRequest request) {
        ErrorResponseDTO error = new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, request.getRequestURI(), ex.getMessage());

        log.error("CRITICAL: {}", error);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
