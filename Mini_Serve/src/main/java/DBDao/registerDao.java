package DBDao;

import Bean.User;

public interface registerDao {
    public void add(User user);
    public void delete(int id);
    public User getUserByUserNumber(String UserNumber);
    public void updateOnlineByUserNumber(String userNumber,boolean isOnline);
    public void updateoGnlineByUserNumber(String userNumber,boolean isOnline);
    public void updataNickname(String userNumber,String nickname);
}
