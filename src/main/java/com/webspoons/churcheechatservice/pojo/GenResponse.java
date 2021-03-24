package com.webspoons.churcheechatservice.pojo;

import lombok.Data;

@Data
public class GenResponse<T> extends Response {

    private T data;
}
