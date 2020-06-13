package ti.kt.agh.master.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ti.kt.agh.master.model.Message;
import ti.kt.agh.master.model.Player;
import ti.kt.agh.master.model.Response;
import ti.kt.agh.master.service.GameService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GameRestController {

    private GameService gameService;

    @Autowired
    public GameRestController(GameService gameService) {
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

    @GetMapping("/newPlayer/{username}")
    public Player newPlayer(@PathVariable String username) {
        return gameService.getPlayerList().stream()
                .filter(player -> username.equals(player.getName()))
                .collect(Collectors.toList()).get(0);
    }

    @GetMapping("poll")
    public List<Response> poll(int playerID) {
        return gameService.getEventsForPlayer(playerID);
    }

    @GetMapping("status")
    public String getStatus() {
        return "status OK";
    }
}
