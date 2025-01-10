package com.example.Task.Websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class WebSocketController {
    private final WebSocketServer webSocketServer;

    public WebSocketController(WebSocketServer webSocketServer) {
        this.webSocketServer = webSocketServer;
    }
    @GetMapping
    public String getHomePage(){
        return "index";
    }
    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public String sendMessage(String message) {
        System.out.println(message);
        return "Server nhận: " + message;
    }
    @PostMapping("/broadcast")
    public String broadcastMessage(@RequestBody String message) {
        webSocketServer.broadcastMessage(message);
        return "Message broadcasted: " + message;
    }

}
