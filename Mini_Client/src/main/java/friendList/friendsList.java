package friendList;

import ConnectUtil.OnlineConn2;
import chatPage.OneChat;
import friendList.Bean.Friend;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

public class friendsList {
    ObservableList<Group> grouplist=FXCollections.observableArrayList();
    List<Friend> list;
    List<ImageView> img;
    private OnlineConn2 onlineConn2;
    public friendsList(List<Friend> list,List<ImageView> img,OnlineConn2 onlineConn2) {
        this.onlineConn2=onlineConn2;
        this.list=list;
        this.img=img;
    }
public  ListView show(){
    ListView<Group> listView=new ListView<>(grouplist);
    listView.setItems(grouplist);
    listView.setPrefSize(290, 588);
    listView.setCellFactory((ListView<Group> l)->new cells());
    listView.setEditable(true);
    for (int i=0;i<list.size();i++){
        grouplist.add(one(list.get(i).getfUserNumber(),list.get(i).getfNickname(),img.get(i),list.get(i),img.get(i)));
    }

    return  listView;
}
private Group one(String a1, String b1, javafx.scene.image.ImageView imageView,Friend f ,ImageView im){
    Group group=new Group();
    group.getChildren().add(RectangleBuilder.create()
            .width(290)
            .height(100)
            .fill(Color.rgb(107, 107, 107, 0.8))
            // .stroke(Color.web("#6C9AB4"))
            .strokeWidth(6.0)
            .arcWidth(16)
            .arcHeight(16)
            .build());
    VBox vBox=new VBox();
    vBox.setPrefSize(260,100);
    vBox.setSpacing(20);
    imageView.setFitHeight(90);
    imageView.setFitWidth(80);
    HBox h=new HBox();
    vBox.getChildren().addAll(word(a1,30,Color.web("#80C9FF")),word2(b1,20,Color.web("#06ADFF")));
    h.getChildren().addAll(imageView,vBox);
    h.setSpacing(20);
    group.getChildren().addAll(h);

    group.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_ENTERED,(javafx.scene.input.MouseEvent e)->{
        group.setEffect(new BoxBlur());
    });
    group.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_EXITED,(javafx.scene.input.MouseEvent e)->{
        group.setEffect(null);
    });
    group.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED,(javafx.scene.input.MouseEvent e)->{
        Stage sss=new Stage();
        sss.initStyle(StageStyle.TRANSPARENT);
//        try {
//            onlineConn2.paintConn.dos.writeUTF(f.getfUserNumber());
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
        OneChat oneChat=new OneChat(sss,f,im,onlineConn2);
        sss.setScene(oneChat.chat());
        sss.show();

        try {
            onlineConn2.chatConn.dos.writeUTF(f.getfUserNumber());
        } catch (IOException e1) {
            e1.printStackTrace();
        }


    });

    return group;
}
    private Text word(String str, int size, Color a) {
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
    private Text word2(String str, int size, Color A) {
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

    class cells extends ListCell<Group>{
        @Override
        protected void updateItem(Group item, boolean empty) {
            super.updateItem(item, empty);
            if(item!=null){
                setGraphic(item);
            }else{
                setGraphic(null);
            }
        }
    }

}
