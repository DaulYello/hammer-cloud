package com.fmkj.order.server.appchat;




import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: youxun
 * @Date: 2018/8/30 14:16
 * @Description:
 */
@ServerEndpoint("/websocket/{userId}")
@Component
public class WebSocketService {

    public static Map<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();

    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) {
        System.out.println("聊天打开 onOpen: " + userId);
        System.out.println("聊天打开 session: " + session);
        if (sessionMap == null) {
            sessionMap = new ConcurrentHashMap<String, Session>();
        }
        sessionMap.put(userId, session);
    }

    @OnClose
    public void OnClose(@PathParam("userId") String userId) {
        System.out.println("聊天关闭 OnClose: " + userId);
        sessionMap.remove(userId);
    }

    @OnMessage
    public void OnMessage(@PathParam("userId") String userId, Session session, String message) throws IOException{
        System.out.println("发送消息 OnMessage: " + message);
        System.out.println("发送消息 Session: " + session);

        System.out.println("发送消息 userId: " + userId);

         WebMessage webMsg = JSON.parseObject(message, WebMessage.class);
         sendMessageTo(message, String.valueOf(webMsg.getToUserId()));
         //sendMessageAll(message);
    }

    @OnError
    public void error(Session session, Throwable t) {

        t.printStackTrace();
    }

    public void sendMessageTo(String message, String To) throws IOException {
        for (Session se : sessionMap.values()) {
            if ("lilei".equals(To) ){
                se.getAsyncRemote().sendText(message);
            }

        }
    }

    public void sendMessageAll(String message) throws IOException {
        for (Session se : sessionMap.values()) {
            se.getAsyncRemote().sendText(message);
        }
    }
}
