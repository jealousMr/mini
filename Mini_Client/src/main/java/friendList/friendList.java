package friendList;

import ConnectUtil.Base64;
import ConnectUtil.OnlineConn2;
import friendList.Bean.Friend;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class friendList {
    ObservableList<HBox> grouplist=FXCollections.observableArrayList();
    ArrayList<Friend>Friends;
    private OnlineConn2 onlineConn2;

    public friendList(OnlineConn2 onlineConn2, ArrayList<Friend>Friends){
        this.Friends=Friends;
        this.onlineConn2=onlineConn2;
    }

    public ListView<HBox> friend(){
        ListView<HBox> listView=new ListView<>(grouplist);
        for(int i=0;i<Friends.size();i++){
            grouplist.add(one("123","das"));
        }
        listView.setItems(grouplist);
        listView.setPrefSize(300, 564);
        listView.setCellFactory((ListView<HBox> l)->new cells());
        listView.setEditable(true);

        return  listView;
    }
  private HBox one(String userName,String sign){
        ImageView imageView=new ImageView();
      HBox hBox=new HBox();
      hBox.setSpacing(10);
      //头像
      imageView.setFitWidth(120);
      imageView.setFitHeight(110);
      //用户名,个人签名
      VBox pv=new VBox();
      Label userL=new Label(userName);
      Label usign=new Label(sign);
      pv.getChildren().addAll(userL,usign);
      pv.setSpacing(8);
      hBox.getChildren().addAll(imageView,pv);
      hBox.setPrefSize(300,110);
      return hBox;
  }




    class cells extends ListCell<HBox> {
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
