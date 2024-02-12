package orgy.ellipseanim;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    private Ellipse ellipse;
    private double angle = 0;
    private double rotationSpeed = 1;
    private boolean isReverse = false;
    private final Ellipse[] ellipses = new Ellipse[6];
    private final double[] angles = new double[6];
    private final double[] rotationSpeeds = new double[6];
    private final boolean[] isReverses = new boolean[6];

    @Override
    public void start(Stage stage) {
        Group root = new Group();
        ellipse = new Ellipse(100, 100, 50, 25);
        ellipse.setStroke(Color.RED);
        ellipse.setStrokeWidth(2);
        ellipse.setFill(Color.TRANSPARENT);
        ellipse.setTranslateX(-50);
        ellipse.setTranslateY(-25);
        root.getChildren().add(ellipse);

        for (int i = 0; i < 6; i++) {
            ellipses[i] = getEllipse(i);
            root.getChildren().add(ellipses[i]);
        }

        Slider slider = new Slider(0, 10, 1);
        slider.setLayoutX(300);
        slider.setLayoutY(50);
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            rotationSpeed = newValue.doubleValue();
        });
        root.getChildren().add(slider);

        Button reverseButton = new Button(" First Ellipse Reverse Rotation");
        reverseButton.setLayoutX(300);
        reverseButton.setLayoutY(70);
        reverseButton.setOnAction(event -> {
            isReverse = !isReverse;
        });
        root.getChildren().add(reverseButton);

        Slider slider2 = new Slider(0, 10, 1);
        slider2.setLayoutX(450);
        slider2.setLayoutY(50);
        slider2.valueProperty().addListener((observable, oldValue, newValue) -> {
            for (int i = 0; i < 6; i++) {
                rotationSpeeds[i] = newValue.doubleValue();
            }
        });
        root.getChildren().add(slider2);

        Button reverseButton2 = new Button("Other Ellipses Reverse Rotation");
        reverseButton2.setLayoutX(300);
        reverseButton2.setLayoutY(100);
        reverseButton2.setOnAction(event -> {
            for (int i = 0; i < 6; i++) {
                isReverses[i] = !isReverses[i];
            }
        });
        root.getChildren().add(reverseButton2);

        Scene scene = new Scene(root, 600, 200);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (isReverse) {
                    angle -= rotationSpeed * 0.1;
                } else {
                    angle += rotationSpeed * 0.1;
                }
                ellipse.setRotate(angle);

                for (int i = 0; i < 6; i++) {
                    Ellipse newEllipse = ellipses[i];
                    if (isReverses[i]) {
                        angles[i] -= rotationSpeeds[i] * 0.1;
                    } else {
                        angles[i] += rotationSpeeds[i] * 0.1;
                    }
                    newEllipse.setRotate(angles[i]);
                }
            }
        }.start();
    }

    private Ellipse getEllipse(int i) {
        Ellipse newEllipse = new Ellipse(100, 100, 50 - (i + 1) * 5, 25 - (i + 1) * 2);
        newEllipse.setStroke(Color.RED);
        newEllipse.setStrokeWidth(2);
        newEllipse.setFill(Color.TRANSPARENT);
        newEllipse.setStroke(Color.BLACK);

        newEllipse.setTranslateX(ellipse.getTranslateX() + (i + 1) * 40 - (i + 1) * 10);
        newEllipse.setTranslateY(ellipse.getTranslateY());
        return newEllipse;
    }

    public static void main(String[] args) {
        launch();
    }
}
