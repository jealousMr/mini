package DBDao;

import Bean.UserMessage;

public interface userMessageDao {
    public void add(UserMessage UserMessage,String ur);
    public UserMessage getUserMessageByUserNumber(String UserNumber);
    public void updataMessage(UserMessage userMessage);
    public void updataPhoto(String  url,String userNumber);
}
