package chatPage;
import ConnectUtil.OnlineConn2;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.shape.StrokeType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class groupFile {
    double sx, sy;
    double ex = 0, ey = 0;
    double dx, dy;
    private Button closeBtn;
    private OnlineConn2 onlineConn2;
    File file=null;
    Thread thread;
    public groupFile(OnlineConn2 onlineConn2,Thread thread){
        this.thread=thread;
        this.onlineConn2=onlineConn2;
        closeBtn=new Button("",new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/personPage/closeBtns.png").toString())));
        closeBtn.setId("close");
        closeBtn.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            Shadow shadow=new Shadow();
            shadow.setWidth(2);
            shadow.setHeight(2);
            closeBtn.setEffect(shadow);
        });
        closeBtn.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            closeBtn.setEffect(null);
        });
    }
    public Scene build(Stage stage){
        Group root = new Group();
        root.setLayoutX(900);
        root.setLayoutY(400);
        root.getChildren().add(RectangleBuilder.create()
                .width(500)
                .height(460)
                .fill(Color.rgb(255, 252, 169, 0.5))
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
        scene.getStylesheets().add(getClass().getResource("/Gui_css/groupFile.css").toExternalForm());
        return scene;
    }
    private GridPane songs(Stage stage){

        GridPane pane=new GridPane();
        pane.setVgap(20);
        pane.setHgap(20);
        pane.add(closeBtn,19,0,3,2);
        Group sendfile=new Group();
        sendfile.getChildren().add(CircleBuilder.create()
                .radius(164)
                .fill(Color.rgb(255, 187, 115, 0.5))
                .build()
        );

        pane.add(sendfile,1,1,16,16);


        Group sendg=new Group();
        sendg.getChildren().add(CircleBuilder.create()
                .radius(60)
                .fill(Color.rgb(240, 239, 187, 0.5))
                .stroke(Color.web("#F0EFBB"))
                .strokeWidth(2.0)
                .build()
        );
        Separator sep=new Separator();
        sep.setPrefHeight(5);
        sep.setPrefWidth(160);
        sep.setId("sep");
        sep.setEffect(new BoxBlur());
        pane.add(sep,4,14,8,1);
        ImageView imgSend=new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/friendMain/send.png").toString()));
        ImageView imgfile=new ImageView(new Image(getClass().getResource("/GUI_img/LoginPage_img/friendMain/dd-22.png").toString()));
        pane.add(imgfile,2,2,13,13);
        pane.add(imgSend,17,15,3,3);
        pane.add(sendg,15,13,8,8);
        pane.add(circle("#F0EFBB",13,30),5,17,2,2);
        pane.add(circle("#F0D1EE",26,28),9,17,3,3);
        pane.add(circle("#FFBB73",10,32),2,15,1,1);

        //添加文件
        imgfile.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            imgfile.setEffect(new BoxBlur());
        });
        imgfile.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            imgfile.setEffect(null);
        });
        imgfile.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            FileChooser fileChooser = new FileChooser();
                file = fileChooser.showOpenDialog(stage);
                 String mes=file.getName()+"\t"+file.length();
            if (file!=null){
//                onlineConn2.sends("file");
//                onlineConn2.sends(mes);
//                onlineConn2.sendFile(file, (int) file.length());
//                System.out.println("文件发送成功");
//                try {
//                    thread.sleep(1000);
//                } catch (InterruptedException e1) {
//                    e1.printStackTrace();
//                }
                try {
                    onlineConn2.fileConn.dos.writeUTF(file.getName());
                    onlineConn2.fileConn.dos.writeUTF(Integer.toString((int)file.length()));
                    onlineConn2.fileConn.sendFile(file);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        sendg.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            Shadow shadow=new Shadow();
            shadow.setWidth(2);
            shadow.setHeight(2);
            imgSend.setEffect(shadow);
        });
        sendg.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            imgSend.setEffect(null);
        });
        sendg.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            Stage recStage=new Stage();
            VBox vBox=new VBox();
            Label label=new Label("文件接收");
            Label fileName=new Label("文件名");
            javafx.scene.control.TextField textField= new javafx.scene.control.TextField("输入文件存储路径");
            textField.setPrefSize(80,40);
            Button btn=new Button("接收");
            vBox.getChildren().addAll(label,fileName,textField,btn);
            vBox.setSpacing(10);
            Scene scene=new Scene(vBox,150,120);
            recStage.setScene(scene);
            recStage.show();
            //接收文件
               String url=textField.getText(),fileNamess;



        });
        //关闭事件
        closeBtn.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            e.fireEvent(stage, new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST ));
        });
        return  pane;
    }
  private Group circle(String color,int big,int how){
        Group root=new Group();
        for(int i=0;i<how;i++)//how=30
        {
            Circle circle=new Circle(big, Color.web(color,0.05));
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStroke(Color.web("white", 0.05));
            circle.setStrokeWidth(0);

            FadeTransition ft = new FadeTransition(Duration.millis(3000), circle);//时间长短
            ft.setFromValue(0.5);
            ft.setToValue(0.1);//透明度深潜
            ft.setCycleCount(Timeline.INDEFINITE);
            ft.setAutoReverse(true);
            ft.play();
            root.getChildren().add(circle);
        }
        return root;
    }
}
