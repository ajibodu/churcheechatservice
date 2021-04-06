package com.webspoons.churcheechatservice.handler;

import com.webspoons.churcheechatservice.Utility.CustomValidationException;
import com.webspoons.churcheechatservice.pojo.Response;
import com.webspoons.churcheechatservice.pojo.ResponseCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = CustomValidationException.class)
    public ResponseEntity<Object> handleValidationException(CustomValidationException ex, WebRequest request){
        Object bodyOfResponse = ex.getResponse();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleMainException(Exception ex, WebRequest request){
        log.error(ex.getMessage());
        ex.printStackTrace();
        var bodyOfResponse = new Response();
        bodyOfResponse.mapResponseCode(ResponseCodes.SYSTEM_EXCEPTION, ex.getMessage());
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
