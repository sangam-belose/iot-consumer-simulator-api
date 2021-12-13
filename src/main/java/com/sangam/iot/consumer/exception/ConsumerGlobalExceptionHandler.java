package com.sangam.iot.consumer.exception;

import com.sangam.iot.consumer.model.ConsumerErrorResponse;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
@Slf4j
public class ConsumerGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ConsumerErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex) {

        log.error("Method argument not valid: ", ex);

        List<String> errors = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage() + " or null");
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage() + " or null");
        }

        ConsumerErrorResponse error = new ConsumerErrorResponse();

        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(HttpStatus.BAD_REQUEST.name());
        error.setErrors(errors);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex) {
        String errorMessage = ex.getParameterName() + " parameter is missing";

        ConsumerErrorResponse error = new ConsumerErrorResponse(HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(), errorMessage);

        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {

        log.error("Constraint violation : ", ex);

        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath()
                    + ": "
                    + violation.getMessage());
        }

        ConsumerErrorResponse error = new ConsumerErrorResponse();

        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(HttpStatus.BAD_REQUEST.name());
        error.setErrors(errors);

        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex) {

        log.error("Method argument type mismatch: ", ex);

        String errorMessage = ex.getName() + " should be of type " + ex.getRequiredType().getName();

        ConsumerErrorResponse error = new ConsumerErrorResponse(HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(), errorMessage);

        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex) {

        log.error("Http method not supported: ", ex);

        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

        ConsumerErrorResponse error = new ConsumerErrorResponse(
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                HttpStatus.METHOD_NOT_ALLOWED.name(), builder.toString());

        return new ResponseEntity<Object>(error, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex) {

        log.error("Http Media type not supported : ", ex);

        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));

        ConsumerErrorResponse error = new ConsumerErrorResponse(
                HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                HttpStatus.UNSUPPORTED_MEDIA_TYPE.name(),
                builder.substring(0, builder.length() - 2));

        return new ResponseEntity<Object>(error, new HttpHeaders(),
                HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler({Exception.class})

    public ResponseEntity<ConsumerErrorResponse> exceptionHandler(Exception ex) {

        // printing every exception in logger file.
        log.error("Global Exception occured:", ex);

        ConsumerErrorResponse error = new ConsumerErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                "Error in processing request. Try again later!");

        return new ResponseEntity<ConsumerErrorResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
