package gallery;
import shapes.Rectangle;
/**
 * Me permite crear un guardia
 *
 * @author Sebastian Zamora
 * @author Johann Amaya
 * @version 1.3
 */
public abstract class Guard {
    protected Rectangle guardia;
    protected int xPos = 0;
    protected int yPos = 0;
    protected int length = 0;

    /**
     * 
     * Moves the guard to the specified coordinates (x,y) within the given length of
     * the room.
     * 
     * @param x      The x-coordinate to move the guard to.
     * @param y      The y-coordinate to move the guard to.
     * @param length The length of the room.
     */
    public abstract void moveGuard(int x, int y, int length) ;

    /**
     * Let me make visible the rooms on the Guard
     */
    public void makeVisible() {
        guardia.makeVisible();
    }

    /**
     * Let me make visible the rooms on the Guard
     */
    public void makeInvisible() {
        guardia.makeInvisible();
    }

    /**
     * Let me change the color of the guard.
     * 
     * @Param color the color of the room.
     */
    public void changeColor(String color) {
        guardia.changeColor(color);
    }

    /**
     * Return its position
     * 
     * @return the guard position.
     */
    public int[] location() {
        int[] posiciones = { xPos, length - yPos };
        return posiciones;
    }
}
