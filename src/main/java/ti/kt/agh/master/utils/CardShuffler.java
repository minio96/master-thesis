package ti.kt.agh.master.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ti.kt.agh.master.model.Weapon;
import ti.kt.agh.master.model.search.room.Curse;
import ti.kt.agh.master.model.search.room.Monster;
import ti.kt.agh.master.service.GameService;

import java.util.ArrayList;
import java.util.List;

public class CardShuffler {
    private static final Logger logger = LoggerFactory.getLogger(GameService.class);

    private List<Monster> monsters;
    private List<Curse> curses;
    private List<Weapon> weapons;
    private int currentMonster;

    private static CardShuffler cardShuffler;

    private CardShuffler(List<Monster> monsters, List<Curse> curses, List<Weapon> weapons, int currentMonster) {
        this.monsters = monsters;
        this.curses = curses;
        this.weapons = weapons;
        this.currentMonster = currentMonster;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public List<Curse> getCurses() {
        return curses;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public void setCurrentMonster(int currentMonster) {
        this.currentMonster = currentMonster;
    }

    public static void shuffleCards() {
        List<Monster> monsters = new ArrayList<>();
        List<Curse> curses = new ArrayList<>();
        List<Weapon> weapons = new ArrayList<>();

        for (int i = 0; i <20; i++) {
            Monster monster = new Monster("Monster " + i, (int)(1 + Math.random()*10), (int)(1 + Math.random()*4));
            monsters.add(monster);
            logger.info(monster.toString());
            Curse curse = new Curse("Curse " + i, (int)(1 + Math.random()*4));
            curses.add(curse);
            Weapon weapon = new Weapon("Weapon " + i, Weapon.WeaponType.ONE_TIME, (int)(1 + Math.random()*5));
            weapons.add(weapon);
        }
        logger.info("Cards shuffled");
        cardShuffler = new CardShuffler(monsters, curses, weapons, 0);
    }

    public int getCurrentMonster() {
        return currentMonster;
    }

    public static CardShuffler getInstance(){
        return cardShuffler;
    }
}
