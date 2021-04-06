//package com.webspoons.churcheechatservice.eventlistener;
//
//import com.webspoons.churcheechatservice.Utility.RedisKeys;
//import com.webspoons.churcheechatservice.Utility.RedisUtil;
//import com.webspoons.churcheechatservice.Utility.Utility;
//import com.webspoons.churcheechatservice.pojo.OnlineUserDetail;
//import lombok.RequiredArgsConstructor;
//import lombok.SneakyThrows;
//import org.springframework.context.ApplicationListener;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.messaging.SessionSubscribeEvent;
//
//@Component
//@RequiredArgsConstructor
//public class StompSubscribeEvent implements ApplicationListener<SessionSubscribeEvent> {
//
//    private final RedisUtil<OnlineUserDetail> onlineUserRedis;
//
//    @SneakyThrows
//    @Override
//    public void onApplicationEvent(SessionSubscribeEvent event) {
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
//        var user = new OnlineUserDetail();
//        var simpSessionId = accessor.getSessionId();
//        if(onlineUserRedis.haskey(RedisKeys.ONLINE_USER_WITH_SESSION, simpSessionId)){
//            user = onlineUserRedis.getValue(RedisKeys.ONLINE_USER_WITH_SESSION, simpSessionId, OnlineUserDetail.class);
//        }else {
//            throw new IllegalArgumentException("No permission");
//        }
//    }
//}
