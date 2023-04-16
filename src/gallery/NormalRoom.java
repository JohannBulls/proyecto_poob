package gallery;

import shapes.Rectangle;

/**
 * Let me create a Normal room.
 * 
 * @author Sebastian Zamora.
 * @author Johann Amaya.
 * @version 1.0
 */
public class NormalRoom extends Room {

    /**
     * Create a normal room.
     * 
     * @param color   the room´s color.
     * @param polygon the room's vertices.
     * @param length  the room´s length.
     * @throws GalleryException
     */
    public NormalRoom(String color, int[][] polygon, int length) throws GalleryException {
        walls = polygon;
        this.color = color;
        this.length = length;
        lineas = buildWalls(walls);
        poligono = createPolygon(walls);
    }

    @Override
    public void displaySculpture(String type, int x, int y)
            throws java.lang.reflect.InvocationTargetException, IllegalAccessException, InstantiationException,
            ClassNotFoundException, NoSuchMethodException,
            GalleryException {
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
        if (escultura != null) {
            if (guardSeeTheSculpture()) {
                escultura.steal();
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
        return escultura.getState();
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
        if (escultura != null) {
            escultura.makeVisible();
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
        if (escultura != null) {
            escultura.makeInvisible();
        }
        repSala.makeInvisible();
        alarma.makeInvisible();
    }
}
