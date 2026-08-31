package org.example;

public class ChatMessage {
    private String type; // "JOIN", "LEAVE", "CHAT", "DM", "USER_LIST", "HISTORY"
    private String sender;
    private String recipient; // null for broadcast, target username for DM
    private String content;

    public ChatMessage() {}

    public ChatMessage(String type, String sender, String recipient, String content) {
        this.type = type;
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}