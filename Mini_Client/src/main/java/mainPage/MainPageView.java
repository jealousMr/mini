package mainPage;
import ConnectUtil.Base64;
import ConnectUtil.Conn;
import ConnectUtil.ImageUtil;
import ConnectUtil.OnlineConn2;
import com.alibaba.fastjson.JSONObject;
import friendList.friendMain;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import mainPage.Bean.userMessage;
import org.omg.CORBA.portable.InputStream;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class MainPageView {

        private Stage stae;
        private OnlineConn2 onlineConn2;
        public MainPageView(OnlineConn2 onlineConn2){
            this.onlineConn2=onlineConn2;
        }
int imgInt=0;
    double sx, sy;
    double ex = 0, ey = 0;
    double dx, dy;
    ImageView view=new ImageView();
    public Scene win(Stage stage){

        Group root = new Group();
        root.setLayoutX(800);
         root.setLayoutY(400);
        root.getChildren().add(RectangleBuilder.create()
                .width(680)
                .height(500)
                .fill(Color.rgb(40, 80, 180, 0.5))
                .stroke(Color.web("#628FB4"))
                .strokeWidth(6.0)
                .arcWidth(16)
                .arcHeight(16)
                .build());
        GridPane pane=new GridPane();
        pane.setHgap(30);
        pane.setVgap(30);
        //top
        Group ico=topImg();
        pane.add(ico,0,0,4,5);//0045
        Text welcome=welcomeText("We",Color.web("#7D98FF"),60);
        pane.add(welcome,3,1,3,3);
        Image imagea=new Image(getClass().getResource("/GUI_img/LoginPage_img/MainPageView_img/tit.png").toString());
        ImageView ig=new ImageView(imagea);
        pane.add(ig,6,1,16,4);

    //个人中心
         Group person=person();
         GridPane personGrid=new GridPane();
         personGrid.setVgap(20);
         personGrid.setHgap(20);
         ImageView touimg=new ImageView(new  Image(getClass().getResource("/GUI_img/LoginPage_img/MainPageView_img/tou.png").toString()));
        personGrid.add(touimg,2,0,6,6);
        VBox perBox=new VBox();
        Text per=personal();
        Label l=new Label();
        perBox.setSpacing(1);
        Separator sep = new Separator();
        sep.setId("sep");
        sep.setEffect(new GaussianBlur());
        perBox.getChildren().addAll(l,per);
       personGrid.add(perBox,2,5,4,2);
       personGrid.add(sep,1,6,6,1);
        person.getChildren().addAll(personGrid);
         pane.add(person,1,3,6,8);//1466
        //person事件
        person.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            DropShadow shdw = new DropShadow();
            shdw.setBlurType(BlurType.GAUSSIAN);
            shdw.setColor(Color.GAINSBORO);
            shdw.setRadius(10);
            shdw.setSpread(0.12);
            shdw.setHeight(10);
            shdw.setWidth(10);
            person.setEffect(shdw);
        });
        person.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            person.setEffect(null);
        });
        person.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            Platform.runLater(()->{
                userMessage us=new userMessage();
                String p1= null;//个人信息
                try {
                   onlineConn2.sends("01");
                    p1 = onlineConn2.dis.readUTF();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                if (p1.equals("0")){

                }else {
                    JSONObject ob=new JSONObject().parseObject(p1);
                    if(ob!=null){
                        System.out.println(ob.toJSONString());
                        us.setNickname(ob.getString("nickname"));
                        us.setBirthday(ob.getString("birthday"));
                        us.setSex(ob.getString("sex"));
                        us.setSign(ob.getString("sign"));
                        us.setUserNumber(ob.getString("userNumber"));
                        us.setPhone(ob.getString("phone"));
                    }
                    //接收头像
                    ImageView imageView;
                    try {
                        byte ph[]=new byte[1111312048];
                        onlineConn2.dis.read(ph);
                        ByteArrayInputStream bin = new ByteArrayInputStream(ph);
                        BufferedImage image = ImageIO.read(bin);
                        imageView=ImageUtil.getImage(image);
                        us.setPhoto(imageView);
                        view=imageView;
                    }catch (IOException e2){
                        e2.printStackTrace();
                    }
                }


                Stage personStage=new Stage();
                    personPage personPage=new personPage(us,personStage,onlineConn2);
                    try {
                        personStage.setScene(personPage.show());
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    } catch (MalformedURLException e1) {
                        e1.printStackTrace();
                    }
                    personStage.initStyle(StageStyle.TRANSPARENT);
                    personStage.show();

            });

        });

        //close
        Group close=close(0.3);
        GridPane closeGrid=new GridPane();
        closeGrid.setHgap(15);
        closeGrid.setVgap(20);
        Button closebtn=new Button("",new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/MainPageView_img/060.png").toString())));
       closebtn.setId("btnc");
        closeGrid.add(closebtn,2,0,8,7);
        Text c=welcomeText("c l o s e",Color.WHITE,40);
        closeGrid.add(c,1,5,11,3);
        close.getChildren().addAll(closeGrid);
        pane.add(close,1,10,6,6);
        //close事件
        close.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(3.0);
            dropShadow.setOffsetX(0);
            dropShadow.setOffsetY(0);
            dropShadow.setColor(Color.color(0.3, 0.5, 0.5));
            c.setEffect(dropShadow);
            DropShadow shdw = new DropShadow();
            shdw.setBlurType(BlurType.GAUSSIAN);
            shdw.setColor(Color.GAINSBORO);
            shdw.setRadius(10);
            shdw.setSpread(0.12);
            shdw.setHeight(10);
            shdw.setWidth(10);
            close.setEffect(shdw);
        });
        close.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{

            c.setX(10.0f);
            c.setY(140.0f);
            c.setCache(true);
            c.setText("c l o s e");
            c.setFill(Color.WHITE);
            c.setFont(Font.font(null, FontWeight.BOLD, 40));
            c.setEffect(new GaussianBlur());
            close.setEffect(null);
        });
        closebtn.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(5.0);
            dropShadow.setOffsetX(0);
            dropShadow.setOffsetY(0);
            dropShadow.setColor(Color.color(0.3, 0.5, 0.5));
            closebtn.setEffect(dropShadow);
        });
        closebtn.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            closebtn.setEffect(null);
        });
        closebtn.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("提示");
            alert.setHeaderText("确定退出？");
            alert.setContentText("。。。");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                System.out.println("退出");
                e.fireEvent(stage, new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST ));
            } else {
                System.out.println("取消");
            }
        });


        //friend
        Group friend=friend();
        GridPane friGrid=new GridPane();
        friGrid.setVgap(30);
        friGrid.setHgap(30);

        //news图片
        Group newsImg=newsImg();
        friGrid.add(newsImg,1,1,12,6);
        GridPane IG=new GridPane();
        IG.setHgap(20);
        IG.setVgap(20);

        IG.setId("newsImg");
        Group zi=zi();
        GridPane ziG=new GridPane();
        ziG.setVgap(15);
        ziG.setHgap(15);
        //处理字母
        Text ziT=ziT("The  Cat is...");
        ziG.add(ziT,2,0,10,4);
        zi.getChildren().add(ziG);
        IG.add(zi,0,6,17,3);
        newsImg.getChildren().add(IG);


        Group markO=markO();
        GridPane markGr=new GridPane();
        markGr.setVgap(20);
        markGr.setHgap(20);
        ImageView nextbtn=new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/MainPageView_img/next.png").toString()));
        markGr.add(nextbtn,0,3,2,3);
        markO.getChildren().add(markGr);
        friGrid.add(markO,12,1,2,6);
        //换图片事件
        markO.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            DropShadow shdw = new DropShadow();
            shdw.setBlurType(BlurType.GAUSSIAN);
            shdw.setColor(Color.GAINSBORO);
            shdw.setRadius(8);
            shdw.setSpread(0.12);
            shdw.setHeight(10);
            shdw.setWidth(10);
            markO.setEffect(shdw);
            DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(3.0);
            dropShadow.setOffsetX(0);
            dropShadow.setOffsetY(0);
            dropShadow.setColor(Color.color(0.2, 0.6, 0.8));
            nextbtn.setEffect(dropShadow);
        });
        markO.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            markO.setEffect(null);
            nextbtn.setEffect(null);
        });
        markO.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            System.out.println("next");
        });
        List<String> tem3=new ArrayList<>();
        tem3.add("The news is jar...");
        tem3.add("The pig is jar...");
        tem3.add("The cat is jar...");
        tem3.add("The java is jar...");
        markO.addEventHandler(MouseEvent.MOUSE_ENTERED,(event) -> {
                Platform.runLater(()->{
                    if (imgInt==tem3.size())
                        imgInt=0;
                    ziT.setText(tem3.get(imgInt));
                    imgInt++;
                    newsImg.setId("ss");
                });
        });


        //盆友圈,news,3
        HBox botto=new HBox();
        Group fr1=fr(175,90,207);
        GridPane fr1G=new GridPane();
        fr1G.setHgap(15);
        fr1G.setVgap(15);
       Button fren=new Button("",new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/MainPageView_img/frien.png").toString())));
       fren.setId("btnc");
       VBox v=new VBox();
       Label a=new Label();
       v.setSpacing(-5);
       v.getChildren().addAll(a,fren);
         fr1G.add(v,1,0,6,6);
        fr1.getChildren().add(fr1G);
        //盆友圈事件
        fr1.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            DropShadow shdw = new DropShadow();
            shdw.setBlurType(BlurType.GAUSSIAN);
            shdw.setColor(Color.GAINSBORO);
            shdw.setRadius(10);
            shdw.setSpread(0.12);
            shdw.setHeight(10);
            shdw.setWidth(10);
            fr1.setEffect(shdw);
            DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(3.0);
            dropShadow.setOffsetX(0);
            dropShadow.setOffsetY(0);
            dropShadow.setColor(Color.color(0.2, 0.6, 0.8));
            fren.setEffect(dropShadow);
        });
        fr1.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            fr1.setEffect(null);
            fren.setEffect(null);
        });
        fren.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            System.out.println("盆友quan");
        });

        Group fr2=fr(218,142,44);
        GridPane newsG=new GridPane();
        newsG.setVgap(15);
        newsG.setHgap(15);
        Button newsBtn=new Button("",new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/MainPageView_img/2c.png").toString())));
        newsBtn.setId("btnc");
        VBox vb=new VBox();
        Label e2=new Label();
        vb.setSpacing(-5);
        vb.getChildren().addAll(e2,newsBtn);
        newsG.add(vb,1,0,6,5);
        fr2.getChildren().add(newsG);
        //news事件
        fr2.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            DropShadow shdw = new DropShadow();
            shdw.setBlurType(BlurType.GAUSSIAN);
            shdw.setColor(Color.GAINSBORO);
            shdw.setRadius(10);
            shdw.setSpread(0.12);
            shdw.setHeight(10);
            shdw.setWidth(10);
            fr2.setEffect(shdw);
            DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(3.0);
            dropShadow.setOffsetX(0);
            dropShadow.setOffsetY(0);
            dropShadow.setColor(Color.color(0.8, 0.2, 0.2));
            newsBtn.setEffect(dropShadow);
        });
        fr2.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            fr2.setEffect(null);
            newsBtn.setEffect(null);
        });
        newsBtn.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            System.out.println("news");
        });



        Group fr3=fr(127,132,206);
        GridPane some=new GridPane();
        some.setHgap(15);
        some.setVgap(15);
        Button someBtn=new Button("",new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/MainPageView_img/c.png").toString())));
        someBtn.setId("btnc");
        some.add(someBtn,1,1,6,5);
        fr3.getChildren().add(some);
        //消息事件
        fr3.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            DropShadow shdw = new DropShadow();
            shdw.setBlurType(BlurType.GAUSSIAN);
            shdw.setColor(Color.GAINSBORO);
            shdw.setRadius(10);
            shdw.setSpread(0.12);
            shdw.setHeight(10);
            shdw.setWidth(10);
            fr3.setEffect(shdw);
            DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(3.0);
            dropShadow.setOffsetX(0);
            dropShadow.setOffsetY(0);
            dropShadow.setColor(Color.color(0.2, 0.4, 0.8));
            someBtn.setEffect(dropShadow);
        });
        fr3.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            fr3.setEffect(null);
            someBtn.setEffect(null);
        });
        //聊天框
        someBtn.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            Platform.runLater(()->{
                onlineConn2.sends("03");
                onlineConn2.sends("p");
                String un=null,nn=null,p=null;
                userMessage userMessage=new userMessage();

                String temp= null;
                try {
                    byte b2[]=new byte[1024];
                    int lens=onlineConn2.dis.read(b2);
                    temp=new String(b2,0,lens);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                        System.out.println(temp);
                    JSONObject js=new JSONObject().parseObject(temp);
                    un=js.getString("userNumber");
                    nn=js.getString("nickname");
                userMessage.setNickname(nn);
                userMessage.setUserNumber(un);//先获得两条个人信息，在的头像流
                userMessage.setPhoto(view);
                Stage sta=new Stage();
                sta.initStyle(StageStyle.TRANSPARENT);
                try {
                    sta.setScene(new friendMain(onlineConn2,sta,userMessage).friendSc());
                    sta.show();
                    e.fireEvent(stage, new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST ));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });

        });


        botto.getChildren().addAll(fr1,fr2,fr3);
        botto.setSpacing(6);
        friGrid.add(botto,1,7,13,4);


        friend.getChildren().addAll(friGrid);
        pane.add(friend,6,4,14,12);


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
        //d.width, d.height
        Scene scene = new Scene(root, d.width, d.height,Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("/Gui_css/mainPage.css").toExternalForm());
        //显示大小
        return scene;
    }


    public Text ziT(String str){
        Text t = new Text();
        t.setX(10.0f);
        t.setY(50.0f);
        t.setCache(true);
        t.setText(str);
        t.setFill(Color.WHITE);
        t.setFont(Font.font(null, FontWeight.BOLD, 20));

        Reflection r = new Reflection();
        r.setFraction(0.7f);

        t.setEffect(r);
        return t;
    }
    public Group zi(){
        Group root = new Group();
        root.getChildren().add(RectangleBuilder.create()
                .width(330)
                .height(58)
                .fill(Color.rgb(5, 5, 5, 0.5))
                // .stroke(Color.web("#6C9AB4"))
                .strokeWidth(6.0)
                .arcWidth(16)
                .arcHeight(16)
                .build());
        return root;
    }
    public Group markO(){
        Group root = new Group();

        root.getChildren().add(RectangleBuilder.create()
                .width(50)
                .height(180)
                .fill(Color.rgb(255, 247, 251, 0.5))
                // .stroke(Color.web("#6C9AB4"))
                .strokeWidth(6.0)
                .arcWidth(16)
                .arcHeight(16)
                .build());
        return root;
    }
    public Text mark(String str,int size,Color a,Color b){
        Text text = TextBuilder.create().text(str).
                font(Font.font("Tahoma", size)).build();
        text.setFill(new LinearGradient(0, 0, 1, 2, true, CycleMethod.REPEAT, new
                Stop[]{new Stop(0, a), new Stop(0.5f, b)}));
        text.setStrokeWidth(1);
        text.setStroke(Color.web("#E5D5FD"));
        return text;
    }
    public Group fr(int red,int green,int blue){
        Group root = new Group();

        root.getChildren().add(RectangleBuilder.create()
                .width(124)
                .height(100)
                .fill(Color.rgb(red, green, blue, 0.7))
                // .stroke(Color.web("#6C9AB4"))
                .strokeWidth(6.0)
                .arcWidth(16)
                .arcHeight(16)
                .build());
        return root;

    }
    public Group newsImg(){
            Group root = new Group();

            root.getChildren().add(RectangleBuilder.create()
                    .width(330)
                    .height(180)
                    .fill(Color.rgb(255, 247, 251, 0.5))
                    // .stroke(Color.web("#6C9AB4"))
                    .strokeWidth(6.0)
                    .arcWidth(16)
                    .arcHeight(16)
                    .build());
            return root;

    }
    public Group close(double op){
        Group root = new Group();

        root.getChildren().add(RectangleBuilder.create()
                .width(180)
                .height(170)
                .fill(Color.rgb(255, 247, 251, op))
               // .stroke(Color.web("#6C9AB4"))
                .strokeWidth(6.0)
                .arcWidth(16)
                .arcHeight(16)
                .build());
        return root;
    }
    public Group person(){
        Group root = new Group();

        root.getChildren().add(RectangleBuilder.create()
                .width(180)
                .height(160)
                .fill(Color.rgb(180, 180, 180, 0.5))
                // .stroke(Color.web("#6C9AB4"))
                .strokeWidth(6.0)
                .arcWidth(16)
                .arcHeight(16)
                .build());
        return root;
    }
    public Group friend(){
        Group root = new Group();

        root.getChildren().add(RectangleBuilder.create()
                .width(440)
                .height(360)
                .fill(Color.rgb(227, 250, 245, 0.5))
                // .stroke(Color.web("#6C9AB4"))
                .strokeWidth(6.0)
                .arcWidth(16)
                .arcHeight(16)
                .build());
        return root;
    }
    public Group topImg(){
        Image srcImage = new Image(getClass().getResource("/GUI_img/LoginPage_img/MainPageView_img/263.png").toString());
        ImageView imageView = new ImageView(srcImage);
        Rectangle clip=new Rectangle(
                srcImage.getWidth(),srcImage.getHeight()
        );
        clip.setArcWidth(15);
        clip.setArcHeight(15);
        imageView.setClip(clip);
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = imageView.snapshot(parameters, null);
        imageView.setClip(null);
        imageView.setEffect(new DropShadow(20, Color.BLACK));
        imageView.setImage(image);

        Group g=new Group();
        g.getChildren().add(imageView);
        return g;
    }
    public Text welcomeText(String str,Color A,int size){
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
    public Text personal(){
        InnerShadow is = new InnerShadow();
        is.setOffsetX(4.0f);
        is.setOffsetY(4.0f);

        Text t = new Text();
        t.setEffect(is);
        t.setX(20);
        t.setY(20);
        t.setText("  个人中心");
        t.setFill(Color.web("#AFB2CF"));
        t.setFont(Font.font(null, FontWeight.BOLD, 20));
        return t;
    }

}
