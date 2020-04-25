package ti.kt.agh.master.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ti.kt.agh.master.model.ChatMessage;
import ti.kt.agh.master.service.ChatService;

@RestController
public class ChatRestController {

    private ChatService chatService;

    @Autowired
    public ChatRestController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/message")
    public void postMessage(@RequestBody ChatMessage chatMessage) {
        System.out.println(System.currentTimeMillis());
    }

}
