package com.oredata.assignment.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.oredata.assignment.exception.ErrorType.*;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> ExceptionHandler(Exception ex){
        return new ResponseEntity<>(createErrorMessage(ex,ErrorType.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(OredataException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> ExceptionHandler(OredataException ex){
        return new ResponseEntity<>(createErrorMessage(ex,ex.getErrorType()),ex.getErrorType().getHttpStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public final ResponseEntity<ErrorMessage> handleMessageNotReadableException(
            HttpMessageNotReadableException exception) {
        ErrorType errorType = BAD_REQUEST_ERROR;
        return new ResponseEntity<>(createErrorMessage(exception,errorType), errorType.getHttpStatus());
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseBody
    public final ResponseEntity<ErrorMessage> handleInvalidFormatException(
            InvalidFormatException exception) {
        ErrorType errorType = BAD_REQUEST_ERROR;
        return new ResponseEntity<>(createErrorMessage(exception,errorType), errorType.getHttpStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public final ResponseEntity<ErrorMessage> handlePSQLException(
            DataIntegrityViolationException exception) {
        ErrorType errorType = BAD_REQUEST_ERROR;
        return new ResponseEntity<>(createErrorMessage(exception,errorType), errorType.getHttpStatus());
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public final ResponseEntity<ErrorMessage> handleMethodArgumentMisMatchException(
            MethodArgumentTypeMismatchException exception) {

        ErrorType errorType = BAD_REQUEST_ERROR;
        return new ResponseEntity<>(createErrorMessage(exception,errorType), errorType.getHttpStatus());
    }
    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public final ResponseEntity<ErrorMessage> handleSqlxception(
            SQLException exception) {

        ErrorType errorType = BAD_REQUEST_ERROR;
        return new ResponseEntity<>(createErrorMessage(exception,errorType), errorType.getHttpStatus());
    }
    @ExceptionHandler(MissingPathVariableException.class)
    @ResponseBody
    public final ResponseEntity<ErrorMessage> handleMethodArgumentMisMatchException(
            MissingPathVariableException exception) {

        ErrorType errorType = BAD_REQUEST_ERROR;
        return new ResponseEntity<>(createErrorMessage(exception,errorType), errorType.getHttpStatus());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public final ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {

        ErrorType errorType = BAD_REQUEST_ERROR;
        List<String> fields = new ArrayList<>();
        exception
                .getBindingResult()
                .getFieldErrors()
                .forEach(e -> fields.add(e.getField() + ": " + e.getDefaultMessage()));
        ErrorMessage errorMessage = createErrorMessage(exception,errorType);
        errorMessage.setFields(fields);
        return new ResponseEntity<>(errorMessage, errorType.getHttpStatus());
    }



    private ErrorMessage createErrorMessage(Exception ex, ErrorType errorType){

        log.error("Exception.....:"+"\nTime: " +LocalDateTime.now()
                + ",\nmessage: " + ex.getMessage()+",\nstatus: "+errorType.getHttpStatus()+",\ncode: "+errorType.getCode());
        return ErrorMessage.builder()
                .message(errorType.getMessage())
                .code(errorType.getCode())
                .build();
    }



}