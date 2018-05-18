package loginPage;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import mainPage.MainPageView;

import java.awt.*;

public class loginElement extends Region  implements Runnable {
    GridPane gridPane;
    Rectangle rect ;
    Button btn;

    public loginElement(Button btn){
        this.btn=btn;
        setId("window");
        this.setPrefSize(200, 400);//400,600
        rect = new Rectangle(200,300);
        this.setClip(rect);
        gridPane = new GridPane();
//		gridPane.setLayoutX(10);
//		gridPane.setLayoutY(10);
        Rectangle rect1 = new Rectangle(100,100);
        RadialGradient gradient = new RadialGradient(0, 0, 0.5, 0.5, 1, true,
                CycleMethod.NO_CYCLE, new Stop[] {
                new Stop(0, Color.rgb(255, 255, 255, 0.1)),
                new Stop(1, Color.rgb(255, 255, 255, 1)) });
        rect1.setFill(gradient);
        Rectangle rect2 = new Rectangle(100,100);
        rect2.setFill(gradient);
        Rectangle rect3 = new Rectangle(100,100);
        rect3.setFill(gradient);
        Rectangle rect4 = new Rectangle(100,100);
        rect4.setFill(gradient);
        Rectangle rect5 = new Rectangle(100,100);
        rect5.setFill(gradient);
        Rectangle rect6 = new Rectangle(100,100);
        rect6.setFill(gradient);
        gridPane.add(rect1, 0, 0);
        gridPane.add(rect2, 1, 0);
        gridPane.add(rect3, 0, 1);
        gridPane.add(rect4, 1, 1);
        gridPane.add(rect5, 0, 2);
        gridPane.add(rect6, 1, 2);
        getChildren().add(gridPane);
        addEvent();
    }

    public void addEvent(){
        final Timeline up = new Timeline();
        KeyFrame kf1 = new KeyFrame(Duration.seconds(1),new KeyValue(gridPane.layoutYProperty(),-300));
        up.getKeyFrames().add(kf1);

        final Timeline down = new Timeline();
        KeyFrame kf2 = new KeyFrame(Duration.seconds(1),new KeyValue(gridPane.layoutYProperty(),0));
        down.getKeyFrames().add(kf2);
        setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                setCursor(Cursor.HAND);
                up.play();
            }

        });

        setOnMouseExited(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                setCursor(Cursor.DEFAULT);
                down.play();
            }

        });
       btn.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
           Platform.runLater(new Runnable() {
               public void run() {
                   setCursor(Cursor.HAND);
                   up.play();
               }
           });
        });





    }

    @Override
    public void run() {
//        Stage mainStage=new Stage();
//        MainPageView maing=new MainPageView();
//        Scene se= maing.win(mainStage);
//        mainStage.setScene(se);
//        mainStage.initStyle(StageStyle.TRANSPARENT);
//        mainStage.show();

    }
}
