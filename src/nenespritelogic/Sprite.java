package nenespritelogic;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import nenelogic.Block;
import nenelogic.Camera;
import nenelogic.Hitbox;

/**
 *
 * @author Martin Joukl
 *
 *
 */
public abstract class Sprite {

    protected Vector position;
    protected Vector velocity;
    protected final double height;
    protected final double width;
    protected Hitbox hitbox;
    protected Image imageGraphics;
    protected final double DEFAULT_SPRITE_SIZE = 20;
    protected final SpriteType spriteType;

    public Sprite(double x, double y, double width, double height, SpriteType spriteType) {
        position = new Vector(x, y);
        velocity = new Vector(0, 0);
        this.width = width * DEFAULT_SPRITE_SIZE;
        this.height = height * DEFAULT_SPRITE_SIZE;
        this.hitbox = new Hitbox(position.getX(), position.getY(), this.width, this.height);
        this.spriteType = spriteType;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public void setImageGraphics(Image imageGraphics) {
        this.imageGraphics = imageGraphics;
    }

    public void setHitbox(Hitbox hitbox) {
        this.hitbox = hitbox;
    }

    public void render(Canvas canvas, Camera camera) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(imageGraphics, position.getX() + camera.getDx(), position.getY() + camera.getDy(), width, height);
    }

    public void setPosition(double x, double y) {
        this.position.setX(x);
        this.position.setY(y);
    }

    public Vector getVelocity() {
        return velocity;
    }

    public Vector getPosition() {
        return position;
    }

    public boolean overlaps(Sprite other) {
        return hitbox.overlaps(other.getHitbox());
    }

    public boolean overlaps(Block other) {
        return other.overlaps(hitbox);
    }

    public void move() {
        position.addVector(velocity);
        this.hitbox.setX(position.getX());
        this.hitbox.setY(position.getY());
    }

    public void move(double x, double y) {
        position.addVector(new Vector(x, y));
        this.hitbox.setX(position.getX());
        this.hitbox.setY(position.getY());
    }

    public void setVelocity(double x, double y) {
        this.velocity.setX(x);
        this.velocity.setY(y);
    }

    public void setVelocityByAngle(double angle, double speed) {
        angle = Math.toRadians(angle);
        double changeX = Math.cos(angle) * speed;
        double changeY = -Math.sin(angle) * speed;
        velocity.setX(changeX);
        velocity.setY(changeY);
    }

    public boolean checkScreeCollision(Canvas canvas, Camera camera) {
        return hitbox.checkScreeCollision(canvas, camera);
    }
}
