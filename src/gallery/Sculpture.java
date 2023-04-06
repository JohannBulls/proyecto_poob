package gallery;

import shapes.Circle;

/**
 * Let me create and modify the sculpture object.
 *
 * @author Sebastian Zamora
 * @author Johann Amaya
 * @version 1.5
 */
public abstract class Sculpture {
    protected Circle circle;
    protected static final int size = 10;
    protected int x, y;
    protected boolean state;
    protected int length;
    protected String type;

    /**
     * Let me make visible the rooms on the Sculpture
     */
    public void makeVisible() {
        circle.makeVisible();
    }

    /**
     * Let me make visible the rooms on the Sculpture
     */
    public void makeInvisible() {
        circle.makeInvisible();
    }

    /**
     * Let me change the color of the sculpture.
     * 
     * @Param color the color of the room.
     */
    public void changeColor(String color) {
        circle.changeColor(color);
    }

    /**
     * Return its position
     * 
     * @return the sculpture's position
     */
    public int[] location() {
        int[] posiciones = { x, y };
        return posiciones;
    }

    /**
     * increase the size of the sculpture.
     * 
     * @param increase The increase that for the sculpture.
     */
    public void enlarge(int increase) {
        circle.changeSize(size + increase);
    }

    /**
     * Steal the sculpture
     */
    public abstract void steal() throws GalleryException;

    /**
     * Get the sculpture's state.
     * 
     * @return whether or not the sculpture is in the room.
     */
    public boolean getState() {
        return state;
    }

    /**
     * Return the sculpture's type.
     * 
     * @return the sculpture's type.
     */
    public String getType() {
        return type;
    }
}
