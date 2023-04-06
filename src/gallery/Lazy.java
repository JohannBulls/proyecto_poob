package gallery;

import shapes.Rectangle;

/**
 * Let me create a guard that moves half the distance less than a regular guard.
 * 
 * @author Sebastian Zamora.
 * @author Johann Amaya.
 * @version 1.0
 */
public class Lazy extends Guard {

    /**
     * Constructor fot objects of class Guard Lazy
     */
    public Lazy(String color) {
        guardia = new Rectangle();
        guardia.moveHorizontal(-70);
        guardia.moveVertical(-15);
        guardia.changeColor(color);
        guardia.changeSize(7, 5);
    }

    @Override
    public void moveGuard(int x, int y, int length) {
        makeInvisible();
        this.length = length;
        guardia.moveVertical(-yPos);
        guardia.moveHorizontal(-xPos);
        int[] posActual = location();
        if (xPos != 0 && yPos != 0) {
            x = (posActual[0] + x) / 2;
            y = ((300 - posActual[1] + y) / 2);
        }
        guardia.moveVertical(y);
        guardia.moveHorizontal(x);
        xPos = x;
        yPos = y;
        makeVisible();
    }
}