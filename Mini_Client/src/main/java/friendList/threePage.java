package friendList;

import ConnectUtil.ImageUtil;
import ConnectUtil.OnlineConn2;
import com.alibaba.fastjson.JSONObject;
import friendList.Bean.Friend;
import javafx.concurrent.Task;
import javafx.scene.control.Pagination;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class threePage {
    private Pagination pagination;
    private Stage sta;
   private OnlineConn2 onlineConn2;
    public threePage(OnlineConn2 onlineConn2, Stage stage){
        sta=stage;
        this.onlineConn2=onlineConn2;
    }

    public void send(String str){
        try {
            onlineConn2.dos.writeUTF(str);
            onlineConn2.dos.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public VBox function(int pageIndex){
        VBox v=new VBox();
        v.getChildren().add(new functionList(onlineConn2).fun());
        return v;
    }
    public StackPane group(int pageIndex) {
     StackPane pane=new StackPane();
     pane.getChildren().add(new groupList(onlineConn2,sta).Groups());
     return pane;
    }


    public AnchorPane three(){

        pagination = new Pagination(3, 0);
      //  pagination.setStyle("-fx-border-color:red;");
        pagination.setId("pagin");


        pagination.getStylesheets().add(getClass().getResource("/Gui_css/threePage.css").toExternalForm());
        friendMain.btn1.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED,(javafx.scene.input.MouseEvent e)->{
            try {
                onlineConn2.friendConn.dos.writeUTF("好友列表。。");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            // 获取好友列表信息
            VBox vBoxs=new VBox();
                    Thread ufm=new Thread(new Task<Void>(){
                        List<Friend> list=new ArrayList<>();
                        List<ImageView> img=new ArrayList<>();
            @Override
            protected Void call() throws Exception {
                String len = onlineConn2.friendConn.dis.readUTF();
                System.out.println(len);
                //先获得好友个数
                String ss = onlineConn2.friendConn.dis.readUTF();
                //System.out.println(ss);//获得用户信息
                JSONObject ob = new JSONObject().parseObject(ss);
                if (ob != null) {
                    for (int i = 0; i < Integer.parseInt(len); i++) {
                        String y = Integer.toString(i);
                        String sta = ob.getString(y);
                        JSONObject js = new JSONObject().parseObject(sta);
                        Friend f = new Friend();
                        if (sta != null) {
                            String fu = js.getString("userNumber");
                            String nickN = js.getString("nickname");
                            String online = js.getString("online");
                            f.setfNickname(nickN);
                            f.setfUserNumber(fu);
                            f.setFonline(online);
                            list.add(f);
                        }
                    }
                    for (int i=0;i<Integer.parseInt(len);i++){
                        //获得头像
                        ImageView imageView;
                        try {
                            byte ph[] = new byte[111312048];
                            onlineConn2.friendConn.dis.read(ph);
                            ByteArrayInputStream bin = new ByteArrayInputStream(ph);
                            BufferedImage image = ImageIO.read(bin);
                            imageView = ImageUtil.getImage(image);
                            img.add(imageView);
                            System.out.println("du  tu"+img.size());
                            Thread.sleep(1500);
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }


                }
                return null;
            }
            @Override
                   protected void succeeded(){
                vBoxs.getChildren().addAll(new friendsList(list,img,onlineConn2).show());
            }
        });ufm.start();
                pagination.setPageFactory((Integer pageIndex) -> vBoxs);
        });


        friendMain.btn2.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED,(javafx.scene.input.MouseEvent e)->{
            pagination.setPageFactory((Integer pageIndex) -> group(pageIndex));
        });
        friendMain.btn3.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED,(javafx.scene.input.MouseEvent e)->{
            pagination.setPageFactory((Integer pageIndex) -> function(pageIndex));
        });

        AnchorPane anchor = new AnchorPane();
        AnchorPane.setTopAnchor(pagination, 2.0);
        AnchorPane.setRightAnchor(pagination, 2.0);
        AnchorPane.setBottomAnchor(pagination, 2.0);
        AnchorPane.setLeftAnchor(pagination, 2.0);
        anchor.getChildren().addAll(pagination);
        anchor.setMaxHeight(680);
        return anchor;
    }
}
