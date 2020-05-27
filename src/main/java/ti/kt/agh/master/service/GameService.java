package ti.kt.agh.master.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ti.kt.agh.master.model.Message;
import ti.kt.agh.master.model.Player;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    private static final Logger logger = LoggerFactory.getLogger(GameService.class);

    List<Player> playerList = new ArrayList<>();
    List<Message> messageList = new ArrayList();
    Integer id = 0;

    public List<Message> getMessageList() {
        return messageList;
    }

    public void addMessageToList(Message message) {
        messageList.add(message);
        System.out.println("New message: " + message.getContent());
    }

    public List<Player> addPlayer(Player player) {
        playerList.add(player);
        logger.info("Added {}", player.toString());
        return playerList;
    }

    public Integer getNextID() {
        this.id +=1;
        return this.id-1;
    }

}
