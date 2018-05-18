package loginPage;

import ConnectUtil.OnlineConn2;
import com.alibaba.fastjson.JSONObject;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import mainPage.MainPageView;
import registerPage.findPassWord;
import registerPage.registerView;

public class loginView {

    double q;
    double w;

    Button login=new Button("登陆");

    public Scene log(Stage stage){

        OnlineConn2 onlineConn2=new OnlineConn2();
        double width = Screen.getPrimary().getVisualBounds().getWidth();
        double height = Screen.getPrimary().getVisualBounds().getHeight();
        // TODO Auto-generated method stub
        loginWindow addElement=new loginWindow();
        DropShadow shadow = new DropShadow();
        BorderPane pane=new BorderPane();
        stage.initStyle(StageStyle.TRANSPARENT);
        //right
        GridPane gi=new GridPane();
        gi.setVgap(30);
        gi.setHgap(30);
     gi.setLayoutX(5);
        gi.setLayoutY(5);
        VBox go=new VBox();
        go.setId("go");
        final Separator sepHor = new Separator();
        sepHor.setValignment(VPos.CENTER);
        sepHor.setId("sep");
        TextField text=new TextField();
        text.setId("number");
        text.setText("用户名");
        text.setPrefHeight(40);
        PasswordField passwordField=new PasswordField();
        passwordField.setPromptText("密码");
        passwordField.setId("number");
        go.setSpacing(1);
        go.getChildren().addAll(text,sepHor,passwordField);
        HBox ghg=new HBox();
        ghg.getChildren().addAll(go);

        gi.add(ghg,0,4,7,4);
        gi.add(new loginWindow().imgHead(),0,0,4,6);
        //Button login=new Button("登陆");
        Label find=new Label("找回密码");
        find.setTextFill(Color.web("#FAFFEB"));
         Label apply=new Label("申请账号");
         apply.setTextFill(Color.web("#FAFFEB"));
         find.setPrefWidth(62);
         find.setPrefHeight(30);
         apply.setPrefHeight(30);
         apply.setPrefWidth(60);
         apply.setId("logins");
         find.setId("logins");
         login.setId("login");
        login.setPrefWidth(180);
        login.setPrefHeight(30);
        gi.add(find,0,7,2,2);
        gi.add(apply,3,7,2,2);
        gi.add(login,0,8,4,2);
        HBox r=new HBox();
        Label a=new Label();
        a.setPrefWidth(13);
        gi.add(r,4,3,7,5);
        //特效
        Group c1=new loginWindow().circle("red",4,33);
        Group c2=new loginWindow().circle("pink",2,33);
        gi.add(c1,0,1,2,1);
        gi.add(c2,1,0);
        pane.setRight(gi);
        //添加事件
        //(1)用户名输入框
        text.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            text.setText(null);
        });

        //(2)登陆框
        login.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            shadow.setColor(Color.YELLOW);
            shadow.setOffsetX(0);
            shadow.setOffsetY(0);
            login.setEffect(shadow);
        });
        login.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            login.setEffect(null);
        });
        login.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            String userNumber=text.getText().trim();
            String password=passwordField.getText().trim();
            JSONObject json=new JSONObject();
            json.put("userNumber",userNumber);
            json.put("password",password);
            String res=json.toJSONString();
            onlineConn2.connect();
            onlineConn2.fileConn.connect();
            onlineConn2.friendConn.connect();
            onlineConn2.chatConn.connect();
            onlineConn2.paintConn.connect();
            onlineConn2.send("101");
            onlineConn2.send(res);

            Thread aw=new Thread(new Task<Void>() {
                int a=-1;
                @Override
                protected Void call() throws Exception {
                    //处理用户登陆线程
                    String states=onlineConn2.dis.readUTF();
                    a=Integer.parseInt(states);
                    System.out.println(a);
                    return null;
                }

                @Override
                protected void succeeded() {
                    if (a==1){
                        try {
                            onlineConn2.fileConn.dos.writeUTF(userNumber);
                            onlineConn2.friendConn.dos.writeUTF(userNumber);
                            onlineConn2.chatConn.dos.writeUTF(userNumber);
                            onlineConn2.paintConn.dos.writeUTF(userNumber);
                            MainPageView mainPageView = new MainPageView(onlineConn2);
                            stage.close();
                            Stage ma=new Stage();
                            ma.setScene(mainPageView.win(ma));
                            ma.initStyle(StageStyle.TRANSPARENT);
                            ma.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        Alert information = new Alert(Alert.AlertType.INFORMATION,"Welocme to chat");
                        information.setTitle("错误"); //设置标题，不设置默认标题为本地语言的information
                        information.setHeaderText("用户名或密码错误");
                        information.show();
                        System.out.println("密码错误");
                    }
                    super.succeeded();
                }

            });
            try {
                aw.sleep(11);
            } catch (InterruptedException ee) {
                ee.printStackTrace();
            }
            aw.start();
        });




        //(3)找回密码事件
        find.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            shadow.setColor(Color.WHITE);
            find.setEffect(shadow);
        });
        find.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            find.setEffect(null);
        });
        find.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            Stage findStage=new Stage();
            Scene se=new findPassWord().back(findStage);
            findStage.setScene(se);
            findStage.initStyle(StageStyle.TRANSPARENT);
            findStage.show();
        });
        //(4)注册账户事;
        apply.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            shadow.setColor(Color.WHITE);
            apply.setEffect(shadow);
        });
        apply.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            apply.setEffect(null);
        });
        apply.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            registerView regi=new registerView();
          Stage regiStage=new Stage();
          regiStage.initStyle(StageStyle.TRANSPARENT);
          Scene scene=regi.register(regiStage,onlineConn2);
            regiStage.setScene(scene);
            regiStage.show();
            e.fireEvent(stage, new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST ));
        });











        //top
      GridPane top=new GridPane();
      top.setVgap(15);
      top.setHgap(20);
      top.setLayoutX(2);
      top.setLayoutY(2);
      Label tes=new Label("test");//
      top.add(tes,28,2);//
        HBox closeh=new HBox();
        Button small=new Button("",new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/small2.png").toString())));
        Button close=new Button("",new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/close2.png").toString())));
        closeh.setSpacing(0);
        close.setPrefWidth(45);
        small.setPrefWidth(45);
        close.setId("topLea");
        small.setId("topLea");
        closeh.getChildren().addAll(close,small);
        top.add(closeh,20,0,3,3);

        //添加特效
        Group a1,a2,a3,a4,a5,a6,a7,a8,a9,a10;
        a1=addElement.circle("#8C8B25",7,30);
        a2=addElement.circle("yellow",9,28);
        a3=addElement.circle("red",9,28);
        a4=addElement.circle("yellow",9,29);
        a5=addElement.circle("#8C8B25",8,29);
        a6=addElement.circle("green",2,31);
        a7=addElement.circle("red",6,30);
        a8=addElement.circle("red",6,30);
        a9=addElement.circle("yellow",2,33);
        a10=addElement.circle("white",1,30);
        top.add(a1,1,2,2,2);
         top.add(a2,3,1,2,2);
         top.add(a3,5,0,2,2);
         top.add(a4,7,1,2,2);
          top.add(a5,9,2,2,2);//绽放
        top.add(a6,11,0,1,1);
        top.add(a7,12,2,1,1);
        //远端
       top.add(a8,15,2,2,1);
       top.add(a9,18,0,1,1);
       top.add(a10,20,1,1,1);
        pane.setTop(top);
        //添加事件

        //(1)关闭按钮事件
       close.setOnAction((ActionEvent e) -> {

           e.fireEvent(stage, new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST ));
        });
       close.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
           shadow.setColor(Color.RED);
           close.setEffect(shadow);
          // System.out.println("clicked");
       });
        close.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            close.setEffect(null);

        });
        //(2)最小化按钮事件
        small.setOnAction((ActionEvent e)->{
            stage.setIconified(true);
        });
        small.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            shadow.setColor(Color.YELLOW);
            small.setEffect(shadow);
        });
        small.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            small.setEffect(null);
        });



        //center
      GridPane center=new GridPane();
      center.setVgap(20);
      center.setHgap(20);
      center.setLayoutX(2);
      center.setLayoutY(2);
      Label ttt=new Label("ddd");
      center.add(ttt,5,17);
      Button sign=new Button("",new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/bao2.png").toString())));
      sign.setId("sign");
      center.add(sign,3,10,3,3);
      //加特效
        Group b1,b2,b3,b4,b5,b6,b7;
        b1=addElement.circle("red",2,30);
        b2=addElement.circle("blue",2,24);
        b3=addElement.circle("red",2,33);
        b4=addElement.circle("yellow",3,30);
        b5=addElement.circle("red",3,30);
        b6=addElement.circle("blue",2,30);
        center.add(b1,0,0,2,2);
        center.add(b2,1,1,2,2);
        center.add(b3,2,2,1,1);
        center.add(b4,3,2,1,2);
        center.add(b5,4,2,1,2);
        center.add(b6,5,2,1,2);

  pane.setCenter(center);


        //left
        HBox hbb=new HBox();
        Label aa=new Label();
        hbb.setSpacing(20);
        loginElement window=new loginElement(login);
        hbb.getChildren().addAll(aa,window);
        pane.setLeft(hbb);

        Scene scene = new Scene(pane,580,400);//580,400
        scene.getStylesheets().add(getClass().getResource("/Gui_css/window.css").toExternalForm());

        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent m){
                //获取当前窗口的坐标
                double x_stage = stage.getX();
                double y_stage = stage.getY();
                //计算
                stage.setX(x_stage + m.getX() - q);
                stage.setY(y_stage + m.getY() - w);
            }
        });

        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent m) {
//按下鼠标后，记录当前鼠标的坐标
                q =m.getX();
                w =m.getY();
            }
        });
        return scene;
    }

}
