package ti.kt.agh.master.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Weapon {
    private String name;
    private WeaponType type;
    private Integer power;

    public enum WeaponType {
        @JsonProperty("CONSTANT")
        CONSTANT,
        @JsonProperty("ONE_TIME")
        ONE_TIME
    }

    public Weapon(String name, WeaponType type, Integer power) {
        this.name = name;
        this.type = type;
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WeaponType getType() {
        return type;
    }

    public void setType(WeaponType type) {
        this.type = type;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }
}
