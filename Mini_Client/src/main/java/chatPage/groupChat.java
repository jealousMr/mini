package chatPage;
import ConnectUtil.OnlineConn2;
import com.alibaba.fastjson.JSONObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Reflection;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import loginPage.loginWindow;

import java.awt.*;
import java.io.*;

public class groupChat {

    double sx, sy;
    double ex = 0, ey = 0;
    double dx, dy;
    TextField enter=new TextField();
    Button Bsend=new Button("",new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/friendMain/send.png").toString())));
    Button small=new Button("",new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/friendMain/19.png").toString())));
    VBox biao=new VBox();
    Button bfile=new Button("",new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/friendMain/6576 - Folder III.png").toString())));
    Button bpane=new Button("",new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/friendMain/6569 - Compose.png").toString())));
    ObservableList<Group> grouplist=FXCollections.observableArrayList();
    Button close=new Button("",new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/personPage/closeBtns.png").toString())));
    ImageView i1,i2,i3,i4,i5,i6,i7,i8,i9;
    private String imgTip="";
    private Stage stage;
    private OnlineConn2 onlineConn2;

    public groupChat(OnlineConn2 onlineConn2,Stage stage){
        this.stage=stage;
        this.onlineConn2=onlineConn2;
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
        HBox two =new HBox();
        HBox three=new HBox();
        one.setSpacing(2);
        two.setSpacing(2);
        three.setSpacing(2);
        one.getChildren().addAll(i1,i2,i3);
        two.getChildren().addAll(i4,i5,i6);
        three.getChildren().addAll(i7,i8,i9);
       biao.setSpacing(2);
       biao.getChildren().addAll(one,two,three);
       biao.setId("biao");
    }
    private Group gnewsImg(String img,String usern){
        Group root = new Group();

        Platform.runLater(new Runnable() {
            ImageView imageViews=null;
        @Override
        public void run() {

        int t=Integer.parseInt(img);
        switch (t){
            case 1:{
                imageViews=i1;
                break;
            }case 2:{
                imageViews=i2;
                break;
            }case 3:{
                imageViews=i3;
                break;
            }case 4:{
                imageViews=i4;
                break;
            }case 5:{
                imageViews=i5;
                break;
            }case 6:{
                imageViews=i6;
                break;
            }case 7:{
                imageViews=i7;
                break;
            }case 8:{
                imageViews=i8;
                break;
            }case 9:{
                imageViews=i9;
                break;
            }
        }
        Label label=new Label(usern+" say:   ");
        label.setId("fontsE");
        HBox box=new HBox();

                box.getChildren().addAll(label,imageViews);


        root.getChildren().add(RectangleBuilder.create()
                .width(300)
                .height(70)
                .fill(Color.rgb(107, 107, 107, 0.8))
                // .stroke(Color.web("#6C9AB4"))
                .strokeWidth(6.0)
                .arcWidth(16)
                .arcHeight(16)
                .build());
        root.getChildren().add(box);
            }
        });
        return root;

    }
    private Group newsI(ImageView imageView){
        Label label=new Label("you say:   ");
        label.setId("fontsE");
        HBox box=new HBox();
        box.getChildren().addAll(label,imageView);
        Group root = new Group();
        root.getChildren().add(RectangleBuilder.create()
                .width(300)
                .height(70)
                .fill(Color.rgb(107, 107, 107, 0.8))
                // .stroke(Color.web("#6C9AB4"))
                .strokeWidth(6.0)
                .arcWidth(16)
                .arcHeight(16)
                .build());
        root.getChildren().add(box);
        return root;
    }

    private Group newsOther(String some,String userNumber){
        //仅仅处理2行
        char temp[]=some.toCharArray();//23换行
        String s1="",s2="";
        Label l1=new Label();
        Label l2=new Label();
        VBox box=new VBox();
        box.setSpacing(5);
        l1.setId("fontsE");
        l2.setId("fontsE");

        int he=30;
            if (temp.length>20){
            for (int i=0;i<20;i++)
            {
                s1=s1+temp[i];
            }
            for (int u=20;u<temp.length;u++){
                s2=s2+temp[u];
            }
            s1=userNumber+"say  "+s1;
            l1.setText(s1);
            l2.setText(s2);
            box.getChildren().addAll(l1,l2);
            he=he+30;
        }else {
            s1=userNumber+"say:   "+some;
            l1.setText(s1);
            box.getChildren().addAll(l1);
        }
        Group root = new Group();
        root.getChildren().add(RectangleBuilder.create()
                .width(300)
                .height(he)
                .fill(Color.rgb(107, 107, 107, 0.8))
                // .stroke(Color.web("#6C9AB4"))
                .strokeWidth(6.0)
                .arcWidth(16)
                .arcHeight(16)
                .build());
        root.getChildren().add(box);
        return root;
    }

    private Group newsK(String some){
        //仅仅处理2行
        char temp[]=some.toCharArray();//23换行
        String s1="",s2="";
        Label l1=new Label();
        Label l2=new Label();
        VBox box=new VBox();
        box.setSpacing(5);
        l1.setId("fontsE");
        l2.setId("fontsE");
        int he=30;
        if(some.equals("begin your chat")){
            l1.setText(some);
            box.getChildren().add(l1);
        }
        else if (temp.length>20){
        for (int i=0;i<20;i++)
        {
            s1=s1+temp[i];
        }
        for (int u=20;u<temp.length;u++){
            s2=s2+temp[u];
        }
        s1="you say  "+s1;
        l1.setText(s1);
        l2.setText(s2);
        box.getChildren().addAll(l1,l2);
        he=he+30;
        }else {
            s1="you say:   "+some;
            l1.setText(s1);
            box.getChildren().addAll(l1);
        }
        Group root = new Group();
        root.getChildren().add(RectangleBuilder.create()
                .width(300)
                .height(he)
                .fill(Color.rgb(107, 107, 107, 0.8))
                // .stroke(Color.web("#6C9AB4"))
                .strokeWidth(6.0)
                .arcWidth(16)
                .arcHeight(16)
                .build());
        root.getChildren().add(box);
        return root;
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

    public Scene design(Stage stage){
        GridPane pane=new GridPane();
        pane.setVgap(30);
        pane.setHgap(30);
        pane.add(enter(),2,14,17,6);
        pane.add(ziC("chat"),0,0,2,2);
        Separator sep=new Separator();
        sep.setId("sep");
        sep.setPrefWidth(400);
        sep.setEffect(new BoxBlur());
        pane.add(sep,2,13,16,2);

        //消息区
        VBox mVbox=new VBox();
        mVbox.setPrefSize(500,360);
        pane.add(mVbox,2,0,16,14);

        grouplist.add(newsK("begin your chat"));
        ListView<Group> listView=new ListView<>(grouplist);
        listView.setItems(grouplist);
        listView.setPrefSize(500, 380);
        listView.setCellFactory((ListView<Group> l)->new cells());
        listView.setEditable(true);

        mVbox.getChildren().add(listView);



        Group root = new Group();
        root.setLayoutX(800);
        root.setLayoutY(400);
        root.getChildren().add(RectangleBuilder.create()
                .width(780)
                .height(610)
                .fill(Color.rgb(255, 248, 244, 0.5))
                .stroke(Color.web("#D3D3D3"))
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
        //右边效果
        Group a1,a2,a3,a4,a5,a6,a7;
        loginWindow addElement=new loginWindow();
        a1=addElement.circle("orange",7,30);
        a2=addElement.circle("yellow",20,28);
        a3=addElement.circle("#FFE8E7",13,28);
        a4=addElement.circle("white",19,29);
        a5=addElement.circle("white",8,29);
        pane.add(a1,17,0,2,2);
        pane.add(a2,22,3,5,5);
        pane.add(a3,17,12,3,3);
        pane.add(a4,22,15,4,4);
        pane.add(a5,19,10,3,3);





        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        //d.width, d.height
        Scene scene = new Scene(root, d.width, d.height,Color.TRANSPARENT);
        Bsend.setId("Bsend");
        enter.setId("enter");
        bpane.setId("bpane");
        bfile.setId("bfile");
        small.setId("small");
        close.setId("small");
        pane.add(close,22,0,2,2);
        scene.getStylesheets().add(getClass().getResource("/Gui_css/groupChat.css").toExternalForm());
        //事件
        Bsend.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            Shadow shadow=new Shadow();
            Bsend.setEffect(shadow);
            shadow.setWidth(2);
            shadow.setHeight(2);
        });
        Bsend.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            Bsend.setEffect(null);
        });
        Bsend.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{

                String mes=enter.getText();
                    JSONObject js=new JSONObject();
                    js.put("message",mes);
                    js.put("tip","word");//tip标记为文字信息;
                    onlineConn2.sends(js.toJSONString());
                    System.out.println(mes);
                    Platform.runLater(()->{
                        if(mes!=null&&mes!=""){
                        HBox tui =new HBox();
                        Label yuil=new Label();
                        tui.getChildren().addAll(yuil,newsK(mes));
                        tui.setSpacing(176);
                        Group la=new Group();//推到右边
                        la.getChildren().add(tui);
                         grouplist.add(la);
                        }
                   });
            enter.setText(null);
        });
        small.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            Shadow shadow=new Shadow();
            shadow.setWidth(2);
            shadow.setHeight(2);
            small.setEffect(shadow);
        });
        small.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            small.setEffect(null);
        });
        Group ser=new Group();
        //表情处理
        small.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
                    if(ser.getChildren().isEmpty()){
                        ser.getChildren().add(biao);
                    }
        });
        pane.add(ser,3,10,8,8);

        //发送表情包
        JSONObject json=new JSONObject();
        json.put("tip","img");//tip标记为图片信息;
        i1.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            imgTip="1";
            ser.getChildren().remove(biao);
            System.out.println("表情1");
            ImageView view=new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/small/1.png").toString()));
            HBox tui =new HBox();
            Label yuil=new Label();
            tui.getChildren().addAll(yuil,newsI(view));
            tui.setSpacing(176);
            Group la=new Group();
            la.getChildren().add(tui);
            grouplist.add(la);
            json.put("message",imgTip);
            onlineConn2.sends(json.toJSONString());
        });
        i2.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            imgTip="2";
            ser.getChildren().remove(biao);
           ImageView view=new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/small/2.png").toString()));
            HBox tui =new HBox();
            Label yuil=new Label();
            tui.getChildren().addAll(yuil,newsI(view));
            tui.setSpacing(176);
            Group la=new Group();
            la.getChildren().add(tui);
            grouplist.add(la);
            json.put("message",imgTip);
            onlineConn2.sends(json.toJSONString());
        });
        i3.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            imgTip="3";
            ser.getChildren().remove(biao);
           ImageView view=new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/small/3.png").toString()));
            HBox tui =new HBox();
            Label yuil=new Label();
            tui.getChildren().addAll(yuil,newsI(view));
            tui.setSpacing(176);
            Group la=new Group();
            la.getChildren().add(tui);
            grouplist.add(la);
            json.put("message",imgTip);
            onlineConn2.sends(json.toJSONString());
        });
        i4.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            imgTip="4";
            ser.getChildren().remove(biao);
           ImageView view=new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/small/4.png").toString()));
            HBox tui =new HBox();
            Label yuil=new Label();
            tui.getChildren().addAll(yuil,newsI(view));
            tui.setSpacing(176);
            Group la=new Group();
            la.getChildren().add(tui);
            grouplist.add(la);
            json.put("message",imgTip);
            onlineConn2.sends(json.toJSONString());
        });
        i5.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            imgTip="5";
            ser.getChildren().remove(biao);
            ser.getChildren().remove(biao);
            ImageView view=new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/small/5.png").toString()));
            HBox tui =new HBox();
            Label yuil=new Label();
            tui.getChildren().addAll(yuil,newsI(view));
            tui.setSpacing(176);
            Group la=new Group();
            la.getChildren().add(tui);
            grouplist.add(la);
            json.put("message",imgTip);
            onlineConn2.sends(json.toJSONString());
        });
        i6.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            imgTip="6";
            ser.getChildren().remove(biao);
            ImageView view=new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/small/6.png").toString()));
            HBox tui =new HBox();
            Label yuil=new Label();
            tui.getChildren().addAll(yuil,newsI(view));
            tui.setSpacing(176);
            Group la=new Group();
            la.getChildren().add(tui);
            grouplist.add(la);
            json.put("message",imgTip);
            onlineConn2.sends(json.toJSONString());
        });
        i7.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            imgTip="7";
            ser.getChildren().remove(biao);
        ImageView view=new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/small/7.png").toString()));
            HBox tui =new HBox();
            Label yuil=new Label();
            tui.getChildren().addAll(yuil,newsI(view));
            tui.setSpacing(176);
            Group la=new Group();
            la.getChildren().add(tui);
            grouplist.add(la);
            json.put("message",imgTip);
            onlineConn2.sends(json.toJSONString());
        });
        i8.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            imgTip="8";
            ser.getChildren().remove(biao);
            ImageView view=new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/small/8.png").toString()));
            HBox tui =new HBox();
            Label yuil=new Label();
            tui.getChildren().addAll(yuil,newsI(view));
            tui.setSpacing(176);
            Group la=new Group();
            la.getChildren().add(tui);
            grouplist.add(la);
            json.put("message",imgTip);
            onlineConn2.sends(json.toJSONString());
        });
        i9.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            imgTip="9";
            ser.getChildren().remove(biao);
            ImageView view=new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/small/9.png").toString()));
            HBox tui =new HBox();
            Label yuil=new Label();
            tui.getChildren().addAll(yuil,newsI(view));
            tui.setSpacing(176);
            Group la=new Group();
            la.getChildren().add(tui);
            grouplist.add(la);
            json.put("message",imgTip);
            onlineConn2.sends(json.toJSONString());
        });
        root.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            ser.getChildren().remove(biao);
        });

        Thread rec=new Thread(new Task<Void>(){
            @Override
            protected Void call() throws Exception {
                //接收信息
                while (true){
                    byte bytes[]=new byte[1024];
                    int les=onlineConn2.dis.read(bytes);
                    String ss=new String(bytes,0,les);
                    System.out.println(ss);
                    JSONObject ob=new JSONObject().parseObject(ss);
                    if(ob!=null){
                        String ti=ob.getString("tip");
                        String mess=ob.getString("message");
                        String us=ob.getString("userNumber");
                        if(ti.equals("word"))
                        grouplist.add(newsOther(mess,us));
                        else if (ti.equals("img")){
                            grouplist.add(gnewsImg(mess,us));
                        }
                        if (ti.equals("10004")){
                            System.out.println("退出群聊");
                            break;
                        }
                    }
                }
                return null;
            }
        });rec.start();


        //接收文件
        Thread fileThread=new Thread(new Task<Void>(){
            String fileName;
            String fileLength,suffix ;
            @Override
            protected Void call() throws Exception {
                while (true) {
                    fileName = onlineConn2.fileConn.dis.readUTF();
                    System.out.println(fileName);
                    fileLength = onlineConn2.fileConn.dis.readUTF();
                    suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
                    break;
//                    FileOutputStream fo = new FileOutputStream("C:\\my\\ppt\\tu\\" + fileName);
//                    int len = 0;
//                    byte bytes[] = new byte[Integer.parseInt(fileLength)];
//                    len = onlineConn2.fileConn.dis.read(bytes);
//                    fo.write(bytes, 0, len);
//                    System.out.println("接收文件成功");
//                    Group group = newsOther("文件下载到目录", "系统");
//                    grouplist.add(group);
//                    try {
//                      Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }return null;
            }
            @Override
            public void succeeded(){
                FileChooser fileChooser1 = new FileChooser();
                fileChooser1.setTitle("Save file");
                File file = fileChooser1.showSaveDialog(stage);
                System.out.println(file);
                String url=file.toString();
                try {
                    FileOutputStream fo = new FileOutputStream(url+"."+suffix);
                    int len = 0;
                    byte bytes[] = new byte[Integer.parseInt(fileLength)];
                    len = onlineConn2.fileConn.dis.read(bytes);
                    fo.write(bytes, 0, len);
                    System.out.println("接收文件成功");
                    Group group = newsOther("文件接收", "成功");
                     grouplist.add(group);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });fileThread.start();



        close.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            Shadow shadow=new Shadow();
            shadow.setHeight(2);
            shadow.setWidth(2);
            close.setEffect(shadow);
        });
        close.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            close.setEffect(null);
        });
        close.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            e.fireEvent(stage, new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST ));
            onlineConn2.sends("10004");

        });
            //你画我猜
        bpane.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            Shadow shadow=new Shadow();
            shadow.setWidth(2);
            shadow.setHeight(2);
            bpane.setEffect(shadow);
        });
        bpane.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            bpane.setEffect(null);
        });
      //文件传输
        bfile.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            Shadow shadow=new Shadow();
            shadow.setWidth(2);
            shadow.setHeight(2);
            shadow.setColor(Color.ORANGE);
            bfile.setEffect(shadow);
        });
        bfile.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            bfile.setEffect(null);
        });
        bfile.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            Stage gfStage=new Stage();
            gfStage.setScene(new groupFile(onlineConn2,rec).build(gfStage));
            gfStage.initStyle(StageStyle.TRANSPARENT);
            gfStage.show();

        });


        return scene;
    }

    private Group enter(){

        GridPane pane=new GridPane();
        pane.setHgap(20);
        pane.setVgap(20);
        HBox eHbox=new HBox();
        Bsend.setPrefSize(60,60);
        enter.setPrefSize(400,60);
        eHbox.getChildren().addAll(enter,Bsend);
        eHbox.setSpacing(10);

        HBox Ghbox=new HBox();
        small.setPrefSize(60,60);
        bfile.setPrefSize(60,60);
        bpane.setPrefSize(60,60);
        Ghbox.getChildren().addAll(small,bfile,bpane);
        Ghbox.setSpacing(60);

        pane.add(eHbox,1,1,28,4);
        pane.add(Ghbox,4,5,28,4);

        Group root = new Group();

        root.getChildren().add(RectangleBuilder.create()
                .width(500)
                .height(170)
                .fill(Color.rgb(107, 107, 107, 0.8))
                // .stroke(Color.web("#6C9AB4"))
                .strokeWidth(6.0)
                .arcWidth(16)
                .arcHeight(16)
                .build());
        root.getChildren().add(pane);
        return root;
    }
    public Text ziC(String str){
        Text t = new Text();
        t.setX(10.0f);
        t.setY(50.0f);
        t.setCache(true);
        t.setText(str);
        t.setFill(Color.WHITE);
        t.setFont(Font.font(null, FontWeight.BOLD, 24));

        Reflection r = new Reflection();
        r.setFraction(0.7f);

        t.setEffect(r);
        return t;
    }
}
