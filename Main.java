package Breakout;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Stage stage = primaryStage;
        stage.setTitle("****Pirate Breakout****");
        Controller.initGame();
        stage.setScene(Controller.getScene());
        stage.show();
        Controller.gameLoop.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
