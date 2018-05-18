package registerPage;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;


public class findPassWord {
    double sx, sy;
    double ex = 0, ey = 0;
    double dx, dy;
    private TextField userNumber;
    private TextField phone;
    private Button closeBtn;
    public findPassWord(){
        closeBtn=new Button("",new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/personPage/closeBtns.png").toString())));
        closeBtn.setId("close");
        userNumber=new TextField();
        userNumber.setId("usernumber");
        userNumber.setPrefSize(180,30);
        phone=new TextField();
        phone.setId("phone");
        phone.setPrefSize(180,30);
    }
    public Scene back(Stage stage){
        Group root = new Group();
        root.setLayoutX(900);
        root.setLayoutY(400);
        root.getChildren().add(RectangleBuilder.create()
                .width(400)
                .height(360)
                .fill(Color.rgb(255, 158, 5, 0.5))
                .stroke(Color.web("#F0EFBB"))
                .strokeWidth(4.0)
                .arcWidth(16)
                .arcHeight(16)
                .build());
       root.getChildren().add(songs(stage));
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
        scene.getStylesheets().add(getClass().getResource("/Gui_css/findPassword.css").toExternalForm());
        return scene;
    }
    private Group songs(Stage stage){
        Group group=new Group();
        GridPane pane=new GridPane();
        pane.setHgap(20);
        pane.setVgap(20);
        Group area=new Group();
        area.getChildren().add(CircleBuilder.create()
                 .radius(150)
                .fill(Color.rgb(7, 7, 7, 0.5))
                .build()
        );
         Group sub=new Group();
        sub.getChildren().add(CircleBuilder.create()
                .radius(30)
                .fill(Color.rgb(7, 7, 7, 0.5))
                .build()
        );
        Separator sep=new Separator();
        sep.setId("sep");
        sep.setPrefHeight(2);
        sep.setPrefWidth(200);
        sep.setEffect(new GaussianBlur());
        Separator sep2=new Separator();
        sep2.setId("sep");
        sep2.setPrefHeight(2);
        sep2.setPrefWidth(180);
        sep2.setEffect(new GaussianBlur());
        VBox v1=new VBox();
        v1.getChildren().addAll(userNumber,sep);
        VBox v2=new VBox();
       v2.getChildren().addAll(phone,sep2);

       pane.add(area,2,1,16,16);
       pane.add(sub,14,12,4,4);
       pane.add(ziC(),1,0,3,2);
       pane.add(closeBtn,15,0,2,2);
       pane.add(word3("用户名"),3,5,4,2);
       pane.add(v1,5,5,10,3);
       pane.add(word3("手机号"),3,9,3,2);
       pane.add(v2,5,9,10,3);
       pane.add(go(),14,13,2,2);
        group.getChildren().add(pane);

        sub.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            Shadow shadow=new Shadow();
            shadow.setHeight(3);
            shadow.setWidth(3);
            shadow.setColor(Color.web("#C7C7C7"));
            sub.setEffect(shadow);
        });
        sub.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            sub.setEffect(null);
        });
        closeBtn.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            closeBtn.setEffect(new BoxBlur());
        });
        closeBtn.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            closeBtn.setEffect(null);
        });
        closeBtn.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            e.fireEvent(stage, new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST ));
        });
        return group;
    }
    private Text ziC(){
        Text t = new Text();
        t.setX(10.0f);
        t.setY(50.0f);
        t.setCache(true);
        t.setText("Finds");
        t.setFill(Color.ORANGE);
        t.setFont(Font.font(null, FontWeight.BLACK, 22));
        Reflection r = new Reflection();
        r.setFraction(0.7f);

        t.setEffect(r);
        return t;
    }
    public Text word3(String str) {
        Text t2 = new Text();
        t2.setX(0.5f);
        t2.setY(0.5f);
        t2.setCache(true);
        t2.setText(str);
        t2.setFill(Color.WHITE);
        t2.setFont(Font.font(null, FontWeight.BLACK, 20));
        t2.setEffect(new BoxBlur());
        return t2;
    }
    public Text go() {
        InnerShadow is = new InnerShadow();
        is.setOffsetX(4.0f);
        is.setOffsetY(4.0f);
        Text signUp = new Text();
        signUp.setEffect(is);
        signUp.setX(20);
        signUp.setY(100);
        signUp.setText(" Go");
        signUp.setFill(Color.ORANGE);
        signUp.setFont(Font.font(null, FontWeight.BOLD, 25));
        signUp.setTranslateX(2);
        signUp.setTranslateY(2);
        return signUp;
    }
}
