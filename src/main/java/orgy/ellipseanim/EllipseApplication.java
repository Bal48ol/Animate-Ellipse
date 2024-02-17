package orgy.ellipseanim;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicReference;

public class EllipseApplication extends Application {
    private double angle1 = 0;
    private double angle2 = 0;
    private double angle3 = 0;
    private double rotationSpeed1 = 3;
    private double rotationSpeed2 = 0;
    private double rotationSpeed3 = 0;
    private final boolean isReverse = false;

    @Override
    public void start(Stage stage) {
        Group root = new Group();

        Ellipse ellipse1 = new Ellipse(200, 100, 50, 25);
        ellipse1.setStroke(Color.RED);
        ellipse1.setStrokeWidth(2);
        ellipse1.setFill(Color.TRANSPARENT);
        ellipse1.setTranslateX(-50);
        ellipse1.setTranslateY(-25);
        root.getChildren().add(ellipse1);

        Bounds bounds1 = ellipse1.getBoundsInParent();
        double rightX1 = bounds1.getMaxX();
        double centerY1 = bounds1.getMinY() + bounds1.getHeight() / 2;
        AtomicReference<Double> radius1 = new AtomicReference<>(bounds1.getWidth() / 2);
        double centerX1 = bounds1.getMinX() + radius1.get();
        Circle marker1 = new Circle(rightX1, centerY1, 5, Color.RED);
        root.getChildren().add(marker1);

        Ellipse ellipse2 = getEllipse(root, ellipse1, marker1);

        Bounds bounds2 = ellipse2.getBoundsInParent();
        AtomicReference<Double> centerY2 = new AtomicReference<>(bounds2.getMinY() + bounds2.getHeight() / 2);
        AtomicReference<Double> radius2 = new AtomicReference<>(bounds2.getWidth() / 2);
        AtomicReference<Double> centerX2 = new AtomicReference<>(bounds2.getMinX() + radius2.get());

        Circle marker2 = new Circle();
        marker2.setRadius(5);
        marker2.setFill(Color.BLACK);
        root.getChildren().add(marker2);

        double centerX2Final = centerX2.get();
        double centerY2Final = centerY2.get();

        marker2.setCenterX(centerX2Final);
        marker2.setCenterY(centerY2Final);

        ellipse2.centerXProperty().addListener((observable, oldValue, newValue) -> {
            centerX2.set(newValue.doubleValue() + radius2.get());
            marker2.setCenterX(centerX2.get());
        });

        ellipse2.centerYProperty().addListener((observable, oldValue, newValue) -> {
            centerY2.set(newValue.doubleValue());
            marker2.setCenterY(centerY2.get());
        });

        Ellipse ellipse3 = getEllipse(root, ellipse2, marker2);

        Slider sliderMain1 = new Slider(-10, 10, 3);
        sliderMain1.setLayoutX(10);
        sliderMain1.setLayoutY(130);
        sliderMain1.setShowTickLabels(true);
        sliderMain1.setShowTickMarks(true);
        sliderMain1.setMajorTickUnit(5);
        sliderMain1.valueProperty().addListener((observable, oldValue, newValue) -> rotationSpeed1 = newValue.doubleValue());
        root.getChildren().add(sliderMain1);

        Slider sliderMain2 = new Slider(-10, 10, 0);
        sliderMain2.setLayoutX(200);
        sliderMain2.setLayoutY(130);
        sliderMain2.setShowTickLabels(true);
        sliderMain2.setShowTickMarks(true);
        sliderMain2.setMajorTickUnit(5);
        sliderMain2.valueProperty().addListener((observable, oldValue, newValue) -> rotationSpeed2 = newValue.doubleValue());
        root.getChildren().add(sliderMain2);

        Slider sliderMain3 = new Slider(-10, 10, 0);
        sliderMain3.setLayoutX(390);
        sliderMain3.setLayoutY(130);
        sliderMain3.setShowTickLabels(true);
        sliderMain3.setShowTickMarks(true);
        sliderMain3.setMajorTickUnit(5);
        sliderMain3.valueProperty().addListener((observable, oldValue, newValue) -> rotationSpeed3 = newValue.doubleValue());
        root.getChildren().add(sliderMain3);

        Slider sliderRunStroke2 = new Slider(-2*Math.PI, 2*Math.PI, 0);
        sliderRunStroke2.setLayoutX(580);
        sliderRunStroke2.setLayoutY(130);
        root.getChildren().add(sliderRunStroke2);

        Slider sliderRunStroke3 = new Slider(-2*Math.PI, 2*Math.PI, 0);
        sliderRunStroke3.setLayoutX(770);
        sliderRunStroke3.setLayoutY(130);
        root.getChildren().add(sliderRunStroke3);

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (isReverse) {
                    angle1 -= rotationSpeed1 * 0.1;
                } else {
                    angle1 += rotationSpeed1 * 0.1;
                }
                ellipse1.setRotate(angle1);

                if (isReverse) {
                    angle2 -= rotationSpeed2 * 0.1;
                } else {
                    angle2 += rotationSpeed2 * 0.1;
                }
                ellipse2.setRotate(angle2);

                if (isReverse) {
                    angle3 -= rotationSpeed3 * 0.1;
                } else {
                    angle3 += rotationSpeed3 * 0.1;
                }
                ellipse3.setRotate(angle3);

                // первый эллипс и первый маркер
                double angleRotate = Math.toRadians(ellipse1.getRotate());

                double speedRotate = sliderRunStroke2.getValue() / 15;
                double angleMove = (now / 1e8 * speedRotate) % (2 * Math.PI);

                double markerX = centerX1 + radius1.get() * Math.cos(angleRotate) * Math.cos(angleMove) - radius1.get() / 2 * Math.sin(angleRotate) * Math.sin(angleMove);
                double markerY = centerY1 + radius1.get() * Math.sin(angleRotate) * Math.cos(angleMove) + radius1.get() / 2 * Math.cos(angleRotate) * Math.sin(angleMove);

                marker1.setCenterX(markerX);
                marker1.setCenterY(markerY);

                // второй эллипс и второй маркер
                double angleRotate2 = Math.toRadians(ellipse2.getRotate());

                double speedRotate2 = sliderRunStroke3.getValue() / 15;
                double angleMove2 = (now / 1e8 * speedRotate2) % (2 * Math.PI);

                double markerX2 = centerX2.get() + radius2.get() * Math.cos(angleRotate2) * Math.cos(angleMove2) - radius2.get() / 2 * Math.sin(angleRotate2) * Math.sin(angleMove2) - 35;
                double markerY2 = centerY2.get() + radius2.get() * Math.sin(angleRotate2) * Math.cos(angleMove2) + radius2.get() / 2 * Math.cos(angleRotate2) * Math.sin(angleMove2);

                marker2.setCenterX(markerX2);
                marker2.setCenterY(markerY2);
            }
        };

        sliderRunStroke2.valueProperty().addListener((observable, oldValue, newValue) -> {
            animationTimer.stop();
            animationTimer.start();
        });

        sliderRunStroke3.valueProperty().addListener((observable, oldValue, newValue) -> {
            animationTimer.stop();
            animationTimer.start();
        });

        animationTimer.start();

        Scene scene = new Scene(root, 1100, 220);
        stage.setTitle("Ellipse Anim!");
        stage.setScene(scene);
        stage.show();
    }

    private Ellipse getEllipse(Group root, Ellipse ellipse1, Circle marker1) {
        Ellipse ellipse2 = new Ellipse();
        ellipse2.centerXProperty().bind(marker1.centerXProperty());
        ellipse2.centerYProperty().bind(marker1.centerYProperty());
        ellipse2.radiusXProperty().bind(ellipse1.radiusXProperty().divide(1.5));
        ellipse2.radiusYProperty().bind(ellipse1.radiusYProperty().divide(1.5));
        ellipse2.setStroke(Color.BLACK);
        ellipse2.setStrokeWidth(2);
        ellipse2.setFill(Color.TRANSPARENT);
        root.getChildren().add(ellipse2);

        return ellipse2;
    }
}