package gallery;

import shapes.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.awt.Polygon;
import java.lang.Math;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormatSymbols;
import java.text.DecimalFormat;

/**
 * Let me create an interate a room.
 *
 * @author Sebastian Zamora
 * @author Johann Amaya
 * @version 1.5
 */
public abstract class Room {
    protected int[][] walls;
    protected boolean isVisible = false;
    protected Wall[] lineas;
    protected String color;
    protected Guard guardia;
    protected int length;
    protected int width;
    protected Polygon poligono;
    protected Sculpture escultura;
    protected Alarm alarma;
    protected Rectangle repSala;
    protected List<int[]> posiciones = new ArrayList<>();

    /**
     * 
     * Builds an array of Walls using the given polygon coordinates.
     * 
     * @param polygon A 2D array of integers representing the polygon coordinates.
     * @return An array of Walls representing the polygon's walls.
     */
    protected Wall[] buildWalls(int[][] polygon) {
        Wall[] muros = new Wall[polygon.length];
        for (int i = 1; i < polygon.length; i++) {
            Wall line = new Wall(walls[i - 1][0], length - walls[i - 1][1], walls[i][0], length - walls[i][1]);
            muros[i] = line;
        }
        Wall line = new Wall(walls[0][0], length - walls[0][1], walls[walls.length - 1][0],
                length - walls[walls.length - 1][1]);
        muros[0] = line;
        return muros;
    }

    /**
     * Let me show the walls
     */
    public abstract void makeVisible();

    /**
     * Let me erase the view of the walls
     */
    public abstract void makeInvisible();

    /**
     * 
     * Displays a sculpture in the room at the given position (x, y).
     * If there is already a sculpture in the room, it throws an exception.
     * If the given position is outside the room, it throws an exception.
     * 
     * @param x the x-coordinate of the position where to display the sculpture
     * @param y the y-coordinate of the position where to display the sculpture
     * @throws GalleryException          if there is already a sculpture in the room
     *                                   or
     *                                   the
     *                                   given position is outside the room
     * @throws NoSuchMethodException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public abstract void displaySculpture(String type, int x, int y)
            throws GalleryException, NoSuchMethodException, ClassNotFoundException, InstantiationException,
            IllegalAccessException, InvocationTargetException;

    /**
     * let me create a polygon with the structure of the room
     * 
     * @param walls the list of the points of the polygon.
     * @return The new polygon.
     */
    protected Polygon createPolygon(int[][] walls) {
        int[] cordx = new int[walls.length];
        int[] cordy = new int[walls.length];
        for (int i = 0; i < walls.length; i++) {
            cordx[i] = walls[i][0];
            cordy[i] = walls[i][1];
        }
        return new Polygon(cordx, cordy, walls.length);
    }

    /**
     * Creates a polygon using the coordinates of the walls of the room.
     * 
     * @param walls an array of 2D coordinates representing the walls of the room.
     * @return a polygon object representing the shape of the room.
     */
    public void arrivedGuard(String type) throws java.lang.reflect.InvocationTargetException, IllegalAccessException,
            InstantiationException, ClassNotFoundException, NoSuchMethodException, GalleryException {
        try {
            if (guardia == null) {
                guardia = (Guard) Class.forName("gallery." + type)
                        .getConstructor(String.class).newInstance(color);
                int[] posiciones = toSouth();
                moveGuard(posiciones[1], posiciones[0]);
                makeVisible();
            } else {
                throw new GalleryException(GalleryException.ROOM_HAS_GUARD);
            }
        } catch (GalleryException e) {
            throw new GalleryException(GalleryException.OUT_OF_THE_ROOM);
        }
    }

    /**
     * Moves the guard to the specified position within the room.
     * 
     * @param x the x-coordinate of the position to move the guard to
     * @param y the y-coordinate of the position to move the guard to
     * @throws GalleryException if the specified position is outside of the room or
     *                          if the room does not have a guard
     */
    public void moveGuard(int x, int y) throws GalleryException {
        if (guardia != null) {
            if (poligono.contains(x, y)) {
                guardia.moveGuard(x + 1, length - y - 5, length);
                if (guardSeeTheSculpture()) {
                    if (escultura.getType().equals("Shy")) {
                        escultura.makeInvisible();
                    }
                }
            } else {
                throw new GalleryException(GalleryException.OUT_OF_THE_ROOM);
            }
        } else {
            throw new GalleryException(GalleryException.ROOM_HAS_GUARD);
        }
    }

    /**
     * 
     * Creates a new alarm object and a visual representation of the alarm system in
     * the gallery.
     * 
     * @param cantidadRooms the number of rooms in the gallery.
     * @param length        the length of each room in the gallery.
     */
    public abstract void alarm(int cantidadRooms, int length) throws GalleryException;

    /**
     * Returns the coordinates of the southernmost vertex of the room.
     *
     * @return an array of two integers, where the first integer represents the
     *         x-coordinate and the second integer
     *         represents the y-coordinate of the southernmost vertex of the room.
     */
    protected int[] toSouth() {
        int[] r = new int[2];
        r[0] = walls[0][1];
        r[1] = walls[0][0];
        for (int i = 0; i < walls.length; i++) {
            if (walls[i][1] < r[0]) {
                r[0] = walls[i][1];
                r[1] = walls[i][0];
            }
        }
        for (int j = 0; j < walls.length; j++) {
            if (walls[j][1] == r[0] && walls[j][0] < r[1]) {
                r[1] = walls[j][0];
            }
        }
        return r;
    }

