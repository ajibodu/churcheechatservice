package com.webspoons.churcheechatservice.pojo;

public enum ResponseCodes
{
    SUCCESSFUL("00"),
    FAILED_CREATION("01"),
    DUPLICATE_REQUEST("02"),
    VALIDATION_FAILURE("03"),
    INVALID_AUTH("04"),
    MISSING_AUTH_HEADER("05"),
    SYSTEM_EXCEPTION("99");

    private String text;

    ResponseCodes(String s) {
        this.text = s;
    }

    public String getText() {
        return this.text;
    }
}
