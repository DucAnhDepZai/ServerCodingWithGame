package com.example.Task.Websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class WebSocketServer extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // Thêm session mới vào danh sách
        sessions.add(session);
        System.out.println("New client connected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Xử lý tin nhắn từ client
        System.out.println("Received message: " + message.getPayload());
        // Có thể gửi phản hồi lại nếu cần
        session.sendMessage(new TextMessage("Server received: " + message.getPayload()));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
        // Xóa session khi client ngắt kết nối
        sessions.remove(session);
        System.out.println("Client disconnected: " + session.getId());
    }

    // Phương thức broadcastMessage
    public void broadcastMessage(String message) {
        synchronized (sessions) {
            for (WebSocketSession session : sessions) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    System.err.println("Error sending message to client: " + session.getId());
                }
            }
        }
    }
}

