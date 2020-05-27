package ti.kt.agh.master.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ti.kt.agh.master.model.Message;
import ti.kt.agh.master.service.GameService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ChatRestController {

    private GameService gameService;

    @Autowired
    public ChatRestController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/message")
    public ResponseEntity postMessage(@RequestBody Message message) {
        gameService.addMessageToList(message);
        System.out.println(System.currentTimeMillis() + " : " + message.getContent());
        return ResponseEntity.ok(HttpStatus.OK + " Message added");
    }

    @GetMapping("/newMessage")
    public List<Message> pollMessages(int lastMessageId) {
        List<Message> messageList = gameService.getMessageList();
        List<Message> responseList = new ArrayList<>();
        while (lastMessageId < messageList.size()) {
            responseList.add(messageList.get(lastMessageId));
            lastMessageId++;
        }
        return responseList;
    }



}
