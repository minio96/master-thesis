package ti.kt.agh.master.model.search.room;

public class Curse {
    private String name;
    private Integer effect;
    private String type;

    public Curse(String name, Integer effect) {
        this.name = name;
        this.effect = effect;
        this.type = "curse";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEffect() {
        return effect;
    }

    public void setEffect(Integer effect) {
        this.effect = effect;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