    /**
     * 
     * This method allows to turn on/off the alarm of the gallery.
     * 
     * @param on a boolean indicating if the alarm should be turned on (true) or off
     *           (false)
     * @throws GalleryException if the alarm can't be changed to the desired state
     */
    public abstract void alarm(boolean on) throws GalleryException;

    /**
     * Returns a boolean value indicating whether the alarm is turned off.
     *
     * @return boolean value representing the state of the alarm (on/off).
     */
    public abstract boolean alarmTurnOf() throws GalleryException;

    /**
     * Returns a boolean value indicating whether the alarm is turned off.
     *
     * @return boolean value representing the state of the alarm (on/off).
     */
    public int[] guardLocation() throws GalleryException {
        int[] location;
        if (guardia != null) {
            location = guardia.location();
        } else {
            throw new GalleryException(GalleryException.ROOM_HAS_NOT_GUARD);
        }
        return location;
    }

    /**
     * Returns the location of the sculpture in the gallery.
     *
     * @return an integer array with two elements representing the x and y
     *         coordinates, respectively
     * @throws GalleryException if the room has not sculpture
     */
    public int[] sculptureLocation() throws GalleryException {
        int[] location;
        if (escultura != null) {
            location = escultura.location();
        } else {
            throw new GalleryException(GalleryException.ROOM_HAS_NOT_SCULPTURE);
        }
        return location;
    }

    /**
     * Checks if the given set of walls intersect with the existing walls of the
     * gallery.
     * 
     * @param prueba the 2D array of integer coordinates representing the walls to
     *               be checked
     * @return true if any of the walls intersect with the existing walls of the
     *         gallery, false otherwise
     */
    public boolean intersect(int[][] prueba) {
        Wall[] salas = buildWalls(prueba);
        boolean intersect = false;
        for (Wall u : lineas) {
            if (u.intersects(salas)) {
                intersect = true;
            }
        }
        return intersect;
    }

    /**
     * Determines if a room with the given polygon could be created without
     * intersecting any existing walls.
     *
     * @param polygon 2D array of integers representing the polygon vertices.
     * @return boolean value indicating whether or not a room could be created.
     * @throws GalleryException if there is an error creating the room.
     */
    public boolean couldCreateRoom(int[][] polygon) throws GalleryException {
        boolean intersect = true;
        for (Wall u : lineas) {
            int contador = 0;
            for (Wall i : lineas) {
                if (u.intersect(i)) {
                    if (contador > 3) {
                        intersect = false;
                    }
                    contador++;
                }
            }
        }
        return intersect;
    }

    /**
     * Tries to steal the sculpture. If the sculpture is not visible, an exception
     * is thrown.
     * If the guard is not watching the sculpture, the sculpture is enlarged and
     * made visible to
     * alert the guard. If the guard is watching the sculpture, the sculpture is
     * stolen by making
     * it invisible and setting the reference to null.
     * 
     * @throws GalleryException if the room does not have a sculpture.
     */
    public abstract void steal() throws GalleryException;

    /**
     * Checks if the guard has a clear line of sight to the sculpture in the
     * gallery.
     *
     * @return true if the guard has a clear line of sight to the sculpture, false
     *         otherwise.
     */
    public boolean guardSeeTheSculpture() {
        int[] posGuardia = guardia.location();
        int[] posEscultura = escultura.location();
        Wall vista = new Wall(posGuardia[0], length - posGuardia[1], posEscultura[0], length - posEscultura[1]);
        return vista.intersects(lineas);
    }

    /**
     * Checks if the gallery has a sculpture.
     * 
     * @return boolean value `true` if there is a sculpture in the gallery, `false`
     *         if there is no sculpture.
     */
    public abstract boolean hasSculpture() throws GalleryException;

    /**
     * Returns true if there has been a false alarm, false otherwise.
     * A false alarm occurs when the alarm is on and there is a sculpture in the
     * gallery.
     * 
     * @return a boolean indicating whether there has been a false alarm.
     */
    public abstract boolean falseAlarm() throws GalleryException;

    /**
     * Calculates the distance traveled by the object based on its previous
     * positions.
     * 
     * @return a float value representing the total distance traveled
     */
    public float distanceTraveled() {
        float distancia = 0;
        DecimalFormatSymbols extencion = new DecimalFormatSymbols();
        extencion.setDecimalSeparator('.');
        DecimalFormat formato1 = new DecimalFormat("#.000000", extencion);
        if (posiciones.size() > 0) {
            for (int i = 1; i < posiciones.size(); i++) {
                distancia += Math.hypot(posiciones.get(i + 1)[0] - posiciones.get(i)[0],
                        posiciones.get(i + 1)[1] - posiciones.get(i)[1]);
            }
        }
        formato1.format(distancia);
        return distancia;
    }
}
