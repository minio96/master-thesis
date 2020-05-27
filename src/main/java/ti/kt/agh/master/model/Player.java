package ti.kt.agh.master.model;

public class Player {
    private Integer id;
    private String name;
    private Integer level;
    private Integer bonus;

    public Player(Integer id, String name, Integer level, Integer bonus) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.bonus = bonus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

    @Override
    public String toString() {
        return "Player: " + getName() + " level: " + getLevel() + " bonus " + getBonus() + " ID: "+ getId();
    }
}
