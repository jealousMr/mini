package Bean;

public class User {
    private String userNumber;
    private String password;
    private String nickName;
    private String email;
    private  int online;
    private int gonline;

    public int getGonline() {
        return gonline;
    }
    public void setGonline(int gonline) {
        this.gonline = gonline;
    }
    public int getOnline() {
        return online;
    }
    public void setOnline(int online) {
        this.online = online;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUserNumber() {
        return userNumber;
    }
    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }
    public String getPassword() {
        return password;
    }
    public String getNickName() {
        return nickName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
