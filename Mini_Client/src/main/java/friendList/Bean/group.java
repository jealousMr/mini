package friendList.Bean;

import java.util.List;

public class group {
    private String groupName;
    private String img;
    private List<String> groupPeople;

    public List<String> getGroupPeople() {
        return groupPeople;
    }
    public void setGroupPeople(List<String> groupPeople) {
        this.groupPeople = groupPeople;
    }
    public void setGroupName(String name){
        groupName=name;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
