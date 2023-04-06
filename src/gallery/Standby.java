package gallery;

import shapes.Rectangle;

/**
 * Creates a new Room that it doesn't contains sculptures.
 * 
 * @author Sebastian Zamora.
 * @author Johann Amaya.
 * @version 1.0
 */
public class Standby extends Room {
    /**
     *
     * Creates a new Room that it doesn't contains sculptures.
     *
     * @param color   the color of the room
     * @param polygon an array of vertices representing the walls of the room
     * @param length  the length of the room's perimeter
     * @throws GalleryException if the room could not be created due to invalid
     *                          polygon parameters
     */
    public Standby(String color, int[][] polygon, int length) throws GalleryException {
        walls = polygon;
        this.color = color;
        this.length = length;
        lineas = buildWalls(walls);
        boolean couldCreate = couldCreateRoom(walls);
        poligono = createPolygon(walls);
        if (!couldCreate) {
            throw new GalleryException(GalleryException.COULD_NOT_CREATE_ROOM);
        }
    }

    @Override
    public void displaySculpture(String color, int x, int y) throws GalleryException {
        throw new GalleryException(GalleryException.STANDBY_ROOM);
    }

    @Override
    public void alarm(int cantidadRooms, int length) {
        alarma = new Alarm(cantidadRooms, length);
        repSala = new Rectangle();
        repSala.changeColor(color);
        repSala.changeSize(20, 20);
        repSala.moveHorizontal(-70);
        repSala.moveHorizontal(length);
        repSala.moveVertical(-15);
        repSala.moveVertical(50 * (cantidadRooms - 1));
        repSala.makeVisible();
        alarma.makeVisible();
    }

    @Override
    public void alarm(boolean on) throws GalleryException {
        try {
            alarma.turn(on);
        } catch (GalleryException e) {
            throw new GalleryException(GalleryException.ALARM_NOT_CHANGE);
        }
    }

    @Override
    public boolean alarmTurnOf() {
        return alarma.state();
    }

    @Override
    public void steal() throws GalleryException {
        throw new GalleryException(GalleryException.STANDBY_STEAL);
    }

    @Override
    public boolean hasSculpture() throws GalleryException {
        throw new GalleryException(GalleryException.STANDBY_NOT_SCULPTUER);
    }

    @Override
    public boolean falseAlarm() {
        boolean falseAlarm = false;
        if (alarma.state() && escultura != null) {
            falseAlarm = true;
        }
        return falseAlarm;
    }

    @Override
    public void makeVisible() {
        isVisible = true;
        if (isVisible) {
            for (Wall i : lineas) {
                i.draw(color);
            }
        }
        if (guardia != null) {
            guardia.makeVisible();
        }
        repSala.makeVisible();
        alarma.makeVisible();
    }

    @Override
    public void makeInvisible() {
        isVisible = false;
        if (!isVisible) {
            for (Wall i : lineas) {
                i.erase();
            }
        }
        if (guardia != null) {
            guardia.makeInvisible();
        }
        repSala.makeInvisible();
        alarma.makeInvisible();
    }
}