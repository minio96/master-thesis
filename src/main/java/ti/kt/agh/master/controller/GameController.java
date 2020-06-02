package ti.kt.agh.master.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import ti.kt.agh.master.model.*;
import ti.kt.agh.master.model.search.room.Monster;
import ti.kt.agh.master.service.GameService;
import ti.kt.agh.master.utils.CardShuffler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class GameController {

    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    private GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @MessageMapping("/game.sendMessage")
    @SendTo("/topic/public")
    public Message sendMessage(@Payload Message message) {
        System.out.println(System.currentTimeMillis());
        gameService.addMessageToList(message);
        return message;
    }

    @MessageMapping("/game.addUser")
    @SendToUser("/game")
    public Player addUser(@Payload Message message,
                          SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", message.getSender());
        Player player = new Player(gameService.getNextID(), message.getSender(), 1, 0);
        gameService.addPlayer(player);
        return player;
    }

    @MessageMapping("/game.startGame")
    @SendTo("/game")
    public StartSetList startGame(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) {
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", message.getSender());
        gameService.startGame();
        logger.info("starting game");
        List<StartSet> startSetList = new ArrayList<>();
        for (Player player : gameService.getPlayerList()) {
            List<Weapon> weaponList = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                weaponList.add(CardShuffler.getInstance().getWeapons().get((int) (1 + Math.random() * 19)));
            }
            startSetList.add(new StartSet(weaponList, CardShuffler.getInstance().getMonsters().get(CardShuffler.getInstance().getCurrentMonster())));
        }
        gameService.setCurrentPlayer(0);
        return new StartSetList("start", startSetList);
    }

    @MessageMapping("/game.hitMonster")
    @SendTo("/game")
    public Monster hitMonster(@Payload Message message) {
        int currentMonster = CardShuffler.getInstance().getCurrentMonster();
        int health = CardShuffler.getInstance().getMonsters().get(currentMonster).getHealth();
        if (Integer.parseInt(message.getSender()) == (gameService.getCurrentPlayer())) {
            if (health - Integer.parseInt(message.getContent()) > 0) {
                CardShuffler.getInstance().getMonsters().get(currentMonster).setHealth(
                        health
                                - Integer.parseInt(message.getContent()));
            } else {
                //TODO set clock to 0
                CardShuffler.getInstance().getMonsters().get(currentMonster).setType("deadMonster");
            }
        } else {
            if (health > 0) {
                CardShuffler.getInstance().getMonsters().get(currentMonster).setHealth(
                        health
                                + Integer.parseInt(message.getContent()));
            } else {
                //TODO set clock to 0
                CardShuffler.getInstance().getMonsters().get(currentMonster).setType("deadMonster");
            }

        }
        return CardShuffler.getInstance().getMonsters().get(currentMonster);
    }

    @MessageMapping("/game.deadMonster")
    @SendTo("/game")
    public NextRound deadMonster(@Payload Message message) {
        int treasures = Integer.parseInt(message.getContent());
        List<Weapon> weaponList = new ArrayList<>();
        for (int i = 0; i < treasures; i++) {
            weaponList.add(CardShuffler.getInstance().getWeapons().get((int) (1 + Math.random() * 20)));
        }
        gameService.setCurrentPlayer(gameService.getCurrentPlayer() + 1);
        Monster monster = CardShuffler.getInstance().getMonsters().get(CardShuffler.getInstance().getCurrentMonster() + 1);
        CardShuffler.getInstance().setCurrentMonster(CardShuffler.getInstance().getCurrentMonster() + 1);
        return new NextRound("nextRound", monster, weaponList, gameService.getCurrentPlayer());
    }
}