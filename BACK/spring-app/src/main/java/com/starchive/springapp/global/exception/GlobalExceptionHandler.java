package com.starchive.springapp.global.exception;

import com.starchive.springapp.category.exception.CategoryAlreadyExistsException;
import com.starchive.springapp.category.exception.CategoryNotFoundException;
import com.starchive.springapp.global.dto.ErrorResult;
import com.starchive.springapp.hashtag.exception.HashTagNotFoundException;
import com.starchive.springapp.post.exception.InvalidPasswordException;
import com.starchive.springapp.post.exception.PostNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 유효성 검증 실패 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // 필드 에러를 순회하며 에러 메시지 수집
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler({CategoryNotFoundException.class, PostNotFoundException.class, HashTagNotFoundException.class})
    public ResponseEntity<ErrorResult> handleNotFoundException(RuntimeException ex) {
        ErrorResult errorResult = new ErrorResult(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.badRequest()
                .body(errorResult);
    }

    @ExceptionHandler({CategoryAlreadyExistsException.class})
    public ResponseEntity<ErrorResult> handleAlreadyExistsException(RuntimeException ex) {
        ErrorResult errorResult = new ErrorResult(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.badRequest()
                .body(errorResult);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResult> handleRuntimeException(RuntimeException ex) {
        ErrorResult errorResult = new ErrorResult(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return ResponseEntity.internalServerError().body(errorResult);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResult> handleInvalidPasswordException(InvalidPasswordException ex) {
        ErrorResult errorResult = new ErrorResult(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResult);
    }
}
