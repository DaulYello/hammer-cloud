package com.fmkj.chat.server.app;


import com.alibaba.fastjson.JSON;
import com.fmkj.chat.dao.domain.HcAppchat;
import com.fmkj.chat.dao.domain.User;
import com.fmkj.chat.dao.domain.WebMessage;
import com.fmkj.chat.server.service.HcAppchatService;
import com.fmkj.chat.server.utils.SpringContextHandler;
import com.fmkj.common.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketService.class);


    public static Map<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();

    private HcAppchatService hcAppchatService = SpringContextHandler.getBean(HcAppchatService.class);

    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) {
        LOGGER.info("聊天打开onOpen：userId={}", userId);
        if (sessionMap == null) {
            sessionMap = new ConcurrentHashMap<String, Session>();
        }
        sessionMap.put(userId, session);
    }

    @OnClose
    public void OnClose(@PathParam("userId") String userId) {
        LOGGER.info("聊天关闭OnClose：userId={}", userId);
        sessionMap.remove(userId);
    }

    @OnMessage
    public void OnMessage(@PathParam("userId") String userId, Session session, String message) throws IOException{
        LOGGER.info("发送消息：userId={}", userId);
        LOGGER.info("发送消息：message={}", message);
        HcAppchat hcAppchat = JSON.parseObject(message, HcAppchat.class);
        sendMessageTo(hcAppchat);
         //sendMessageAll(message);
    }

    @OnError
    public void error(Session session, Throwable t) {
        LOGGER.error("socket通讯出现异常:", t.getMessage());
        t.printStackTrace();
    }

    public void sendMessageTo(HcAppchat hcAppchat) throws IOException {
        Session se = sessionMap.get(String.valueOf(hcAppchat.getAcceptId()));
        Date now = new Date();
        hcAppchat.setCreateDate(now);
        if(se != null){
            WebMessage webms = new WebMessage();
            hcAppchat.setStatus(1);
            boolean result = hcAppchatService.insert(hcAppchat);
            LOGGER.info("用户在线，直接发送消息：result={}", result);
            webms.setId(hcAppchat.getId());
            webms.setCreatedAt(DateUtil.dateStr(now, "yyyy-MM-dd HH:mm:ss"));
            webms.setText(hcAppchat.getText());
            User user = hcAppchatService.queryUserInfo(hcAppchat.getSendId());
            webms.setUser(user);
            LOGGER.info("发送消息给【" + user.getName() + "】, message={}", JSON.toJSONString(webms));
            se.getAsyncRemote().sendText(JSON.toJSONString(webms));
        }else{
            hcAppchat.setStatus(0);
            boolean result = hcAppchatService.insert(hcAppchat);
            if(result){
                LOGGER.info("接受消息用户不在线，将消息保存数据库成功！");
            }else{
                LOGGER.info("接受消息用户不在线，将消息保存数据库失败！");
            }
        }
    }

    public void sendMessageAll(String message) throws IOException {
        for (Session se : sessionMap.values()) {
            se.getAsyncRemote().sendText(message);
        }
    }

}
