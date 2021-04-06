package com.webspoons.churcheechatservice.service;

import com.mongodb.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class HttpService {

    @Value("${baseurl.client}")
    public String BASE_URL_CLIENT;

    private final RestTemplate restTemplate;

    public <Req, Resp> Resp churchClient(String url, @Nullable Req requestObject, HttpMethod httpMethod, HashMap<String, String> headers, Class<Resp> clazz){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        for(var key: headers.keySet()){
            httpHeaders.add(key, headers.get(key));
        }

        HttpEntity<Req> request = new HttpEntity<Req>(requestObject, httpHeaders);
        var  serviceResp = restTemplate.exchange(BASE_URL_CLIENT.concat(url), httpMethod, request, clazz).getBody();

        return serviceResp;
    }
}
