package ti.kt.agh.master.model;

public class Player implements Response {
    private Integer id;
    private String name;
    private Integer level;
    private Integer bonus;
    private String type;

    public Player(Integer id, String name, Integer level, Integer bonus) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.bonus = bonus;
        this.type = "player";
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    @Override
    public String toString() {
        return "Player: " + getName() + " level: " + getLevel() + " bonus " + getBonus() + " ID: "+ getId();
    }
}
