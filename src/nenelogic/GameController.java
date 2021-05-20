package nenelogic;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import nenespritelogic.Player;
import nenespritelogic.Sprite;

/**
 *
 * @author Martin Joukl
 */
public class GameController {

    List<Block> blocks;
    Canvas canvas;
    List<Sprite> sprites;
    Camera camera;
    double numberOfBlocksInColumn = 54;
    double sizeOfBlock = 20;
    double gallopForwardSpeed = 3;
    Sprite player;

    public GameController(Canvas canvas) throws FileNotFoundException {
        this.camera = new Camera();
        this.blocks = new ArrayList<>();
        this.sprites = new ArrayList<>();
        this.canvas = canvas;

        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < numberOfBlocksInColumn; j++) {
                BlockType type;
                if (j < 20) {
                    type = BlockType.GRASS;
                } else {
                    type = BlockType.LAND;
                }
                blocks.add(new Block(i * sizeOfBlock, j * sizeOfBlock, sizeOfBlock, sizeOfBlock, type));
            }
        }
        this.player = new Player(500, 500, 15, 15);
        sprites.add(player);
    }

    public GameController(List<Block> blocks, Canvas canvas) throws FileNotFoundException {
        this(canvas);
        this.blocks = blocks;

    }

    public void render() {
        blocks.forEach((t) -> {
            t.render(canvas, camera);
        });
        sprites.forEach((t) -> {
            t.render(canvas, camera);
        });
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Hitbox hitbox = player.getHitbox();
        gc.setFill(new javafx.scene.paint.Color(0, 0, 0, 0.5));
        gc.fillRect(hitbox.getX() + camera.getDx(), hitbox.getY() + camera.getDy(), hitbox.getWidth(), hitbox.getHeight());
    }

    public Sprite getPlayer() {
        return player;
    }

    public void updatePosition() {
        sprites.forEach((t) -> {
            t.move();
        });
        //extra pohyb 
        player.move(gallopForwardSpeed, 0);
        //opačný směr
        camera.addDx(-gallopForwardSpeed);
        checkCollision();
    }

    void checkCollision() {
        blocks.forEach((t) -> {
            if (t.getBlockType() == BlockType.LAND) {
                return;
            }
            resolveCollisionWithBlock(t);
            resolveCollisionWithScreen();

        });
    }

    public Camera getCamera() {
        return camera;
    }

    private void resolveCollisionWithScreen() {
        if (player.checkScreeCollision(canvas, camera)) {
            player.move(0, -player.getVelocity().getY());
            if (player.checkScreeCollision(canvas, camera)) {
                player.move(-player.getVelocity().getX(), +player.getVelocity().getY());
                //pokud je stále mimo, přídá zpět změnu Y
                if (player.checkScreeCollision(canvas, camera)) {
                    player.move(0, -player.getVelocity().getY());
                }
            }
        }
    }

    private void resolveCollisionWithBlock(Block t) {
        if (t.overlaps(player.getHitbox())) {
            //vrátí zpátky

            player.move(0, -player.getVelocity().getY());
            //pokud je stále mimo, vrátí minulou změnu a posune ve směru X
            if (t.overlaps(player.getHitbox())) {
                player.move(-player.getVelocity().getX(), +player.getVelocity().getY());
                //pokud je stále mimo, přídá zpět změnu Y
                if (t.overlaps(player.getHitbox())) {
                    player.move(0, -player.getVelocity().getY());
                }
            }

        }
    }
}
