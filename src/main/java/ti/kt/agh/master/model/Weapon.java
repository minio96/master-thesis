package ti.kt.agh.master.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Weapon {
    private String name;
    private WeaponType weaponType;
    private Integer power;
    private String type;

    public enum WeaponType {
        @JsonProperty("CONSTANT")
        CONSTANT,
        @JsonProperty("ONE_TIME")
        ONE_TIME
    }

    public Weapon(String name, WeaponType weaponType, Integer bonus) {
        this.name = name;
        this.weaponType = weaponType;
        this.power = bonus;
        this.type = "weapon";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WeaponType getType() {
        return weaponType;
    }

    public void setType(WeaponType type) {
        this.weaponType = type;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }
}
