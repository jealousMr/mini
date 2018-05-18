package mainPage;

import ConnectUtil.Base64;
import ConnectUtil.Conn;
import ConnectUtil.OnlineConn2;
import com.alibaba.fastjson.JSONObject;
import com.sun.deploy.config.Platform;
import com.sun.jmx.snmp.tasks.Task;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ImageInput;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mainPage.Bean.userMessage;

import javax.swing.*;
import java.io.*;
import java.net.MalformedURLException;


public class personPage {
    private userMessage users;
    private Button close;
    private TextField nickname;
    private TextField birthday;
    private ImageView img;
    private TextArea sign;
    private ChoiceBox<String> sex;
    private TextField phone;
    private ChoiceBox<String> problem;
    private Button sub;
    private String newPhoto=null;
    private String t=null;
    private Stage stage;
    String imgFile;
    String fileNa;
    private OnlineConn2 onlineConn2;
    public personPage(userMessage users,Stage stage,OnlineConn2 onlineConn2){
        this.onlineConn2=onlineConn2;
        this.stage=stage;
        this.users=users;
        close=new Button("",new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/personPage/closeBtns.png").toString())));
        close.setId("close");
        nickname=new TextField();
        birthday=new TextField();
        img=new ImageView();
        img.setFitWidth(80);
        img.setFitHeight(80);
        img.setId("img");
        sign=new TextArea();
        sign.setPrefColumnCount(14);
        sign.setPrefRowCount(5);
        sign.setId("sign");
        sex=new ChoiceBox<>();
        sex.setPrefSize(40,24);
        sex.setItems(FXCollections.observableArrayList(
                "男", "女"));
        sex.setId("sex");
        phone=new TextField();
        phone.setPrefSize(200,20);

        problem=new ChoiceBox<>();
        problem.setItems(FXCollections.observableArrayList(
                "good","nice"
        ));
        problem.setPrefSize(200,30);
        problem.setId("pro");
        sub=new Button("",new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/personPage/sub.png").toString())));
        sub.setId("sub");

        //事件
        close.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            Shadow shadow=new Shadow();
            shadow.setWidth(2);
            shadow.setHeight(2);
            close.setEffect(shadow);
        });
        close.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            close.setEffect(null);
        });
        close.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            e.fireEvent(stage, new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST ));
        });
        sub.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            Shadow shadow=new Shadow();
            shadow.setWidth(2);
            shadow.setHeight(2);
            sub.setEffect(shadow);
        });
        sub.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            sub.setEffect(null);
        });
        //提交数据给服务器
        sub.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            onlineConn2.sends("02");
                onlineConn2.sends(getMessage());//发送信息
            //发送头像信息
            if (imgFile!=null){
//                try {
//                    onlineConn2.dos.writeUTF(fileNa);
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(imgFile);
                    //获取输出流

                    byte[] buf = new byte[1024];
                    int len = 0;
                    //2.往输出流里面投放数据
                    while ((len = fis.read(buf)) != -1)
                    {
                        onlineConn2.dos.write(buf,0,len);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }finally {
                    try {
                        fis.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            Alert succeeds = new Alert(Alert.AlertType.INFORMATION,"提交成功");
                succeeds.showAndWait();
        });
    }
    private String getMessage(){
        userMessage user=new userMessage();
        user.setUserNumber(users.getUserNumber());
        user.setSex(sex.getValue());
        user.setBirthday(birthday.getText());
        user.setSign(sign.getText());
        user.setNickname(nickname.getText());
        user.setAnswerOne(problem.getValue());
        user.setPhone(phone.getText());
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("phone",user.getPhone());
        jsonObject.put("userNumber",user.getUserNumber());
        jsonObject.put("sex",user.getSex());
        jsonObject.put("birthday",user.getBirthday());
        jsonObject.put("sign",user.getSign());
        jsonObject.put("answerOne",user.getAnswerOne());
        jsonObject.put("nickname",user.getNickname());
        return jsonObject.toJSONString();
    }


    public Scene show() throws InterruptedException, MalformedURLException {

        nickname.setText(users.getNickname());
        sign.setText(users.getSign());
        birthday.setText(users.getBirthday());
        sex.setValue(users.getSex());
        phone.setText(users.getPhone());
        //设置头像
                if (users.getPhoto()!=null){
                    img=users.getPhoto();
                    img.setFitWidth(80);
                    img.setFitHeight(80);
              }


        GridPane pane=new GridPane();
        pane.setId("backs");
        pane.setVgap(20);
        pane.setHgap(20);
        //头部;
        HBox top=new HBox();
        top.getChildren().addAll(close);
        top.setSpacing(340);
        top.setMinSize(400,30);
        top.setId("top");
        pane.add(top,0,0,20,2);

        //中部输入
        VBox center=new VBox();
        center.setPrefSize(280,80);
        center.setId("center");
        HBox h1=new HBox();
        Label nc=new Label("昵称");
        nc.setId("fonts");
        nickname.setPrefSize(200,30);
        h1.getChildren().addAll(nc,nickname);
        h1.setSpacing(10);
        center.getChildren().add(h1);
        HBox h2=new HBox();
        Label bd=new Label("生日");
        bd.setId("fonts");
        birthday.setPrefSize(200,30);
        h2.setSpacing(10);
        h2.getChildren().addAll(bd,birthday);
        center.getChildren().add(h2);
        center.setSpacing(10);
        //头向设置
        VBox imgV=new VBox();
        imgV.setPrefSize(80,80);
        imgV.setId("img");
        imgV.getChildren().addAll(img);
        pane.add(imgV,1,2,4,4);
        Separator sep=new Separator();
        pane.add(center,6,2,13,4);
        pane.add(sep,6,5,13,2);
        imgV.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e2)->{
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showOpenDialog(stage);
            // System.out.println(file);
            fileNa=file.getName();
            String urls=file.toString();//获得图片url
            imgFile=urls;
            File filee = new File(urls);
            String localUrl = null;
            try {
                localUrl = filee.toURI().toURL().toString();
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }
            Image localImage = new Image(localUrl, false);
            img.setImage(localImage);
        });


        //签名
        HBox signH=new HBox();
        signH.setPrefSize(200,40);
        signH.setId("signH");
        Label sl=new Label("sign");
        sl.setId("fonts");
        signH.getChildren().addAll(sl,sign);
        signH.setSpacing(6);
        pane.add(signH,6,7,13,5);

        //性别
        HBox sexH=new HBox();
        sexH.setPrefSize(100,30);
        sexH.setId("sign");//
        Label se=new Label("性别");
        se.setId("fonts");
        sexH.getChildren().addAll(se,sex);
        sexH.setSpacing(4);
        pane.add(sexH,2,7,5,2);

        Separator sep2=new Separator();
        sep2.setPrefWidth(340);
        pane.add(sep2,2,12,13,1);

        //安全设置
        Label safe=new Label("安全设置");
        safe.setId("fonts");
        pane.add(safe,2,13,4,1);
        //手机绑定
        HBox ph=new HBox();
        ph.setPrefSize(240,30);
        Label bl=new Label("绑定手机");
        ph.getChildren().addAll(bl,phone);
        ph.setSpacing(10);
        pane.add(ph,5,14,14,3);
        //密保设置
        HBox mH=new HBox();
        mH.setPrefSize(240,30);
        Label ml=new Label("feedback");
        mH.getChildren().addAll(ml,problem);
        mH.setSpacing(10);
        pane.add(mH,5,17,14,3);
        pane.add(sub,15,19,3,3);

        Scene scene=new Scene(pane,400,520);
        scene.getStylesheets().add(getClass().getResource("/Gui_css/personal.css").toExternalForm());
        return scene;

    }
}
