package gallery;

import shapes.Circle;

/**
 * Let me create and modify the sculpture object.
 *
 * @author Sebastian Zamora
 * @author Johann Amaya
 * @version 1.0
 */
public class NormalSculpture extends Sculpture {

    /**
     * Let me create a Normal Sculpture.
     * 
     * @param color  The room´s color.
     * @param x      The x-Coorder of the sculpture.
     * @param y      The y-Coorder of the sculpture.
     * @param length The gallery's length.
     */
    public NormalSculpture(String color, int x, int y, int length) {
        circle = new Circle();
        circle.changeColor(color);
        circle.changeSize(size);
        this.x = x;
        this.y = y;
        this.length = length;
        circle.moveVertical(length - y - 15);
        circle.moveHorizontal(x - 20);
        type = "Normal";
    }

    @Override
    public void steal() {
        makeInvisible();
        state = false;
    }
}