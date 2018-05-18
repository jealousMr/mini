package friendList;
import ConnectUtil.OnlineConn2;
import com.alibaba.fastjson.JSONObject;
import filess.FileFrame;
import friendList.Bean.group;
import game.Copy2048;
import game.Menu;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Reflection;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.awt.*;


public class functionList {
    Button closeBtn=new Button("",new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/registerView_img/close.png").toString())));
    private group groupChat=new group();
    VBox vBox1;
    boolean isC=false;
    boolean addClose=false;
    private OnlineConn2 onlineConn2;
    Label not;
    public functionList(OnlineConn2 onlineConn2){
       this.onlineConn2=onlineConn2;

    }
    public VBox fun(){
     VBox vb=new VBox();
     vb.getStylesheets().add(getClass().getResource("/Gui_css/functionList.css").toExternalForm());
     vb.setPrefSize(300,680);

     //加好友
     Group fun1=face2(120,120,120,310,102,0.7);
     HBox addf=new HBox();
     Button abtn=new Button("",new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/OneChat/add.png").toString())));
     addf.getChildren().add(abtn);
     abtn.setStyle("-fx-background-color: transparent");
     VBox lv=new VBox();
        lv.getChildren().add(ziC("添加好友"));
        addf.getChildren().add(lv);
        addf.setSpacing(10);
        addf.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            abtn.setEffect(new BoxBlur());
        });
        addf.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            abtn.setEffect(null);
        });
     fun1.getChildren().add(addf);
     abtn.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{

            Stage addGroupS=new Stage();
            addGroupS.initStyle(StageStyle.TRANSPARENT);
         addGroupS.setScene(addScence(addGroupS));
             addGroupS.show();
             addClose=false;


     });
        //文件传输:
     Group fun2=face2(120,120,120,310,102,0.7);
     HBox h2=new HBox();
     Button abtn2=new Button("",new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/OneChat/file.png").toString())));
    abtn2.setStyle("-fx-background-color: transparent");
     h2.getChildren().add(abtn2);
     VBox leb=new VBox();
     leb.getChildren().add(ziC(" 文件收发"));
     h2.getChildren().add(leb);
     fun2.getChildren().add(h2);
     h2.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
         abtn2.setEffect(new BoxBlur());
     });
     h2.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
         abtn2.setEffect(null);
     });
     fun2.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
         EventQueue.invokeLater(new Runnable() {
             public void run() {
                 try {
                     FileFrame frame = new FileFrame();
                     frame.setVisible(true);
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }
         });
     });

      //你画我猜
        Group fun3=face2(120,120,120,310,102,0.7);
        HBox h3=new HBox();
        Button abtn3=new Button("",new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/OneChat/qqq.png").toString())));
        abtn3.setStyle("-fx-background-color: transparent");
        h3.getChildren().add(abtn3);
        VBox v2=new VBox();
        v2.getChildren().add(ziC(" 你画我猜"));
        h3.getChildren().add(v2);
        fun3.getChildren().add(h3);
        vb.getChildren().addAll(fun1,fun2,fun3);
     vb.setSpacing(15);
       HBox Bh=new HBox();
       Bh.setSpacing(20);
       h3.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
           abtn3.setEffect(new BoxBlur());
       });
       h3.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
           abtn3.setEffect(null);
       });


       //坦克大战
       Group b1=face2(67,67,67,145,210,0.5);
       VBox tan=new VBox();
       Button tanb=new Button("",new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/friendMain/tan.png").toString())));
       tanb.setPrefSize(100,100);
       tanb.setStyle("-fx-background-color: transparent");
       tan.setStyle("-fx-padding: 10,10,10,10");
       tan.setSpacing(20);
       tan.getChildren().addAll(tanb,ziC(" TankWar"));
       b1.getChildren().add(tan);

       tanb.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
           tanb.setEffect(new BoxBlur());
       });
       tanb.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
           tanb.setEffect(null);
       });
       tanb.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
           Menu m =new Menu();
           m.iniFrame();
       });



        Group b2=face2(67,67,67,145,210,0.5);
        VBox tw=new VBox();
        Button bbs=new Button("",new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/ww.png").toString())));
        tw.getChildren().addAll(bbs,ziC("    2048"));
        b2.getChildren().add(tw);
        tw.setStyle("-fx-padding: 10,10,10,10");
        tw.setSpacing(20);
        bbs.setStyle("-fx-background-color: transparent");
        Bh.getChildren().addAll(b1,b2);
        vb.getChildren().add(Bh);
        bbs.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            bbs.setEffect(new BoxBlur());
        });
        bbs.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            bbs.setEffect(null);
        });
        bbs.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            Copy2048 copy2048=new Copy2048();
            copy2048.ini2048();
        });
        return vb;
    }

    private Text ziC(String str){
        Text t = new Text();
        t.setX(10.0f);
        t.setY(50.0f);
        t.setCache(true);
        t.setText(str);
        t.setFill(Color.web("#7E9EFF"));
        t.setFont(Font.font(null, FontWeight.BOLD, 24));

        Reflection r = new Reflection();
        r.setFraction(0.7f);

        t.setEffect(r);
        return t;
    }

    public Scene addScence(Stage stage){

         closeBtn.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            addClose=true;
            e.fireEvent(stage, new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST ));
            onlineConn2.sends("close2");
        });
        Thread rec=new Thread(new Task<Void>(){
            String fus="";
            @Override
            protected Void call() throws Exception {

                //接收信息
                while (true){
                    byte b[]=new byte[1024];
                    int len=onlineConn2.dis.read(b);
                    String s1=new String(b,0,len);
                    String ss=s1.split("\t")[0];
                    System.out.println(ss);
                    if (ss.equals("clo")){break;}
                    JSONObject ob=new JSONObject().parseObject(ss);
                    if(ob.getString("tip").equals("noF")){
                        //不存在
                        Platform.runLater(()->{
                            System.out.println("不存在此用户");
                            not.setText("账户不存在");
                        });isC=false;

                    }else if(ob.getString("tip").equals("yesF")){
                        fus=ob.getString("userNumber");
                        Platform.runLater(()->{
                            not.setText("存在用户： "+fus);
                        });isC=true;
                    }
                } System.out.println("退出好友添加页面");
                return null;
            }

        });rec.start();
        if (addClose){
            try {
                rec.join();
                System.out.println("thred");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        vBox1=new VBox();
        vBox1.setId("backk");
        closeBtn.setId("close");
        Label labels=new Label();
        HBox col=new HBox();
        col.getChildren().addAll(labels,closeBtn);
        col.setSpacing(246);
        Label label=new Label("输入账号：");
        label.setId("ent");
        Button button=new Button("",new ImageView(new Image("/GUI_img/LoginPage_img/friendMain/find.png")));
        TextField te=new TextField();
        te.setPrefSize(230,50);
        HBox h=new HBox();
        h.getChildren().addAll(button,te);
        h.setSpacing(10);
        vBox1.setSpacing(10);
        not=new Label();
        Button add=new Button("添加");
        not.setId("ent");
        vBox1.getChildren().addAll(col,label,h,not,add);
        button.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            //数据处理
            String fre=te.getText();
            if (fre!=null&&fre!=""){
                JSONObject js=new JSONObject();
                js.put("friend",fre);
                onlineConn2.sends("fun1\t");
               onlineConn2.sends(js.toJSONString());
            }
        });
        add.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            JSONObject os=new JSONObject();
            if (isC){
                onlineConn2.sends("close");
                os.put("friend",te.getText());
                onlineConn2.sends(os.toJSONString());//数据库添加好友成功
                addClose=true;
                Alert information = new Alert(Alert.AlertType.INFORMATION,"添加成功");
                information.setTitle("add"); //设置标题，不设置默认标题为本地语言的information
                information.setHeaderText("success");
                information.show();
            }
        });


        Scene scene=new Scene(vBox1,300,200);
        scene.getStylesheets().add(getClass().getResource("/Gui_css/functionList.css").toExternalForm());
        return scene;
    }
    private Group face(int red, int green, int blue, double op, int width){
        Group root = new Group();

        root.getChildren().add(CircleBuilder.create()
                .radius(width)
                .fill(Color.rgb(red, green, blue, op))
                // .stroke(Color.web("#6C9AB4"))
                .strokeWidth(6.0)

                .build());
        return root;
    }
    public Group face2(int red,int green,int blue,int w,int h,double op){
        Group root = new Group();

        root.getChildren().add(RectangleBuilder.create()
                .width(w)
                .height(h)
                .fill(Color.rgb(red, green, blue, op))
                // .stroke(Color.web("#6C9AB4"))
                .strokeWidth(6.0)
                .arcWidth(16)
                .arcHeight(16)
                .build());
        return root;

    }
}
