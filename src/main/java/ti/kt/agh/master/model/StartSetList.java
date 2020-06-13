package ti.kt.agh.master.model;

import java.util.List;

public class StartSetList implements Response{
    private String type;
    private List<StartSet> startSetList;

    public StartSetList(String type, List<StartSet> startSetList) {
        this.type = type;
        this.startSetList = startSetList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<StartSet> getStartSetList() {
        return startSetList;
    }

    public void setStartSetList(List<StartSet> startSetList) {
        this.startSetList = startSetList;
    }
}
