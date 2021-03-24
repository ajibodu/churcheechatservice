package com.webspoons.churcheechatservice.pojo;

public enum ResponseCodes
{
    SUCCESSFUL("00"),
    FAILED_CREATION("01"),
    DUPLICATE_USER_REQUEST("02"),
    VALIDATION_FAILURE("03");

    private String text;

    ResponseCodes(String s) {
        this.text = s;
    }

    public String getText() {
        return this.text;
    }
}
