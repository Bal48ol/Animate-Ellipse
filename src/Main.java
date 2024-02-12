import dto.Ellipse;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class Main extends JPanel {
    private final ArrayList<Ellipse> firstEllipses;
    private final ArrayList<Ellipse> otherEllipses;
    private final int initialRadiusX = 100;
    private final int initialRadiusY = 50;
    private int radiusX = initialRadiusX;
    private int radiusY = initialRadiusY;
    private int xPosition = 0;
    private double rotationAngle = 0.0;
    private double rotationIncrement = 0.1;
    private int timerDelay = 100;
    private final JSlider speedSliderFirstEllipse;
    private int firstEllipseIndex = -1;

    public Main() {
        setBackground(Color.BLACK);
        firstEllipses = new ArrayList<>();
        otherEllipses = new ArrayList<>();

        Timer timer = new Timer(timerDelay, e -> {
            addEllipse();
            rotationAngle += rotationIncrement;
            repaint();
        });
        timer.start();

        speedSliderFirstEllipse = new JSlider(JSlider.HORIZONTAL, 0, 300, 150);
        speedSliderFirstEllipse.setPreferredSize(new Dimension(400, speedSliderFirstEllipse.getPreferredSize().height));
        speedSliderFirstEllipse.setBackground(Color.black);
        JButton reverseButtonFirstEllipse = new JButton("Изменить направление вращения");

        speedSliderFirstEllipse.addChangeListener(e -> {
            int value = speedSliderFirstEllipse.getValue();
            timerDelay = 300 - value;
            timer.setDelay(timerDelay);
        });

        reverseButtonFirstEllipse.addActionListener(e -> {
            rotationIncrement = -rotationIncrement;
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.black);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(reverseButtonFirstEllipse);
        buttonPanel.add(speedSliderFirstEllipse);
        add(buttonPanel);
    }

    private void addEllipse() {
        Ellipse ellipse = new Ellipse(xPosition, (double) getHeight() / 2 - radiusY, 2 * radiusX, 2 * radiusY);

        if (firstEllipses.isEmpty()) {
            firstEllipses.add(ellipse);
            firstEllipseIndex = 0;
        } else {
            otherEllipses.add(ellipse);
        }

        if (!otherEllipses.isEmpty()) {
            int prevIndex = otherEllipses.size() - 1;
            Ellipse prevEllipse = otherEllipses.get(prevIndex);
            xPosition = (int) (prevEllipse.getCenterX());
            ellipse.setFrame(xPosition, (double) getHeight() / 2 - radiusY, 2 * radiusX, 2 * radiusY);
        }

        radiusX = (int) (radiusX * 0.95);
        radiusY = (int) (radiusY * 0.95);

        if (xPosition + 2 * radiusX > getWidth()) {
            resetShapes();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < firstEllipses.size() + otherEllipses.size(); i++) {
            Ellipse ellipse;
            if (i < firstEllipses.size()) {
                ellipse = firstEllipses.get(i);
            } else {
                ellipse = otherEllipses.get(i - firstEllipses.size());
            }

            double centerX = ellipse.getCenterX();
            double centerY = ellipse.getCenterY();
            int pointSize = 5;

            g2d.setColor(Color.WHITE);
            if (i == firstEllipseIndex) {
                g2d.setColor(Color.RED);
            }

            g2d.fillOval((int) centerX - pointSize / 2, (int) centerY - pointSize / 2, pointSize, pointSize);

            g2d.rotate(rotationAngle, centerX, centerY);
            g2d.draw(ellipse);
            g2d.rotate(-rotationAngle, centerX, centerY);
        }
    }

    private void resetShapes() {
        xPosition = 0;
        radiusX = initialRadiusX;
        radiusY = initialRadiusY;
        firstEllipses.clear();
        otherEllipses.clear();
        rotationAngle = 0.0;
        firstEllipseIndex = -1;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Connected Ellipses");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Main main = new Main();
        frame.add(main);
        frame.setSize(1650, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}