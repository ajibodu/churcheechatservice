
package com.webspoons.churcheechatservice.pojo;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AuthTokenDetail {

    @SerializedName("data")
    private AuthTokenData mAuthTokenData;
    @SerializedName("msg")
    private String mMsg;

    public AuthTokenData getData() {
        return mAuthTokenData;
    }

    public void setData(AuthTokenData authTokenData) {
        mAuthTokenData = authTokenData;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

}
