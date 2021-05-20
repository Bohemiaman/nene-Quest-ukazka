package nenelogic;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author Martin Joukl
 */
public class Block {

    double x;
    double y;
    double height;
    double width;
    BlockType blockType;

    public Block(double x, double y, double height, double width, BlockType blockType) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.blockType = blockType;
    }
//    public boolean overlaps(){
//    
//    }

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

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }

    public void render(Canvas canvas, Camera camera) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        switch (this.getBlockType()) {
            case GRASS:
                gc.setFill(Color.GREENYELLOW);
                break;
            case LAND:
                gc.setFill(Color.BURLYWOOD);
                break;
            case WALL:
                gc.setFill(Color.GREY);
                break;
            case WATER:
                gc.setFill(Color.AQUAMARINE);
            case WOOD:
                gc.setFill(Color.BROWN);
            default:
                gc.setFill(Color.WHITE);
        }
        gc.fillRect(x + camera.getDx(), y + camera.getDy(), width, height);
        //XXX smaž mě, test
        //gc.strokeRect(x + camera.getDx(), y + camera.getDy(), width, height);
    }

    public boolean overlaps(Hitbox other) {
        return !(other.getX() + other.getWidth() < this.x
                || this.x + this.width < other.getX()
                || other.getY() + other.getHeight() < this.y
                || this.y + this.height < other.getY());

    }
}
