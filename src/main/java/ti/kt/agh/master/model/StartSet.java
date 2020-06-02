package ti.kt.agh.master.model;

import ti.kt.agh.master.model.search.room.Monster;

import java.util.List;

public class StartSet {
    private List<Weapon> weaponList;
    private Monster monster;

    public StartSet(List<Weapon> weaponList, Monster monster) {
        this.weaponList = weaponList;
        this.monster = monster;
    }

    public List<Weapon> getWeaponList() {
        return weaponList;
    }

    public void setWeaponList(List<Weapon> weaponList) {
        this.weaponList = weaponList;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }
}
