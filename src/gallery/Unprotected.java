package gallery;

import shapes.Rectangle;

/**
 * Creates a new Room that it doesn't has alarm.
 * 
 * @author Sebastian Zamora.
 * @author Johann Amaya.
 * @version 1.0
 */
public class Unprotected extends Room {

    /**
     * Create a new Unprotected room.
     * 
     * @param color   the room´s color.
     * @param polygon the room's vertices.
     * @param length  the room´s length.
     * @throws GalleryException
     */
    public Unprotected(String color, int[][] polygon, int length) throws GalleryException {
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
    public void displaySculpture(String type, int x, int y) throws java.lang.reflect.InvocationTargetException,IllegalAccessException,InstantiationException,ClassNotFoundException,NoSuchMethodException, GalleryException {
        if (escultura == null) {
            if (poligono.contains(x, y)) {
                escultura = (Sculpture) Class.forName("gallery." + type)
                        .getConstructor(String.class, int.class, int.class, int.class).newInstance(color, x, y, length);
                escultura.makeVisible();
            } else {
                throw new GalleryException(GalleryException.OUT_OF_THE_ROOM);
            }
        } else {
            throw new GalleryException(GalleryException.ROOM_HAS_SCULPTURE);
        }
    }

    @Override
    public void alarm(int cantidadRoom, int length) throws GalleryException {
        throw new GalleryException(GalleryException.UNPROTECTED_ROOM);
    }

    @Override
    public void alarm(boolean on) throws GalleryException {
        throw new GalleryException(GalleryException.UNPROTECTED_ROOM);
    }

    public boolean alarmTurnOf() throws GalleryException {
        throw new GalleryException(GalleryException.UNPROTECTED_ROOM);
    }

    @Override
    public void steal() throws GalleryException {
        if (escultura != null) {
            if (guardSeeTheSculpture()) {
                escultura.makeInvisible();
                escultura = null;
            } else {
                escultura.enlarge(5);
                escultura.makeVisible();
            }
        } else {
            throw new GalleryException(GalleryException.ROOM_HAS_NOT_SCULPTURE);
        }
    }

    @Override
    public boolean hasSculpture() {
        boolean hasSculpture = true;
        if (escultura == null) {
            hasSculpture = false;
        }
        return hasSculpture;
    }

    @Override
    public boolean falseAlarm() throws GalleryException {
        throw new GalleryException(GalleryException.UNPROTECTED_FALSE_ALARM);
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
        if (escultura != null) {
            escultura.makeVisible();
        }
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
        if (escultura != null) {
            escultura.makeInvisible();
        }
    }
}