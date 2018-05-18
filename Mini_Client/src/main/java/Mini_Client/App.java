package Mini_Client;


import javafx.application.Application;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import loginPage.loginView;


/**
 * Hello world!
 *
 */
public class App extends Application
{
    public static void main( String[] args )
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new loginView().log(primaryStage));
   primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }


}
