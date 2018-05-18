package mainPage.Bean;

import javafx.scene.image.ImageView;

public class userMessage {
    private String userNumber;
    private String nickname;
    private ImageView photo;
    private String birthday;
    private String sign;
    private String sex;
    private String answerOne;
    private String phone;

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

    public void setPhoto(ImageView photo) {
        this.photo = photo;
    }

    public ImageView getPhoto() {
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

    public void setPhone(String phone) {
        this.phone=phone;
    }


    public void setSign(String sign) {
        this.sign = sign;
    }
}
