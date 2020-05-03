package ti.kt.agh.master.service;

import org.springframework.stereotype.Service;
import ti.kt.agh.master.model.ChatMessage;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {

    List<ChatMessage> messageList = new ArrayList();

    public List<ChatMessage> getMessageList() {
        return messageList;
    }

    public void addMessageToList(ChatMessage message) {
        messageList.add(message);
        System.out.println("New message: " + message.getContent());
    }

}
