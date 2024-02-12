package dto;

public class Ellipse {
    private double speed;
    private boolean clockwise;

    public Ellipse(double speed, boolean clockwise) {
        this.speed = speed;
        this.clockwise = clockwise;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean isClockwise() {
        return clockwise;
    }

    public void setClockwise(boolean clockwise) {
        this.clockwise = clockwise;
    }

    @Override
    public String toString() {
        return "Ellipse{ speed=" + speed + ", clockwise=" + clockwise + " }";
    }
}