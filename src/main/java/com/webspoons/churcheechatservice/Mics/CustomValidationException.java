package com.webspoons.churcheechatservice.Mics;

import com.webspoons.churcheechatservice.pojo.Response;
import com.webspoons.churcheechatservice.pojo.ResponseCodes;
import lombok.Data;

@Data
public class CustomValidationException extends Exception{

    public CustomValidationException(String errorMessage){
        response = new Response();
        response.mapResponseCode(ResponseCodes.VALIDATION_FAILURE, errorMessage);
    }
    public CustomValidationException(String errorMessage, ResponseCodes responseCodes){
        response = new Response();
        response.mapResponseCode(responseCodes, errorMessage);
    }

    private Response response;


}
