package registerPage;

import ConnectUtil.OnlineConn2;
import ConnectUtil.Send;
import com.alibaba.fastjson.JSONObject;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import loginPage.loginView;

import java.io.IOException;
//import loginPage.loginView;

public class registerView  {

    double q;
    double w;
    public Scene register(Stage stage, OnlineConn2 onlineConn2) {
        BorderPane pane = new BorderPane();
        pane.setId("paneBack");
        GridPane topG = new GridPane();
        topG.setHgap(20);
        topG.setVgap(20);

        Text signUp = word("Sign up", 35, Color.web("5488FF"));
        topG.add(signUp, 3, 2, 6, 2);
        Button close = new Button("", new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/registerView_img/close.png").toString())));
        close.setId("close");
        topG.add(close, 18, 1, 2, 2);
        pane.setTop(topG);
        //关闭事件
        Shadow shadow=new Shadow();
        close.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_ENTERED,(javafx.scene.input.MouseEvent e)->{
            close.setEffect(shadow);
        });
        close.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_EXITED,(javafx.scene.input.MouseEvent e)->{
            close.setEffect(null);
        });
        close.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED,(javafx.scene.input.MouseEvent e)->{
            Stage login=new Stage();
            Scene scene=new loginView().log(login);
            login.setScene(scene);
            login.show();
            e.fireEvent(stage, new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST ));
        });



        GridPane center = new GridPane();

        center.setVgap(20);
        center.setHgap(20);
        Separator sep = new Separator();

        Text userNumber = word2("UserNumber:", 16, Color.web("#5488FF"));
        center.add(userNumber, 5, 2, 5, 1);
        VBox userNumberBox = new VBox();
        TextField userNumberText = new TextField();
        userNumberText.setId("border");
        Separator userNumberSep = new Separator();
        userNumberSep.setId("ussep");
        userNumberSep.setEffect(new GaussianBlur());
        userNumberBox.getChildren().addAll(userNumberText, userNumberSep);
        center.add(userNumberBox, 5, 3, 14, 3);

        Text password = word2("password:", 16, Color.web("#83BDFF"));
        center.add(password, 5, 5, 5, 1);
        VBox passwordBox = new VBox();
        TextField passwordFile = new TextField();
        passwordFile.setId("border");
        Separator passwordSep = new Separator();
        passwordSep.setId("ussep");
        passwordSep.setEffect(new GaussianBlur());
        passwordBox.getChildren().addAll(passwordFile, passwordSep);
        center.add(passwordBox, 5, 6, 14, 3);
        pane.setCenter(center);

        Text nickname = word2("nickname:", 16, Color.web("#FF6176"));
        center.add(nickname, 5, 8, 5, 1);
        VBox nicknameBox = new VBox();
        TextField nicknameFile = new TextField();
        nicknameFile.setId("border");
        Separator nicknameSep = new Separator();
        nicknameSep.setId("ussep");
        nicknameSep.setEffect(new GaussianBlur());
        nicknameBox.getChildren().addAll(nicknameFile, nicknameSep);
        center.add(nicknameBox, 5, 9, 14, 3);

        Text email = word2("email:", 16, Color.PINK);
        center.add(email, 5, 11, 5, 1);
        VBox emailBox = new VBox();
        TextField emailFile = new TextField();
        emailFile.setId("border");
        Separator emailSep = new Separator();
        emailSep.setId("ussep");
        emailSep.setEffect(new GaussianBlur());
        emailBox.getChildren().addAll(emailFile, emailSep);
        center.add(emailBox, 5, 12, 14, 3);

        VBox sureBox = new VBox();
        Button sure = new Button("sure");
        sureBox.getChildren().addAll(sure);
        sure.setPrefHeight(50);
        sure.setPrefWidth(150);
        sure.setId("sure");
        sure.setEffect(new Lighting());
        center.add(sureBox, 8, 16, 6, 4);
        //加特效
        Group a1, a2, a3, a4;
        a1 = circle("#AFB7FF", 10, 30);
        center.add(a1, 1, 3, 3, 2);
        a2=circle("#AA75FF",6,30);
        center.add(a2,2,6,2,2);
        a3=circle("#FFF91E",4,30);
        center.add(a3,3,9,2,2);
        a4=circle("FFCBD6",2,32);
        center.add(a4,4,12,2,2);
        //确认事件



        sure.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_ENTERED,(javafx.scene.input.MouseEvent e)->{
            shadow.setColor(Color.web("D5DAA6"));
            shadow.setWidth(2);
            shadow.setHeight(2);
            sure.setEffect(shadow);
        });
        sure.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_EXITED,(javafx.scene.input.MouseEvent e)->{
            sure.setEffect(null);
        });
        sure.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED,(javafx.scene.input.MouseEvent e)->{
            String userNumbers=null,passwords=null,nicknames=null,emails=null,Waring=null;
            userNumbers=userNumberText.getText().trim();
            passwords=passwordFile.getText().trim();
            nicknames=nicknameFile.getText().trim();
            emails=emailFile.getText().trim();
            if(userNumbers==null||userNumbers.equals("")){
              Waring="用户名不能为空"+"\n";
            }else{
                System.out.println(userNumbers);
            }
            if(passwords==null||passwords.equals("")){
             Waring+="密码不能为空"+"\n";
            }else{
                System.out.println(passwords);
            }
            if(nicknames==null||nicknames.equals("")){
               Waring+="昵称不能为空"+"\n";
            }else{
                System.out.println(nicknames);
            }
            if(emails==null||emails.equals("")){
              Waring+="邮箱不能为空"+"\n";
            }else{
                System.out.println(emails);
            }
            if(Waring!=null){
                Alert warning = new Alert(Alert.AlertType.WARNING,Waring);
                warning.showAndWait();
            }else{
                User US=new User();
                US.setEmail(emails);
                US.setNickName(nicknames);
                US.setPassword(passwords);
                US.setUserNumber(userNumbers);
                JSONObject json=new JSONObject();
                json.put("state","100");
                json.put("userNumber",US.getUserNumber());
                json.put("password",US.getPassword());
                json.put("email",US.getEmail());
                json.put("nickname",US.getNickName());
                String res=json.toJSONString();
                try {
                    onlineConn2.connect();
                   onlineConn2.dos.writeUTF("100");
                   onlineConn2.dos.writeUTF(res);
                   onlineConn2.disconnect();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }finally {
                    Stage login=new Stage();
                    Scene scene=new loginView().log(login);
                    login.setScene(scene);
                    login.show();
                    e.fireEvent(stage, new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST ));
                }



            }



        });

        Scene scene = new Scene(pane, 440, 560);
        scene.getStylesheets().add(getClass().getResource("/Gui_css/register.css").toExternalForm());

        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent m){
                //获取当前窗口的坐标
                double x_stage = stage.getX();
                double y_stage = stage.getY();
                //计算
                stage.setX(x_stage + m.getX() - q);
                stage.setY(y_stage + m.getY() - w);
            }
        });

        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent m) {
//按下鼠标后，记录当前鼠标的坐标
                q =m.getX();
                w =m.getY();
            }
        });
        return scene;
    }

    public Text word(String str, int size, Color a) {
        Text t = new Text();
        t.setX(10.0f);
        t.setY(10.0f);
        t.setCache(true);
        t.setText(str);
        t.setFill(a);
        t.setFont(Font.font(null, FontWeight.BOLD, size));
        Reflection r = new Reflection();
        r.setFraction(0.7f);
        t.setEffect(r);
        t.setTranslateY(0);
        return t;
    }

    public Text word2(String str, int size, Color A) {
        InnerShadow is = new InnerShadow();
        is.setOffsetX(4.0f);
        is.setOffsetY(4.0f);
        Text signUp = new Text();
        signUp.setEffect(is);
        signUp.setX(20);
        signUp.setY(100);
        signUp.setText(str);
        signUp.setFill(A);
        signUp.setFont(Font.font(null, FontWeight.BOLD, size));
        signUp.setTranslateX(2);
        signUp.setTranslateY(2);
        return signUp;
    }

    public Text word3(String str, Color A, int size) {
        Text t2 = new Text();
        t2.setX(10.0f);
        t2.setY(140.0f);
        t2.setCache(true);
        t2.setText(str);
        t2.setFill(A);
        t2.setFont(Font.font(null, FontWeight.BOLD, size));
        t2.setEffect(new GaussianBlur());
        return t2;
    }

    public Group circle(String color,int big,int how){
        Group root=new Group();
        for(int i=0;i<how;i++)//how=30
        {
            Circle circle=new Circle(big, Color.web(color,0.05));
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStroke(Color.web("white", 0.16));
            circle.setStrokeWidth(4);

            FadeTransition ft = new FadeTransition(Duration.millis(2600), circle);//时间长短
            ft.setFromValue(1.0);
            ft.setToValue(0.1);//透明度深潜
            ft.setCycleCount(Timeline.INDEFINITE);
            ft.setAutoReverse(true);
            ft.play();
            root.getChildren().add(circle);

        }
        root.setEffect(new BoxBlur(10,10,3));

        return root;
    }
}
