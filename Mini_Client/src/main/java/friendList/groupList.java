package friendList;

import ConnectUtil.OnlineConn2;
import chatPage.groupChat;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class groupList {
    ObservableList<HBox> grouplist=FXCollections.observableArrayList();//获取Group对象
    private ImageView imageView;
    private Image image;
    private Label le;
    private HBox hBox;
    private OnlineConn2 onlineConn2;
    private Stage sta;
    public groupList(OnlineConn2 onlineConn2,Stage stage){
        sta=stage;
        this.onlineConn2=onlineConn2;
    }

    public ListView Groups(){
        Image a=new Image(getClass().getResource("/GUI_img/LoginPage_img/SourceImg/80.png").toString());
        addGroups("Welcome Chat",a);
        grouplist.add(chiv());
        grouplist.add(chiv2());
        grouplist.add(chiv3());
        ListView<HBox> listView=new ListView<>(grouplist);
        listView.setItems(grouplist);
        listView.setPrefSize(300, 200);
        listView.setCellFactory((ListView<HBox> l)->new cells());
        listView.setEditable(true);
        return  listView;
    }
    private void addGroups(String name,Image image){
        grouplist.add(getG(name,image));
    }
    private HBox getG(String name,Image image){
        hBox=new HBox();
        imageView=new ImageView();
        le=new Label(name);
        le.setStyle("-fx-font-size: 60");
        le.setStyle("-fx-text-fill: white");

        imageView.setFitWidth(120);
        imageView.setFitHeight(110);
        imageView.setImage(image);
        hBox.setSpacing(10);
        hBox.setPrefSize(300,100);
        hBox.getChildren().addAll(imageView,le);
        hBox.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            Shadow shadow=new Shadow();
            shadow.setWidth(2);
            shadow.setHeight(2);
            le.setEffect(shadow);
            Stage set=new Stage();
            set.initStyle(StageStyle.TRANSPARENT);
            groupChat chat=new groupChat(onlineConn2,sta);
            set.setScene(chat.design(set));
            set.show();
                    onlineConn2.sends("03");
                    onlineConn2.sends("g");
                   //加入群聊
        });
        hBox.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            Shadow shadow=new Shadow();
            shadow.setWidth(1);
            shadow.setHeight(1);
            hBox.setEffect(new BoxBlur());
        });
        hBox.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
           le.setEffect(null);
           hBox.setEffect(null);
        });
        return hBox;
    }
    private HBox chiv(){
        HBox hBox=new HBox();
        hBox.getChildren().add(circle("#FFFCF6",40,38));
        return hBox;
    }
    private HBox chiv2(){
        HBox hBox=new HBox();
        Label a=new Label();
        hBox.setSpacing(40);
        hBox.getChildren().addAll(a,circle2("white",50,33));
        return hBox;
    }
    private HBox chiv3(){
        HBox hBox=new HBox();
        Label a=new Label();
        hBox.setSpacing(100);
        hBox.getChildren().addAll(a,circle2("white",80,25));
        return hBox;
    }
    public Group circle(String color, int big, int how){
        Group root=new Group();
        for(int i=0;i<how;i++)//how=30
        {
            Circle circle=new Circle(big, javafx.scene.paint.Color.web(color,0.02));
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStroke(Color.web("white", 0.02));
            circle.setStrokeWidth(0);

            FadeTransition ft = new FadeTransition(Duration.millis(3000), circle);//时间长短
            ft.setFromValue(1.0);
            ft.setToValue(0.1);//透明度深潜
            ft.setCycleCount(Timeline.INDEFINITE);
            ft.setAutoReverse(true);
            ft.play();


            root.getChildren().add(circle);
        }return root;
    }
    public Group circle2(String color,int big,int how){
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
    class cells extends ListCell<HBox>{
        @Override
        protected void updateItem(HBox item, boolean empty) {
            super.updateItem(item, empty);
            if(item!=null){
                setGraphic(item);
            }else{
                setGraphic(null);
            }
        }
    }
}
