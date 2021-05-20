package nenelogic;

import javafx.scene.canvas.Canvas;

/**
 *
 * @author Martin Joukl
 */
public class Hitbox {

    private double x;
    private double y;
    private double width;
    private double height;

    public double getX() {
        return x;
    }

    public Hitbox(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public boolean overlaps(Hitbox other) {
        return !(other.getX() + other.getWidth() < this.x
                || this.x + this.width < other.getX()
                || other.getY() + other.getHeight() < this.y
                || this.y + this.height < other.getY());

    }

    public boolean checkScreeCollision(Canvas Canvas, Camera camera) {
        return x + camera.getDx() < 0
                || (x + width) + camera.getDx() > Canvas.getWidth()
                || y - camera.getDy() < 0
                || (y + height) + camera.getDy() > Canvas.getHeight();
    }
}
