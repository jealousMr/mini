package Bean;

import com.alibaba.fastjson.JSONObject;

public class UserMessage {
    private String nickname;
    private String userNumber;
    private String birthday;
    private String sex;
    private String sign;
    private String photo;
    private String answerOne;
    private String phone;
    private int online;
    public void setOnline(int online) {
        this.online = online;
    }
    public int getOnline() {
        return online;
    }
    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }
    public String getUserNumber() {
        return userNumber;
    }
    public String getAnswerOne() {
        return answerOne;
    }
    public String getBirthday() {
        return birthday;
    }
    public String getNickname() {
        return nickname;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhoto() {
        return photo;
    }
    public String getSex() {
        return sex;
    }
    public String getSign() {
        return sign;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setAnswerOne(String answerOne) {
        this.answerOne = answerOne;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    public String json(){
        JSONObject js=new JSONObject();
        js.put("nickname",nickname);
        js.put("userNumber",userNumber);
        js.put("online",Integer.toString(online));
        return js.toJSONString();
    }
}
