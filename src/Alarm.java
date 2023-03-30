/**
 * Let me create and interactive with the alarm.
 *
 * @author Sebastian Zamora
 * @author Johann Amaya
 * @version 1.3
 */
public class Alarm {
    private boolean on;
    private Circle alarma;

    /**
     * Constructor for objects of class Alam
     */
    public Alarm(int cantidadRooms, int length) {
        alarma = new Circle();
        on = false;
        alarma.changeSize(20);
        alarma.changeColor("black");
        alarma.moveHorizontal(-20);
        alarma.moveHorizontal(length + 40);
        alarma.moveVertical(-15);
        alarma.moveVertical(50 * (cantidadRooms - 1));
    }

    /**
     * Changes the state of the alarm system.
     * 
     * @param state The desired state of the alarm system (true for on, false for
     *              off).
     * @throws GalleryException If the new state is the same as the current state of
     *                          the alarm system.
     */
    public void turn(boolean stade) throws GalleryException {
        if (stade != on) {
            on = stade;
            if (stade) {
                alarma.changeColor("red");
            } else {
                alarma.changeColor("black");
            }
        } else {
            throw new GalleryException(GalleryException.AlarmNotChange);
        }
    }

    /**
     * Draw the alarm
     */
    public void makeVisible() {
        alarma.makeVisible();
    }

    /**
     * Errase the alarm
     */
    public void makeInvisible() {
        alarma.makeInvisible();
    }

    /**
     * Returns the current state of the alarm.
     *
     * @return true if the alarm is on, false if it is off.
     */
    public boolean state() {
        return on;
    }
}