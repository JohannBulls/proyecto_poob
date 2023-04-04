package gallery;
import shapes.Rectangle;
/**
 * Me permite crear un guardia
 *
 * @author Sebastian Zamora
 * @author Johann Amaya
 * @version 1.1
 */
public class Guard {
    private Rectangle guardia;
    private int xPos = 0;
    private int yPos = 0;
    private int length = 0;

    /**
     * Constructor for objects of class Guard
     */
    public Guard(String color) {
        guardia = new Rectangle();
        guardia.moveHorizontal(-70);
        guardia.moveVertical(-15);
        guardia.changeColor(color);
        guardia.changeSize(5, 5);
    }

    /**
     * 
     * Moves the guard to the specified coordinates (x,y) within the given length of
     * the room.
     * 
     * @param x      The x-coordinate to move the guard to.
     * @param y      The y-coordinate to move the guard to.
     * @param length The length of the room.
     */
    public void moveGuard(int x, int y, int length) {
        makeInvisible();
        this.length = length;
        int[] posiciones = guardia.posiciones();
        guardia.moveVertical(-yPos);
        guardia.moveHorizontal(-xPos);
        guardia.moveVertical(y);
        guardia.moveHorizontal(x);
        xPos = x;
        yPos = y;
        makeVisible();
    }

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
