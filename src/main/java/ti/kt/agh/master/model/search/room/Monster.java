package ti.kt.agh.master.model.search.room;

import ti.kt.agh.master.model.Response;

public class Monster implements Response {

    private String name;
    private Integer health;
    private Integer treasures;
    private String type;

    public Monster(String name, Integer health, Integer treasures) {
        this.name = name;
        this.health = health;
        this.treasures = treasures;
        this.type = "monster";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public Integer getTreasures() {
        return treasures;
    }

    public void setTreasures(Integer treasures) {
        this.treasures = treasures;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Monster " + getName() + " level " + getHealth() + " treasures " + getTreasures();
    }
}
