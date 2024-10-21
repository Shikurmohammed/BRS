package com.BRS.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;

import com.BRS.entity.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // @ExceptionHandler is an annotation used to handle exceptions in centralized
    // way, used to customize responses, avoid unnecessary try catch
    @ExceptionHandler(NotFoundException.class)
    // Handle Not Found Exception
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex, WebRequest req) {
        // Using WebRequest parameter , we can access the request metadata,
        // and we can set the details of the error to our custom details variable in
        // ErrorResponse class
        logger.error(ex.getMessage());
        ErrorResponse errorRes = new ErrorResponse(ex.getMessage(), req.getDescription(true),
                HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorRes, HttpStatus.NOT_FOUND);

    }

    // Handle Invalid Data Exception
    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<?> handleInvalidDataException(InvalidDataException ex, WebRequest req) {
        ErrorResponse errorRes = new ErrorResponse(ex.getMessage(), req.getDescription(false),
                HttpStatus.BAD_REQUEST.value());
        logger.error(ex.getMessage());
        return new ResponseEntity<>(errorRes, HttpStatus.BAD_REQUEST);
    }

    // Handle Expired token exception
    @ExceptionHandler(JwtTokenExpiredException.class)
    public ResponseEntity<?> handleJwtTokenExpiredException(JwtTokenExpiredException ex, WebRequest req) {
        logger.error(ex.getMessage());
        System.out.println("Expired token");
        ErrorResponse errRes = new ErrorResponse(ex.getMessage(), req.getDescription(false),
                HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(errRes, HttpStatus.UNAUTHORIZED);
    }

    // handle Invalid token exception
    @ExceptionHandler(JwtTokenMalformedException.class)
    public ResponseEntity<?> handleJwtTokenMalFormedException(JwtTokenMalformedException ex, WebRequest req) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), null, HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST);
    }

    // Handle Sql Exceptions
    @ExceptionHandler(DatabaseOperationException.class)
    public ResponseEntity<?> handleDatabaseOperationException(DatabaseOperationException ex) {
        ErrorResponse errorRes = new ErrorResponse();
        errorRes.setDetails(null);
        errorRes.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorRes, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<?> handleDuplicateKeyException(DuplicateKeyException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.CONFLICT.value()); // 409 Conflict
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    // handler Multipart exception
    @ExceptionHandler(handleMultipartException.class)
    public ResponseEntity<String> handleMultipartException(MultipartException ex) {
        System.out.println("Multipart file error: ");
        return new ResponseEntity<>("Multipart file error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Handle all other Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception e, WebRequest req) {
        logger.error(e.getMessage());
        ErrorResponse errorRes = new ErrorResponse(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorRes, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
