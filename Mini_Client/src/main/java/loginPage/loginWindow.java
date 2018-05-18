package loginPage;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.util.Duration;

import java.awt.*;

public class loginWindow {
    double sx, sy;
    double ex = 0, ey = 0;
    double dx, dy;

    public Group win(){
        Group root = new Group();
        root.setLayoutX(100);
        root.setLayoutY(100);

        root.getChildren().add(RectangleBuilder.create()
                .width(390)
                .height(250)
                .fill(Color.rgb(240, 200, 120, 0.5))
                .stroke(Color.RED)
                .strokeWidth(6.0)
                .arcWidth(16)
                .arcHeight(16)
                .build());
        Text text = TextBuilder.create().x(20).y(100).text("Hello JavaFX!").fill(Color.RED).font(Font.font("Cambria", FontWeight.BOLD, 50)).build();

        root.getChildren().add(text);

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
        //d.width, d.height         Scene scene = new Scene(root, d.width, d.height,Color.TRANSPARENT);
        //显示大小
        return root;
    }

    public Group circle(String color,int big,int how){
        Group root=new Group();
        for(int i=0;i<how;i++)//how=30
        {
            Circle circle=new Circle(big, Color.web(color,0.05));
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStroke(Color.web("white", 0.16));
            circle.setStrokeWidth(4);

            FadeTransition ft = new FadeTransition(Duration.millis(3000), circle);//时间长短
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
    public Group wordD(){
        Group root = new Group();
        Canvas canvas = new Canvas(200, 150);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawShapes(gc);
        canvas.setTranslateX(20);
        canvas.setTranslateY(20);
        root.getChildren().add(canvas);
        return root;
    }
    private void  drawShapes(GraphicsContext gc){
        gc.beginPath();
        gc.moveTo(50, 50);
        gc.bezierCurveTo(100, 15, 100, 100, 45, 100);//150,20,150,150,75,150
        gc.closePath();
        gc.setFill(new RadialGradient(0, 0, 0.5, 0.5, 0.1, true,
                CycleMethod.REFLECT,
                new Stop(0.0,Color.web("yellow")),
                new Stop(1.0, Color.web("blue"))));
        gc.fill();
        LinearGradient lg = new LinearGradient(0, 0, 1, 1, true,
                CycleMethod.REFLECT,
                new Stop(0.0, Color.web("yellow")),
                new Stop(1.0, Color.web("white")));
        gc.setStroke(lg);
        gc.setLineWidth(15);
        gc.stroke();
        gc.applyEffect(new DropShadow(20, 20, 0, Color.web("pink")));
        gc.applyEffect(new DropShadow(20, 0, 20, Color.web("white")));
        gc.applyEffect(new DropShadow(20, -20, 0, Color.web("green")));
        gc.applyEffect(new DropShadow(20, 0, -20, Color.web("white")));
    }
    public Group imgHead(){
        Image srcImage = new Image(getClass().getResource("/GUI_img/LoginPage_img/baobao.png").toString());
        ImageView imageView = new ImageView(srcImage);
        Rectangle clip=new Rectangle(
                srcImage.getWidth(),srcImage.getHeight()
        );
        clip.setArcWidth(15);
        clip.setArcHeight(15);
        imageView.setClip(clip);
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = imageView.snapshot(parameters, null);
        imageView.setClip(null);
        imageView.setEffect(new DropShadow(20, Color.BLACK));
        imageView.setImage(image);

        Group g=new Group();
        g.getChildren().add(imageView);
        return g;
    }
    public Group imgHai(){
        Image srcImage = new Image(getClass().getResource("/GUI_img/LoginPage_img/hai.png").toString());
        ImageView imageView = new ImageView(srcImage);
        Rectangle clip=new Rectangle(
                srcImage.getWidth(),srcImage.getHeight()
        );
        clip.setArcWidth(15);
        clip.setArcHeight(15);
        imageView.setClip(clip);
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = imageView.snapshot(parameters, null);
        imageView.setClip(null);
        imageView.setEffect(new DropShadow(20, Color.BLACK));
        imageView.setImage(image);

        Group g=new Group();
        g.getChildren().add(imageView);
        return g;
    }

}
