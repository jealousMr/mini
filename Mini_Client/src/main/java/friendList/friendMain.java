package friendList;

import ConnectUtil.Base64;
import ConnectUtil.OnlineConn2;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
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
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mainPage.Bean.userMessage;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class friendMain {
    double x1;
    double y1;
  static  Button btn1=new Button("好友");
  static  Button btn2=new Button("群");
  static  Button btn3=new Button("功能");
  private Button closeAll;
  private ImageView imageView;
  private Image image;
  private userMessage um;
  Text text;
  private OnlineConn2 onlineConn2;
  private Stage sta;
  public friendMain(OnlineConn2 onlineConn2, Stage stage,userMessage um){
      this.um=um;
      this.onlineConn2=onlineConn2;
      sta=stage;
      btn1.setId("bbb");
      btn2.setId("bbb");
      btn3.setId("bbb");
      btn1.setPrefWidth(120);
      btn1.setPrefHeight(60);
      btn2.setPrefHeight(60);
      btn2.setPrefWidth(100);
      btn3.setPrefHeight(60);
      btn3.setPrefWidth(70);
      closeAll=new Button("", new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/personPage/closeBtns.png").toString())));
      closeAll.setId("closeAll");
      imageView=new ImageView();
      imageView.setFitHeight(106);
      imageView.setFitWidth(78);
      text=mark(um.getNickname(),20,Color.WHITE,Color.PINK);
      //设置头像
      imageView=um.getPhoto();
      //设置昵称

      btn3.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
          onlineConn2.sends("03");
          onlineConn2.sends("f");
      });
      btn2.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
          System.out.println("关闭功能界面，开启群聊界面");
          onlineConn2.sends("addGc");
      });
      Shadow shadow=new Shadow();
      shadow.setWidth(2);
      shadow.setHeight(2);
      shadow.setColor(Color.WHITE);
      btn1.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
          btn1.setStyle("-fx-background-color: #dcdcdc");
          btn1.setStyle(" -fx-text-fill: #434343");
          btn1.setEffect(shadow);
      });
      btn1.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
          btn1.setStyle("-fx-background-color:transparent");
          btn1.setEffect(null);
      });
      btn2.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
          btn2.setStyle("-fx-background-color: #dcdcdc");
          btn2.setStyle(" -fx-text-fill: #434343");
          btn2.setEffect(shadow);
      });
      btn2.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
          btn2.setStyle("-fx-background-color:transparent");
          btn2.setEffect(null);
      });
      btn3.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
          btn3.setStyle("-fx-background-color: #dcdcdc");
          btn3.setStyle(" -fx-text-fill: #434343");
          btn3.setEffect(shadow);
      });
      btn3.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
          btn3.setStyle("-fx-background-color:transparent");
          btn3.setEffect(null);
      });
      closeAll.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
          closeAll.setEffect(new BoxBlur());
      });
      closeAll.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
          closeAll.setEffect(null);
      });
      closeAll.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
          e.fireEvent(stage, new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST ));
      });
  }
    public Scene friendSc() throws IOException{
        //头部
        GridPane root=new GridPane();
        root.setHgap(20);
        root.setVgap(20);
        root.setPrefSize(320,300);
        root.add(closeAll,13,0,2,2);
        VBox imgBox=new VBox();
        imgBox.setPrefSize(80,110);
        imgBox.setId("imgBox");
        imgBox.getChildren().add(imageView);
        root.add(imgBox,0,1,6,6);
      //  Text chat=mark(um.getUserNumber(),40,Color.web("#FF6E69"),Color.web("#FFF7B9"));
        Text chat=word(um.getUserNumber(),40,Color.web("#6FB1FF"));
        root.add(chat,6,0,6,4);
        root.add(text,6,3,6,3);


         Group typic=face(239, 251, 255,0.5,320,800);
         GridPane mainPane=new GridPane();//主布局
         mainPane.setHgap(30);
         mainPane.setVgap(30);
         mainPane.add(root,0,0,11,27);
         //列表：
        Group back1=face(255,255,255,0.5,320,64);
        HBox tbtn=new HBox();
        tbtn.setSpacing(6);
        tbtn.getChildren().addAll(btn1,btn2,btn3);
        back1.getChildren().add(tbtn);
        mainPane.add(back1,0,5,11,3);
        mainPane.add(new threePage(onlineConn2,sta).three(),0,7,11,23);
         typic.getChildren().addAll(mainPane);
         Scene scene=new Scene(typic,320,800,Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("/Gui_css/friendMain.css").toExternalForm());
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent m){
            //获取当前窗口的坐标
            double x_stage = sta.getX();
            double y_stage = sta.getY();
        //计算
        sta.setX(x_stage + m.getX() - x1);
        sta.setY(y_stage + m.getY() - y1);
        }
    });

scene.setOnMousePressed(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent m) {
//按下鼠标后，记录当前鼠标的坐标
            x1 =m.getX();
            y1 =m.getY();
        }
    });
        return scene;
    }
    public Group face(int red,int green,int blue,double op,int width,int height){
        Group root = new Group();

        root.getChildren().add(RectangleBuilder.create()
                .width(width)
                .height(height)
                .fill(Color.rgb(red, green, blue, op))
                // .stroke(Color.web("#6C9AB4"))
                .strokeWidth(6.0)
                .arcWidth(16)
                .arcHeight(16)
                .build());
        return root;
    }
    public Text mark(String str, int size, Color a, Color b){
        Text text = TextBuilder.create().text(str).
                font(Font.font("Tahoma", size)).build();
        text.setFill(new LinearGradient(0, 0, 1, 2, true, CycleMethod.REPEAT, new
                Stop[]{new Stop(0, a), new Stop(0.5f, b)}));
        text.setStrokeWidth(1);
        text.setStroke(Color.web("#E5D5FD"));
        return text;
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
}
