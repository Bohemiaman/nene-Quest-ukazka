package nenespritelogic;

/**
 *
 * @author Martin Joukl
 */
public class Vector {

    private double x;
    private double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
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

    public void addVector(Vector anotherVector) {
        this.x += anotherVector.getX();
        this.y += anotherVector.getY();
    }

}
