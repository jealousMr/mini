package chatPage;

import ConnectUtil.ImageUtil;
import ConnectUtil.OnlineConn2;
import com.alibaba.fastjson.JSONObject;
import friendList.Bean.Friend;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Reflection;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;

public class OneChat {
    double sx, sy;
    double ex = 0, ey = 0;
    double dx, dy;
    private Stage stage;
    private Friend friend;
    private ImageView touI;
    private Button close=new Button("",new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/personPage/closeBtns.png").toString())));
    ObservableList<Group> grouplist=FXCollections.observableArrayList();
    VBox eVbox=new VBox();
    TextField textField;
    ImageView imageView;
    byte[] emojiBytes1 = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x81};
    byte[] emojiBytes2 = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x82};
    byte[] emojiBytes3 = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x83};
    byte[] emojiBytes4 = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x84};
    byte[] emojiBytes5 = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x85};
    byte[] emojiBytes6 = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x86};
    String emoji1 = new String(emojiBytes1, Charset.forName("UTF-8"));
    String emoji2 = new String(emojiBytes2, Charset.forName("UTF-8"));
    String emoji3 = new String(emojiBytes3, Charset.forName("UTF-8"));
    String emoji4 = new String(emojiBytes4, Charset.forName("UTF-8"));
    String emoji5 = new String(emojiBytes5, Charset.forName("UTF-8"));
    String emoji6 = new String(emojiBytes6, Charset.forName("UTF-8"));
    private OnlineConn2 onlineConn2;
    public OneChat(Stage stage,Friend friend,ImageView touI,OnlineConn2 onlineConn2){
        this.onlineConn2=onlineConn2;
        this.touI=touI;
        this.friend=friend;
        this.stage=stage;
        eVbox.setPrefSize(196,210);
        close.setId("no");
        grouplist.add(tip());
        textField=new TextField();
    }
    public Scene chat(){
        Thread ch2=new Thread(new Task<Void>(){
            @Override
            protected Void call() throws Exception {
                while (true){
                    String mes=onlineConn2.chatConn.dis.readUTF();
                    System.out.println(mes);
                    if (mes.equals("img")){
                        byte ph[]=new byte[1111312048];
                        onlineConn2.chatConn.dis.read(ph);
                        ByteArrayInputStream bin = new ByteArrayInputStream(ph);
                        BufferedImage image = ImageIO.read(bin);
                        ImageView imageView2=ImageUtil.getImage(image);
                        grouplist.add(oimg(imageView2));
                    }
                    else if (mes.equals("file")){
                        break;
                    }
                    else {
                        grouplist.add(other(mes));
                    }
                }
                return null;
            }
            @Override
            public void succeeded(){
                FileChooser fileChooser1 = new FileChooser();
                fileChooser1.setTitle("Save file");
                File file = fileChooser1.showSaveDialog(stage);
                System.out.println(file);
                String url=file.toString();
                try {
                    String fileNmae = onlineConn2.chatConn.dis.readUTF();
                    String suffix = fileNmae.substring(fileNmae.lastIndexOf(".") + 1);
                    System.out.println(fileNmae);
                    String fileLength = onlineConn2.chatConn.dis.readUTF();
                    FileOutputStream fo = new FileOutputStream(url+"."+suffix);
                    int len = 0;
                    byte bytes[] = new byte[Integer.parseInt(fileLength)];
                    len = onlineConn2.chatConn.dis.read(bytes);
                    fo.write(bytes, 0, len);
                    System.out.println("保存文件成功");
                    fo.close();
                    grouplist.add(other("ch2接收文件成功"));
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        Thread ch=new Thread(new Task<Void>(){
        int a=0;
            @Override
            protected Void call() throws Exception {
                while (true){
                String mes=onlineConn2.chatConn.dis.readUTF();
                System.out.println(mes+"ch");
                if (mes.equals("img")){
                    byte ph[]=new byte[1111312048];
                    onlineConn2.chatConn.dis.read(ph);
                    ByteArrayInputStream bin = new ByteArrayInputStream(ph);
                    BufferedImage image = ImageIO.read(bin);
                    ImageView imageView2=ImageUtil.getImage(image);
                    grouplist.add(oimg(imageView2));
                }
                else if (mes.equals("file")){
                    a=1;
                    break;
                }else if (mes.equals("close")){
                    break;
                }
                else {
                    grouplist.add(other(mes));
                }
                }
             return null;
            }
            @Override
            public void succeeded(){
                if (a==1){
                    FileChooser fileChooser1 = new FileChooser();
                    fileChooser1.setTitle("Save file");
                    File file = fileChooser1.showSaveDialog(stage);
                    System.out.println(file);
                    String url=file.toString();
                    try {
                        String fileNmae = onlineConn2.chatConn.dis.readUTF();
                        String suffix = fileNmae.substring(fileNmae.lastIndexOf(".") + 1);
                        System.out.println(fileNmae);
                        String fileLength = onlineConn2.chatConn.dis.readUTF();
                        FileOutputStream fo = new FileOutputStream(url+"."+suffix);
                        int len = 0;
                        byte bytes[] = new byte[Integer.parseInt(fileLength)];
                        len = onlineConn2.chatConn.dis.read(bytes);
                        fo.write(bytes, 0, len);
                        System.out.println("保存文件成功");
                        fo.close();
                        grouplist.add(other("ch1接收文件成功"));
                    }catch (IOException e){
                        e.printStackTrace();
                    }finally {
                        ch2.start();
                    }
                }
            }
        });ch.start();

        close.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            close.setEffect(new BoxBlur());
        });
        close.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            close.setEffect(null);
        });
        close.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            System.out.println("关闭聊天窗口");
            try {
                onlineConn2.chatConn.dos.writeUTF("close");
                e.fireEvent(stage, new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST ));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        GridPane pane=new GridPane();
        pane.setPrefSize(800,600);
        pane.setVgap(30);
        pane.setHgap(30);
        pane.add(close,21,0,2,1);
        pane.add(ziC("Chat"),1,0,2,1);
        VBox vBox=new VBox();
        vBox.setPrefSize(520,360);
        ListView<Group> listView=new ListView<>(grouplist);
        listView.setItems(grouplist);
        listView.setPrefSize(500, 380);
        listView.setCellFactory((ListView<Group> l)->new cells());
        listView.setEditable(true);
        listView.setPrefWidth(520);
        listView.setPrefHeight(360);
        vBox.getChildren().add(listView);

        pane.add(vBox,1,1,17,12);
        pane.add(pic(),0,14,3,3);
        pane.add(files(),1,11,3,3);
        pane.add(eVbox,6,6,8,8);

        textField.setPrefSize(380,100);
        pane.add(textField,5,13,14,3);

        pane.add(paint(),3,12,3,3);
        pane.add(send(),17,14,3,3);
        pane.add(imjo(),9,12,2,2);

        pane.add(circle("white",6,30),19,15,1,1);
       pane.add(tou(),17,2,5,5);
        pane.add(user(),17,7,5,2);

        Group root = new Group();
        root.setStyle("-fx-padding: 2,2,2,2");
        root.setLayoutX(800);
        root.setLayoutY(400);
        root.getChildren().add(RectangleBuilder.create()
                .width(800)
                .height(620)
                .fill(Color.rgb(227, 244, 255, 0.5))
                .stroke(Color.web("#FFECE5"))
                .strokeWidth(6.0)
                .arcWidth(16)
                .arcHeight(16)
                .build());

        root.getChildren().add(pane);
        root.setOnMousePressed(e ->{


            sx = e.getSceneX() + sx - ex;
            sy = e.getSceneY() + sy - ey;


        });
        root.setOnMouseDragged(e ->{
            dx = e.getSceneX();
            dy = e.getSceneY();

            Group t = ((Group)e.getSource());
            t.setTranslateX(dx - sx);
            t.setTranslateY(dy - sy);
        });
        root.setOnMouseReleased(e ->{
            ex = e.getSceneX();
            ey = e.getSceneY();

        });
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        Scene scene = new Scene(root, d.width, d.height,Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("/Gui_css/OneChat.css").toExternalForm());
        return scene;
    }
    private Group tip(){
        String a="";
        if (friend.getFonline().equals("1"))
            a="在线";
        else
            a="离线";
        Group c=new Group();
        StackPane root=new StackPane();
        root.getChildren().add(RectangleBuilder.create()
                .width(280)
                .height(40)
                .fill(Color.rgb(93, 93, 93, 0.5))
                .strokeWidth(6.0)
                .arcWidth(16)
                .arcHeight(16)
                .build());
        Text t = new Text();
        t.setX(10.0f);
        t.setY(10.0f);
        t.setCache(true);
        t.setText("Tip:好友在线状态---"+a);
        t.setFill(Color.WHITE);
        t.setFont(Font.font(null, FontWeight.BOLD, 16));
        Reflection r = new Reflection();
        r.setFraction(0.7f);
        t.setEffect(r);
        t.setTranslateY(0);
        root.getChildren().add(t);
        c.getChildren().add(root);
        return c;
    }
    private Text word(String string){
        Text t = new Text();
        t.setX(10.0f);
        t.setY(10.0f);
        t.setCache(true);
        t.setText(string);
        t.setFill(Color.WHITE);
        t.setFont(Font.font(null, FontWeight.BOLD, 16));
        return t;
    }
    private Group other(String str){
        HBox hBox=new HBox();
        Group cc=new Group();
        StackPane root=new StackPane();
        if (str.length()<20){
            root.getChildren().add(RectangleBuilder.create()
                    .width(16*str.length())
                    .height(40)
                    .fill(Color.rgb(93, 93, 93, 0.5))
                    .strokeWidth(6.0)
                    .arcWidth(16)
                    .arcHeight(16)
                    .build());
            root.getChildren().add(word(str));
        }else {
            char temp[]=str.toCharArray();
            String a1="",a2="";
            for (int i=0;i<20;i++){
                a1=a1+temp[i];
            }
            for (int i=20;i<temp.length;i++){
                a2=a2+temp[i];
            }
            root.getChildren().add(RectangleBuilder.create()
                    .width(280)
                    .height(80)
                    .fill(Color.rgb(93, 93, 93, 0.5))
                    .strokeWidth(6.0)
                    .arcWidth(16)
                    .arcHeight(16)
                    .build());
            VBox b=new VBox();
            b.setSpacing(10);
            b.getChildren().addAll(word(a1),word(a2));
            root.getChildren().add(b);
        }
        cc.getChildren().add(root);
        hBox.getChildren().addAll(cc);
        Group p=new Group();
        p.getChildren().add(hBox);
        return p;
    }
    private StackPane imjo(){
        StackPane root=new StackPane();
        root.getChildren().add(CircleBuilder.create()
                .radius(26)
                .fill(Color.rgb(255, 187, 155, 0.8))
                .build()
        );
        Button imjo=new Button("",new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/OneChat/1.png").toString())));
        imjo.setId("no");
        root.getChildren().add(imjo);
        ImageView i1,i2,i3,i4,i5,i6,i7,i8,i9;
        i1=new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/small/1.png").toString()));
        i2=new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/small/2.png").toString()));
        i3=new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/small/3.png").toString()));
        i4=new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/small/4.png").toString()));
        i5=new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/small/5.png").toString()));
        i6=new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/small/6.png").toString()));
        i7=new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/small/7.png").toString()));
        i8=new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/small/8.png").toString()));
        i9=new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/small/9.png").toString()));
        HBox one=new HBox();
        one.setSpacing(12);
        one.getChildren().addAll(i1,i2,i3);
        HBox two=new HBox();
        two.getChildren().addAll(i4,i5,i6);
        two.setSpacing(12);
        HBox three=new HBox();
        three.setSpacing(12);
        three.getChildren().addAll(i7,i8,i9);
        VBox cc=new VBox();
        cc.setId("cc");
        cc.getChildren().addAll(one,two,three);
        cc.setSpacing(5);


        imjo.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            Shadow shadow=new Shadow();
            shadow.setColor(Color.PINK);
            shadow.setWidth(1);
            shadow.setHeight(1);
           root.setEffect(shadow);
        });
        imjo.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            root.setEffect(null);
        });
        imjo.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            if (eVbox.getChildren().isEmpty())
            eVbox.getChildren().add(cc);
            else
                eVbox.getChildren().remove(cc);
        });

        i1.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            String s=textField.getText();
            textField.setText(s+emoji1);
        });
        i2.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            String s=textField.getText();
            textField.setText(s+emoji2);
        });
        i3.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            String s=textField.getText();
            textField.setText(s+emoji3);
        });
        i4.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            String s=textField.getText();
            textField.setText(s+emoji4);
        });
        i5.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            String s=textField.getText();
            textField.setText(s+emoji5);
        });
        i6.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            String s=textField.getText();
            textField.setText(s+emoji6);
        });
        i7.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            String s=textField.getText();
            textField.setText(s+emoji2);
        });
        i8.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            String s=textField.getText();
            textField.setText(s+emoji3);
        });
        i9.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            String s=textField.getText();
            textField.setText(s+emoji2);
        });


        return root;
    }
    private StackPane send(){
        StackPane root=new StackPane();
        root.getChildren().add(CircleBuilder.create()
                .radius(33)
                .fill(Color.rgb(199, 199, 199, 0.8))
                .stroke(Color.web("#C7C7C7"))
                .strokeWidth(3.0)
                .build()
        );
        Button pane=new Button("",new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/OneChat/28.png").toString())));
        pane.setId("no");
        root.getChildren().add(pane);

        pane.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            pane.setEffect(new BoxBlur());
        });
        pane.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            pane.setEffect(null);
        });
        pane.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            if (textField.getText().equals("")||textField.getText()==null){
                textField.setText(null);
            }

            String str=textField.getText();
            if (str!=null&&!str.equals("")){
                grouplist.add(own(str));
                try {
                    onlineConn2.chatConn.dos.writeUTF(str);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        });
        return root;
    }
    private Group own(String str){
        HBox hBox=new HBox();
        javafx.scene.control.Label a=new javafx.scene.control.Label();
        Group cc=new Group();
        StackPane root=new StackPane();
       if (str.length()<20){
           root.getChildren().add(RectangleBuilder.create()
                   .width(16*str.length())
                   .height(40)
                   .fill(Color.rgb(93, 93, 93, 0.5))
                   .strokeWidth(6.0)
                   .arcWidth(16)
                   .arcHeight(16)
                   .build());
           root.getChildren().add(word(str));
       }else {
           char temp[]=str.toCharArray();
           String a1="",a2="";
           for (int i=0;i<20;i++){
               a1=a1+temp[i];
           }
           for (int i=20;i<temp.length;i++){
               a2=a2+temp[i];
           }
           root.getChildren().add(RectangleBuilder.create()
                   .width(20*10)
                   .height(80)
                   .fill(Color.rgb(93, 93, 93, 0.5))
                   .strokeWidth(6.0)
                   .arcWidth(16)
                   .arcHeight(16)
                   .build());
           VBox b=new VBox();
           b.setSpacing(10);
           b.getChildren().addAll(word(a1),word(a2));
           root.getChildren().add(b);
       }
        cc.getChildren().add(root);
       hBox.getChildren().addAll(a,cc);
       hBox.setSpacing(290);
       Group p=new Group();
       p.getChildren().add(hBox);
        return p;
    }
    private StackPane paint(){
        StackPane root=new StackPane();
        root.getChildren().add(CircleBuilder.create()
                .radius(30)
                .fill(Color.rgb(107, 107, 107, 0.8))
                .build()
        );
        Button fbtn=new Button("",new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/OneChat/paint.png").toString())));
        fbtn.setId("no");

        fbtn.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            Shadow shadow=new Shadow();
            shadow.setColor(Color.web("#76F092"));
            shadow.setWidth(2);
            shadow.setHeight(2);
            fbtn.setEffect(shadow);
        });
        fbtn.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            fbtn.setEffect(null);
        });
        fbtn.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            try {
                onlineConn2.paintConn.dos.writeUTF(friend.getfUserNumber());
                new paneSwing(onlineConn2,friend.getfUserNumber()).iniFrame();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        root.getChildren().add(fbtn);
        return root;
    }
    private StackPane files(){
        StackPane root=new StackPane();
        root.getChildren().add(CircleBuilder.create()
                .radius(40)
                .fill(Color.rgb(107, 107, 107, 0.8))
                .build()
        );
        Button fbtn=new Button("",new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/OneChat/files.png").toString())));
       fbtn.setId("no");
       fbtn.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
           Shadow shadow=new Shadow();
           shadow.setColor(Color.web("#76F092"));
           shadow.setWidth(2);
           shadow.setHeight(2);
           fbtn.setEffect(shadow);
       });
       fbtn.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
           fbtn.setEffect(null);
       });
       fbtn.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
           try {
               onlineConn2.chatConn.dos.writeUTF("file");
           } catch (IOException e1) {
               e1.printStackTrace();
           }
           FileChooser fileChooser = new FileChooser();
           File file = fileChooser.showOpenDialog(stage);
           String fileName=file.getName();
           String fileLength=Long.toString(file.length());
           try {
               onlineConn2.chatConn.dos.writeUTF(fileName);
               onlineConn2.chatConn.dos.writeUTF(fileLength);
               onlineConn2.chatConn.sendFile(file, (int) file.length());
               grouplist.add(own("文件发送成功"));
               Thread.sleep(1000);
           } catch (IOException e1) {
               e1.printStackTrace();
           } catch (InterruptedException e1) {
               e1.printStackTrace();
           }
       });
        root.getChildren().add(fbtn);
        return root;
    }
    private Group oimg(ImageView imageView){
        Group group=new Group();
        StackPane root=new StackPane();
        root.getChildren().add(RectangleBuilder
                .create()
                .width(imageView.getFitWidth()+5)
                .height(imageView.getFitHeight()+5)
                .fill(Color.rgb(93, 93, 93, 0.5))
                .strokeWidth(6.0)
                .arcWidth(16)
                .arcHeight(16)
                .build());
        root.getChildren().add(imageView);
        HBox h=new HBox();
        h.getChildren().addAll(root);
        group.getChildren().add(h);
        return group;
    }
    private StackPane pic(){
        StackPane root=new StackPane();
        root.getChildren().add(CircleBuilder.create()
                .radius(40)
                .fill(Color.rgb(107, 107, 107, 0.8))
                .build()
        );
        Button btni=new Button("",new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/OneChat/imgs.png").toString())));
        btni.setId("no");
        btni.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            Shadow shadow=new Shadow();
            shadow.setHeight(2);
            shadow.setWidth(2);
            shadow.setColor(Color.web("#7CE69A"));
            btni.setEffect(shadow);
        });
        btni.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            btni.setEffect(null);

        });
        btni.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showOpenDialog(stage);

            if (file!=null){
                String urls=file.toString();//获得图片url
                File filee = new File(urls);
                String localUrl = null;
                try {
                    localUrl = filee.toURI().toURL().toString();
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
                Image localImage = new Image(localUrl, false);
                imageView=new ImageView(localImage);
                grouplist.add(img(imageView));
                //发送img
                try {
                    onlineConn2.chatConn.dos.writeUTF("img");
                    long le=file.length();
                    String fileLength=Long.toString(le);
                    String fileName=file.getName();
                    JSONObject js=new JSONObject();
                    js.put("imgLength",fileLength);
                    js.put("imgName",fileName);
                    onlineConn2.chatConn.dos.writeUTF(js.toJSONString());
                    onlineConn2.chatConn.sendFile(file,Integer.parseInt(fileLength));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        root.getChildren().add(btni);
        return root;
    }
    private Group img(ImageView imageView){
        Group group=new Group();
        StackPane root=new StackPane();
        root.getChildren().add(RectangleBuilder
                .create()
                .width(imageView.getFitWidth()+5)
                .height(imageView.getFitHeight()+5)
                .fill(Color.rgb(93, 93, 93, 0.5))
                .strokeWidth(6.0)
                .arcWidth(16)
                .arcHeight(16)
                .build());
        root.getChildren().add(imageView);
        HBox h=new HBox();
        h.setSpacing(340);
        javafx.scene.control.Label a=new javafx.scene.control.Label();
        h.getChildren().addAll(a,root);
        group.getChildren().add(h);
        return group;
    }
    private StackPane tou(){
        StackPane root=new StackPane();
        root.getChildren().add(CircleBuilder.create()
                .radius(70)
                .fill(Color.rgb(107, 107, 107, 0.8))
                .build()
        );
        Button btni=new Button("",touI);
        btni.setId("no");
        root.getChildren().add(btni);
        return root;
    }
    private StackPane user(){
        StackPane root=new StackPane();
        root.getChildren().add(RectangleBuilder.create()
                .width(180)
                .height(60)
                .fill(Color.rgb(93, 93, 93, 0.5))

                .build());
        root.getChildren().add(  ziC(friend.getfUserNumber()));
        return root;
    }
    private Text ziC(String str){
        Text t = new Text();
        t.setX(10.0f);
        t.setY(50.0f);
        t.setCache(true);
        t.setText(str);
        t.setFill(Color.web("#D0F8FF"));
        t.setFont(Font.font(null, FontWeight.BOLD, 24));

        Reflection r = new Reflection();
        r.setFraction(0.7f);

        t.setEffect(r);
        return t;
    }
    class cells extends ListCell<Group>{
        @Override
        protected void updateItem(Group item, boolean empty) {
            super.updateItem(item, empty);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if(item!=null){
                        setGraphic(item);
                    }else{
                        setGraphic(null);
                    }
                }
            });
        }
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
