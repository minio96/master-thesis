package ti.kt.agh.master.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ti.kt.agh.master.model.ChatMessage;
import ti.kt.agh.master.service.ChatService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ChatRestController {

    private ChatService chatService;

    @Autowired
    public ChatRestController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/message")
    public ResponseEntity postMessage(@RequestBody ChatMessage chatMessage) {
        chatService.addMessageToList(chatMessage);
        System.out.println(System.currentTimeMillis() + " : " + chatMessage.getContent());
        return ResponseEntity.ok(HttpStatus.OK + " Message added");
    }

    @GetMapping("/newMessage")
    public List<ChatMessage> pollMessages(int lastMessageId) {
        List<ChatMessage> messageList = chatService.getMessageList();
        List<ChatMessage> responseList = new ArrayList<>();
        while (lastMessageId < messageList.size()) {
            responseList.add(messageList.get(lastMessageId));
            lastMessageId++;
        }
        return responseList;
    }



}
