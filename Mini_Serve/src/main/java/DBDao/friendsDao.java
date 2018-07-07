package DBDao;

import java.util.ArrayList;

public interface friendsDao {
    //在注册时候增加用户
    public void addUserNumber(String userNumber);
    //添加好友（1-10）位置
    public void addFriends(String user,String friends,int pos);
    //查看此位置是否有好友
    public boolean isKon(String user,int pos);
    public ArrayList<String> getFriends(String userNumber);
    //查询是否已经是朋友
    public boolean isFriend(String userNumber,String fr);
}
