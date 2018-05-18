package friendList.Bean;


import javafx.scene.image.ImageView;

public class Friend {
        private  String fUserNumber;
        private ImageView fPhoto;
        private String fNickname;
        private String fonline;

    public String getFonline() {
        return fonline;
    }

    public void setFonline(String fonline) {
        this.fonline = fonline;
    }

    public String getfNickname() {
        return fNickname;
    }


    public String getfUserNumber() {
        return fUserNumber;
    }

    public void setfNickname(String fNickname) {
        this.fNickname = fNickname;
    }

    public void setfPhoto(ImageView fPhoto) {
        this.fPhoto = fPhoto;
    }

    public ImageView getfPhoto() {
        return fPhoto;
    }

    public void setfUserNumber(String fUserNumber) {
        this.fUserNumber = fUserNumber;
    }
}
