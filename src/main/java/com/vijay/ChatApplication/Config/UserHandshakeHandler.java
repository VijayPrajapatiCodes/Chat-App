package com.vijay.ChatApplication.Config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

public class UserHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(
            ServerHttpRequest request,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes) {

        String query = request.getURI().getQuery();

        String tempUserId = "guest"; // fallback

        if (query != null) {
            try {
                String[] params = query.split("&");

                for (String param : params) {
                    if (param.startsWith("userId=")) {
                        tempUserId = param.split("=")[1];
                        break;
                    }
                }
            } catch (Exception e) {
                tempUserId = "guest";
            }
        }

        // ✅ final variable for lambda
        final String userId = tempUserId;

        return () -> userId;
    }
}