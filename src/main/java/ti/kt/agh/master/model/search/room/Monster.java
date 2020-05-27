package ti.kt.agh.master.model.search.room;

public class Monster {

    private String name;
    private Integer level;
    private Integer treasures;

    public Monster(String name, Integer level, Integer treasures) {
        this.name = name;
        this.level = level;
        this.treasures = treasures;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getTreasures() {
        return treasures;
    }

    public void setTreasures(Integer treasures) {
        this.treasures = treasures;
    }
}
