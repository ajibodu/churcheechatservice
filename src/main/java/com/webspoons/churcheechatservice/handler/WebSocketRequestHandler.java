package com.webspoons.churcheechatservice.handler;

import com.webspoons.churcheechatservice.Utility.Contant;
import com.webspoons.churcheechatservice.Utility.RedisKeys;
import com.webspoons.churcheechatservice.Utility.RedisUtil;
import com.webspoons.churcheechatservice.Utility.Utility;
import com.webspoons.churcheechatservice.pojo.OnlineUserDetail;
import com.webspoons.churcheechatservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

@Slf4j
@RequiredArgsConstructor
public class WebSocketRequestHandler implements ChannelInterceptor {

    private final AuthenticationService authenticationService;
    private final RedisUtil<OnlineUserDetail> onlineUserRedis;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        var user = new OnlineUserDetail();
        var simpSessionId = accessor.getSessionId();

//        try {
//            if(StompCommand.CONNECT.equals(accessor.getCommand())){
//                var token = authenticationService.getAuthTokenDetail(accessor.getFirstNativeHeader(Contant.USER_HEADER_AUTH));
//                user = new OnlineUserDetail(token.getData().get_id(), Utility.currentTime(), true, simpSessionId);
//                onlineUserRedis.addValue(RedisKeys.ONLINE_USER_WITH_ID, user.getUserID(), user);
//                onlineUserRedis.addValue(RedisKeys.ONLINE_USER_WITH_SESSION, simpSessionId, user);
//            }else if(StompCommand.SUBSCRIBE.equals(accessor.getCommand())){
//                if(onlineUserRedis.haskey(RedisKeys.ONLINE_USER_WITH_SESSION, simpSessionId)){
//                    user = onlineUserRedis.getValue(RedisKeys.ONLINE_USER_WITH_SESSION, simpSessionId, OnlineUserDetail.class);
//                }else {
//                    throw new Exception("User is not connected");
//                }
//            }
//            else if(StompCommand.DISCONNECT.equals(accessor.getCommand())){
//                if(onlineUserRedis.haskey(RedisKeys.ONLINE_USER_WITH_SESSION, simpSessionId)){
//                    user = onlineUserRedis.getValue(RedisKeys.ONLINE_USER_WITH_SESSION, simpSessionId, OnlineUserDetail.class);
//                    onlineUserRedis.delete(RedisKeys.ONLINE_USER_WITH_SESSION, simpSessionId);
//                    onlineUserRedis.delete(RedisKeys.ONLINE_USER_WITH_ID, user.getUserID());
//                }
//            }
//        }catch (Exception e){
//            log.error(e.getMessage());
//            throw new IllegalArgumentException("No permission for connect");
//        }

        return message;
    }
}
