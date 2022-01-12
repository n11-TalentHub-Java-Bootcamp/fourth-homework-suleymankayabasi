package com.suleymankayabasi.fourthhomework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    //404
    @ExceptionHandler
    public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest webRequest) {

        Date errorDate = new Date();
        String message = ex.getMessage();
        String description = webRequest.getDescription(false);

        ExceptionResponseDetail exceptionResponseDetail = new ExceptionResponseDetail(errorDate, message, description);

        return new ResponseEntity(exceptionResponseDetail, HttpStatus.NOT_FOUND);
    }

    public final ResponseEntity<Object> handleCollectionNotFoundException(CollectionNotFoundException ex,WebRequest webRequest){

        Date errorDate = new Date();
        String message = ex.getMessage();
        String description = webRequest.getDescription(false);

        ExceptionResponseDetail exceptionResponseDetail = new ExceptionResponseDetail(errorDate, message, description);

        return new ResponseEntity(exceptionResponseDetail, HttpStatus.NOT_FOUND);
    }

    public final ResponseEntity<Object> handleLoanNotFoundException(LoanNotFoundException ex,WebRequest webRequest){

        Date errorDate = new Date();
        String message = ex.getMessage();
        String description = webRequest.getDescription(false);

        ExceptionResponseDetail exceptionResponseDetail = new ExceptionResponseDetail(errorDate, message, description);

        return new ResponseEntity(exceptionResponseDetail, HttpStatus.NOT_FOUND);
    }

    //500
    @ExceptionHandler
    public final ResponseEntity<Object> handleAll(Exception ex, WebRequest webRequest) {

        Date errorDate = new Date();
        String message = ex.getMessage();
        String description = webRequest.getDescription(false);

        ExceptionResponseDetail exceptionResponseDetail = new ExceptionResponseDetail(errorDate, message, description);

        return new ResponseEntity(exceptionResponseDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
