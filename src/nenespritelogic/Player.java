/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nenespritelogic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import nenelogic.Hitbox;

/**
 *
 * @author Martin Joukl
 */
public class Player extends Sprite {

    public Player(double x, double y, double width, double height) throws FileNotFoundException {
        super(x, y, width, height, SpriteType.Player);
        this.imageGraphics = new Image(new FileInputStream("resources/knight.png"));
        this.setHitbox(new Hitbox(x, y + this.height / 2, this.width, this.height / 2));
    }

    @Override
    public void move() {
        super.move();
        this.hitbox.setY(position.getY() + this.height / 2);
    }

    @Override
    public void move(double x, double y) {
        super.move(x, y);
        this.hitbox.setY(position.getY() + this.height / 2);
    }

}
