package com.webspoons.churcheechatservice.service;

import com.webspoons.churcheechatservice.Utility.Contant;
import com.webspoons.churcheechatservice.Utility.CustomValidationException;
import com.webspoons.churcheechatservice.pojo.AuthTokenDetail;
import com.webspoons.churcheechatservice.pojo.ResponseCodes;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final HttpService httpService;
    private final HttpServletRequest httpServletRequest;

    public AuthTokenDetail getAuthTokenDetail() throws Exception{

        var requestHeader = new HashMap<String, String>();
        String userAuth = httpServletRequest.getHeader(Contant.USER_HEADER_AUTH);
        if(StringUtils.isEmpty(userAuth)){
            throw new CustomValidationException("Missing Request Header", ResponseCodes.MISSING_AUTH_HEADER);
        }
        requestHeader.put(Contant.CLIENT_HEADER_AUTH, userAuth);

        var authTokenDetail = httpService.churchClient("user/me", null, HttpMethod.GET, requestHeader, AuthTokenDetail.class);

        Assert.notNull(authTokenDetail.getData(), "Empty Token Detail");
        if(StringUtils.isEmpty(authTokenDetail.getData().get_id())){
            throw new CustomValidationException("Invalid Request Header", ResponseCodes.INVALID_AUTH);
        }
        return authTokenDetail;
    }

    public AuthTokenDetail getAuthTokenDetail(String userAuth) throws Exception{
        var requestHeader = new HashMap<String, String>();
        if(StringUtils.isEmpty(userAuth)){
            throw new CustomValidationException("Missing Request Header", ResponseCodes.MISSING_AUTH_HEADER);
        }
        requestHeader.put(Contant.CLIENT_HEADER_AUTH, userAuth);

        var authTokenDetail = httpService.churchClient("user/me", null, HttpMethod.GET, requestHeader, AuthTokenDetail.class);

        Assert.notNull(authTokenDetail.getData(), "Empty Token Detail");
        if(StringUtils.isEmpty(authTokenDetail.getData().get_id())){
            throw new CustomValidationException("Invalid Request Header", ResponseCodes.INVALID_AUTH);
        }
        return authTokenDetail;
    }
}
