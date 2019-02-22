package Breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

class Controller {

    private static Group root;
    private static Scene scene;

    private static Rectangle paddle;
    private static Circle ball;
    private static double ballSpeedX;
    private static double ballSpeedY;
    private static Group bricks;

    static Timeline gameLoop;

    static void initGame() {
        root = new Group();
        scene = new Scene(root, 500, 600);
        paddle = new Rectangle(scene.getWidth() / 2 - 25, scene.getHeight() - 50, 50, 10);
        paddle.setFill(Color.BROWN);

        ball = new Circle(scene.getWidth() / 2, scene.getHeight() - 400, 5, Color.BLACK);
        ballSpeedX = 0;
        ballSpeedY = 1;

        bricks = new Group();
        root.getChildren().addAll(paddle, bricks, ball);

        Color[] brickColor = {Color.BROWN, Color.DARKGREEN, Color.LIGHTGREEN, Color.LIGHTBLUE};

        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 10; column++) {
                Rectangle thisBrick = new Rectangle(column * 45 + 30, row * 20 + 35, 30, 15);
                thisBrick.setFill(brickColor[row]);
                thisBrick.setArcWidth(10);
                thisBrick.setArcHeight(10);
                bricks.getChildren().add(thisBrick);
            }
        }
        //this works, moves the paddle
        scene.setOnMouseMoved(event -> paddle.setX((event.getX() - (paddle.getWidth() / 2))));

        gameLoop = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(10), e -> Controller.moveBall());
        gameLoop.getKeyFrames().add(keyFrame);
        gameLoop.setCycleCount(Timeline.INDEFINITE);
    }

    private static void moveBall() {

        ball.setLayoutX(ball.getLayoutX() + getBallSpeedX());
        ball.setLayoutY(ball.getLayoutY() + getBallSpeedY());

        Controller.detectCollision();
    }

    private static void detectCollision() {

        if (ball.getBoundsInParent().intersects(paddle.getBoundsInParent())) {
            setBallSpeedX(paddleBallControl());
            setBallSpeedY(getBallSpeedY() * (-1)); //reverses direction up/down when hit paddle
        }

        if (ball.getBoundsInParent().intersects(0, 0, 1, 600) ||
                ball.getBoundsInParent().intersects(500, 0, 1, 600)) {
            setBallSpeedX(getBallSpeedX() * (-1)); //reverses direction left/right when hit boundary
        }

        if (ball.getBoundsInParent().intersects(0, 0, 500, 1)) {  //reverse direction up/down when hit top boundary
            setBallSpeedY(getBallSpeedY() * (-1));
        }

        if (ball.getBoundsInParent().intersects(0, 600, 500, 1)) {
            Text youLose = new Text(100, 300, "Didn't break through\nthe beach!");
            Font font = new Font(25);
            youLose.setFont(font);
            youLose.setFill(Color.RED);
            root.getChildren().add(youLose);
        }

        for (int i = 0; i <= bricks.getChildren().size() - 1; i++) {

            if (ball.getBoundsInParent().intersects(bricks.getChildren().get(i).getBoundsInLocal())) {
                bricks.getChildren().remove(i);
                ballSpeedY *= -1;

                if (bricks.getChildren().size() == 0) {
                    Text youWin = new Text(100, 200, "ARRR!!!\nProceed\nthrough\nthe beach!");
                    Font font = new Font(40);
                    youWin.setFont(font);
                    youWin.setFill(Color.GREEN);
                    root.getChildren().add(youWin);
                    break;
                }
            }
        }
    }

    private static double paddleBallControl() {

        Double touchPoint = ball.getLayoutX() - paddle.getX() + 225;
        //System.out.println("ball: " + (ball.getLayoutX()+225));
        //System.out.println("paddle: " + paddle.getX());
        //System.out.println("touchpoint: "+touchPoint.toString());

        if (touchPoint != 0) { //midpoint
            //System.out.println("return x: "+ (touchPoint/20));
            return touchPoint/20;

        }

        else {  //touch midpoint
            return 0;
        }
    }

    static Scene getScene() {
        return scene;
    }
    private static double getBallSpeedX () {
        return ballSpeedX;
    }
    private static void setBallSpeedX ( double ballSpeedX){
        Controller.ballSpeedX = ballSpeedX;
    }
    private static double getBallSpeedY () {
        return ballSpeedY;
    }
    private static void setBallSpeedY ( double ballSpeedY){
        Controller.ballSpeedY = ballSpeedY;
    }
}