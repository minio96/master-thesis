package ti.kt.agh.master.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
    private MessageType type;
    private String content;
    private String sender;

    public enum MessageType {
        @JsonProperty("CHAT")
        CHAT,
        @JsonProperty("JOIN")
        JOIN,
        @JsonProperty("LEAVE")
        LEAVE,
        @JsonProperty("FIRST")
        FIRST,
        @JsonProperty("START")
        START,
        @JsonProperty("HIT")
        HIT,
        @JsonProperty("DEAD")
        DEAD
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}