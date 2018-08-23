package com.n.sell.service;


import lombok.extern.slf4j.Slf4j;
import okhttp3.WebSocket;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/websocket")
@Slf4j
public class WebSocketService {

    private Session session;

    private static CopyOnWriteArraySet<WebSocketService> webSocketServices = new CopyOnWriteArraySet();

    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSocketServices.add(this);
    }

    @OnClose
    public void onClose(){
        webSocketServices.remove(this);
    }

    @OnMessage
    public void onMessage(String message){
        log.info("[WebSocket消息], 收到消息{}", message);
    }

    public void sendMessage(String message){
        try {
            for (WebSocketService webSocketService : webSocketServices) {
                webSocketService.session.getBasicRemote().sendText(message);
            }
        } catch (Exception e){
            log.error("[WebSocket消息], 广播消息异常", e);
        }
    }

}
