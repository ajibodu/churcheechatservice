package com.webspoons.churcheechatservice.pojo;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class Response {

    private String responseCode;
    private String responseDesc;
    private String description;

    public void mapResponseCode(ResponseCodes responseCode){
        this.setResponseCode(responseCode.getText());
        this.setResponseDesc(responseCode.toString());

        this.setDescription(responseCode.toString());
    }

    public void mapResponseCode(ResponseCodes responseCode, String description){
        this.setResponseCode(responseCode.getText());
        this.setResponseDesc(responseCode.toString());

        this.setDescription(description);
    }
}
