package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatHandler extends TextWebSocketHandler {

    // Map username -> WebSocketSession
    private final ConcurrentHashMap<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();
    // Session ID -> username map for cleanup on disconnect
    private final ConcurrentHashMap<String, String> sessionUserMap = new ConcurrentHashMap<>();
    // In-memory public chat history buffer
    private final List<ChatMessage> history = new CopyOnWriteArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        ChatMessage message = objectMapper.readValue(textMessage.getPayload(), ChatMessage.class);

        if ("JOIN".equals(message.getType())) {
            String username = message.getSender();
            userSessions.put(username, session);
            sessionUserMap.put(session.getId(), username);

            // 1. Send chat history to newly joined user
            for (ChatMessage oldMsg : history) {
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(oldMsg)));
            }

            // 2. Broadcast updated user list to everyone
            broadcastUserList();
            return;
        }

        if ("CHAT".equals(message.getType())) {
            // Save to memory buffer
            history.add(message);
            // Broadcast message to everyone
            for (WebSocketSession s : userSessions.values()) {
                if (s.isOpen()) {
                    s.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
                }
            }
        } else if ("DM".equals(message.getType())) {
            // Direct Message handling
            WebSocketSession recipientSession = userSessions.get(message.getRecipient());
            String jsonMessage = objectMapper.writeValueAsString(message);

            // Send to target user
            if (recipientSession != null && recipientSession.isOpen()) {
                recipientSession.sendMessage(new TextMessage(jsonMessage));
            }
            // Also mirror to sender so it appears on their screen
            if (session.isOpen() && !message.getSender().equals(message.getRecipient())) {
                session.sendMessage(new TextMessage(jsonMessage));
            }
        }
    }

    private void broadcastUserList() throws Exception {
        List<String> users = new ArrayList<>(userSessions.keySet());
        ChatMessage listMessage = new ChatMessage("USER_LIST", "SERVER", null, objectMapper.writeValueAsString(users));
        String json = objectMapper.writeValueAsString(listMessage);

        for (WebSocketSession s : userSessions.values()) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage(json));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        String username = sessionUserMap.remove(session.getId());
        if (username != null) {
            userSessions.remove(username);
            broadcastUserList(); // Update online users list for everyone
        }
    }
}