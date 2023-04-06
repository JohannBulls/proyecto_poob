package gallery;
import shapes.Circle;
/**
 * Let me create a sculpture that if it's see for a guard disapear itself.
 * 
 * @author Sebastian Zamora.
 * @author Johann Amaya.
 * @version 1.0
 */
public class Shy extends Sculpture {
    /**
     * Let create a Shy Sculpture.
     * 
     * @param color  The room´s color.
     * @param x      The x-Coorder of the sculpture.
     * @param y      The y-Coorder of the sculpture.
     * @param length The gallery's length.
     */
    public Shy(String color, int x, int y, int length) {
        circle = new Circle();
        circle.changeColor(color);
        circle.changeSize(size + 2);
        this.x = x;
        this.y = y;
        this.length = length;
        circle.moveVertical(length - y - 15);
        circle.moveHorizontal(x - 20);
        state = true;
        type = "Shy";
    }

    @Override
    public void steal() {
        makeInvisible();
        state = false;
    }
}