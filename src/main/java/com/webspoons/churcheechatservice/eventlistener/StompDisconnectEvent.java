//package com.webspoons.churcheechatservice.eventlistener;
//
//import com.webspoons.churcheechatservice.Utility.RedisKeys;
//import com.webspoons.churcheechatservice.Utility.RedisUtil;
//import com.webspoons.churcheechatservice.Utility.Utility;
//import com.webspoons.churcheechatservice.pojo.OnlineUserDetail;
//import lombok.AllArgsConstructor;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.ApplicationListener;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.messaging.SessionDisconnectEvent;
//
//@Slf4j
//@Component
//@AllArgsConstructor
//public class StompDisconnectEvent implements ApplicationListener<SessionDisconnectEvent> {
//
//    private final RedisUtil<OnlineUserDetail> onlineUserRedis;
//
//    @SneakyThrows
//    @Override
//    public void onApplicationEvent(SessionDisconnectEvent event) {
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
//        var user = new OnlineUserDetail();
//        var simpSessionId = accessor.getSessionId();
//        if(onlineUserRedis.haskey(RedisKeys.ONLINE_USER_WITH_SESSION, simpSessionId)){
//            user = onlineUserRedis.getValue(RedisKeys.ONLINE_USER_WITH_SESSION, simpSessionId, OnlineUserDetail.class);
//            onlineUserRedis.delete(RedisKeys.ONLINE_USER_WITH_SESSION, simpSessionId);
//            onlineUserRedis.delete(RedisKeys.ONLINE_USER_WITH_ID, user.getUserID());
//        }
//    }
//}
