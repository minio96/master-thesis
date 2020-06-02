package ti.kt.agh.master.model;

import ti.kt.agh.master.model.search.room.Monster;

import java.util.List;

public class NextRound {
    String type;
    Monster monster;
    List<Weapon> weaponList;
    Integer currentPlayer;

    public NextRound(String type, Monster monster, List<Weapon> weaponList, Integer currentPlayer) {
        this.type = type;
        this.monster = monster;
        this.weaponList = weaponList;
        this.currentPlayer = currentPlayer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public List<Weapon> getWeaponList() {
        return weaponList;
    }

    public void setWeaponList(List<Weapon> weaponList) {
        this.weaponList = weaponList;
    }

    public Integer getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Integer currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
