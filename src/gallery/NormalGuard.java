package gallery;

import shapes.Rectangle;

/**
 * Let me create a Normal guard
 * 
 * @author Sebastian Zamora.
 * @author Johann Amaya.
 * @version 1.0
 */
public class NormalGuard extends Guard {
    /**
     * Constructor for objects of class Guard Normal
     */
    public NormalGuard(String color) {
        guardia = new Rectangle();
        guardia.moveHorizontal(-70);
        guardia.moveVertical(-15);
        guardia.changeColor(color);
        guardia.changeSize(5, 5);
    }

    /**
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
        int[] posActual = location();
        System.out.println(posActual[0] + " " + posActual[1]);
        guardia.moveVertical(-yPos);
        guardia.moveHorizontal(-xPos);
        guardia.moveVertical(y);
        guardia.moveHorizontal(x);
        xPos = x;
        yPos = y;
        makeVisible();
    }
}