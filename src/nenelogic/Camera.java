/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nenelogic;

/**
 *
 * @author Martin Joukl
 */
public class Camera {

    private double dx = 0;
    private double dy = 0;

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void addDx(double plusDx) {
        this.dx += plusDx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

}
