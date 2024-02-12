package dto;

import java.awt.*;
import java.awt.geom.*;

public class Ellipse implements Shape {
    private double x;
    private double y;
    private double width;
    private double height;

    public Ellipse(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getCenterX() {
        return getX() + getWidth() / 2.0;
    }

    public double getCenterY() {
        return getY() + getHeight() / 2.0;
    }

    public void setFrame(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, (int)width, (int)height);
    }

    @Override
    public Rectangle2D getBounds2D() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    @Override
    public boolean contains(double px, double py) {
        return new Ellipse2D.Double(x, y, width, height).contains(px, py);
    }

    @Override
    public boolean contains(Point2D p) {
        return new Ellipse2D.Double(x, y, width, height).contains(p);
    }

    @Override
    public boolean intersects(double px, double py, double pw, double ph) {
        return new Ellipse2D.Double(x, y, width, height).intersects(px, py, pw, ph);
    }

    @Override
    public boolean intersects(Rectangle2D r) {
        return new Ellipse2D.Double(x, y, width, height).intersects(r);
    }

    @Override
    public boolean contains(double px, double py, double pw, double ph) {
        return new Ellipse2D.Double(x, y, width, height).contains(px, py, pw, ph);
    }

    @Override
    public boolean contains(Rectangle2D r) {
        return new Ellipse2D.Double(x, y, width, height).contains(r);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at) {
        return new Ellipse2D.Double(x, y, width, height).getPathIterator(at);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return new Ellipse2D.Double(x, y, width, height).getPathIterator(at, flatness);
    }
}
