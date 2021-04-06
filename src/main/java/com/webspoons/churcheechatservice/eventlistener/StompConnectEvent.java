//package com.webspoons.churcheechatservice.eventlistener;
//
//import com.webspoons.churcheechatservice.Utility.RedisKeys;
//import com.webspoons.churcheechatservice.Utility.RedisUtil;
//import com.webspoons.churcheechatservice.Utility.Utility;
//import com.webspoons.churcheechatservice.pojo.OnlineUserDetail;
//import com.webspoons.churcheechatservice.service.AuthenticationService;
//import lombok.AllArgsConstructor;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.EventListener;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.messaging.SessionConnectEvent;
//
//@Slf4j
//@AllArgsConstructor
//@Component
//public class StompConnectEvent implements ApplicationListener<SessionConnectEvent> {
//
//    private final AuthenticationService authenticationService;
//    private final RedisUtil<OnlineUserDetail> onlineUserRedis;
//
//    @Override
//    public void onApplicationEvent(SessionConnectEvent event) {
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
//        var user = new OnlineUserDetail();
//        var simpSessionId = accessor.getSessionId();
//        try {
//            if(onlineUserRedis.haskey(RedisKeys.ONLINE_USER_WITH_SESSION, simpSessionId)){
//                user = onlineUserRedis.getValue(RedisKeys.ONLINE_USER_WITH_SESSION, simpSessionId, OnlineUserDetail.class);
//                user.setConnectTime(Utility.currentTime());
//            }else {
//                var token = authenticationService.getAuthTokenDetail();
//                user = new OnlineUserDetail(token.getData().get_id(), Utility.currentTime(), true, simpSessionId);
//            }
//            onlineUserRedis.addValue(RedisKeys.ONLINE_USER_WITH_ID, user.getUserID(), user);
//            onlineUserRedis.addValue(RedisKeys.ONLINE_USER_WITH_SESSION, simpSessionId, user);
//        }catch (Exception e){
//            throw new IllegalArgumentException("No permission for connection");
//        }
//    }
//}
