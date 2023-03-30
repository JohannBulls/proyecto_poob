
import java.util.ArrayList;
import java.util.List;
import java.awt.Polygon;
import java.lang.Math;

/**
 * Let me create an interate a room.
 *
 * @author Sebastian Zamora
 * @author Johann Amaya
 * @version 1.2
 */
public class Room {
    private int[][] walls;
    private boolean isVisible = false;
    private Wall[] lineas;
    private String color;
    private Guard guardia;
    private int length;
    private int width;
    private Polygon poligono;
    private Sculpture escultura;
    private Alarm alarma;
    private Rectangle repSala;
    private List<int[]> posiciones = new ArrayList<>();
    private Wall[] repMovimiento;

    /**
     * 
     * Creates a new Room object with the given parameters.
     * 
     * @param color   the color of the room
     * @param polygon an array of vertices representing the walls of the room
     * @param length  the length of the room's perimeter
     * @throws GalleryException if the room could not be created due to invalid
     *                          polygon parameters
     */
    public Room(String color, int[][] polygon, int length) throws GalleryException {
        walls = polygon;
        this.color = color;
        this.length = length;
        lineas = buildWalls(walls);
        boolean couldCreate = couldCreateRoom(walls);
        poligono = createPolygon(walls);
        if (!couldCreate) {
            throw new GalleryException(GalleryException.CouldNotCreateRoom);
        }
    }

    /**
     * 
     * Builds an array of Walls using the given polygon coordinates.
     * 
     * @param polygon A 2D array of integers representing the polygon coordinates.
     * @return An array of Walls representing the polygon's walls.
     */
    private Wall[] buildWalls(int[][] polygon) {
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

    /**
     * Let me erase the view of the walls
     */
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

    /**
     * 
     * Displays a sculpture in the room at the given position (x, y).
     * If there is already a sculpture in the room, it throws an exception.
     * If the given position is outside the room, it throws an exception.
     * 
     * @param x the x-coordinate of the position where to display the sculpture
     * @param y the y-coordinate of the position where to display the sculpture
     * @throws GalleryException if there is already a sculpture in the room or the
     *                          given position is outside the room
     */
    public void displaySculpture(int x, int y) throws GalleryException {
        if (escultura == null) {
            if (poligono.contains(x, y)) {
                escultura = new Sculpture(color, x, y, length);
                escultura.makeVisible();
            } else {
                throw new GalleryException(GalleryException.OutOfTheRoom);
            }
        } else {
            throw new GalleryException(GalleryException.RoomHasSculpture);
        }
    }

    /**
     * let me create a polygon with the structure of the room
     * 
     * @param walls the list of the points of the polygon.
     * @return The new polygon.
     */
    private Polygon createPolygon(int[][] walls) {
        int[] cordx = new int[walls.length];
        int[] cordy = new int[walls.length];
        for (int i = 0; i < walls.length; i++) {
            cordx[i] = walls[i][0];
            cordy[i] = walls[i][1];
        }
        return new Polygon(cordx, cordy, walls.length);
    }

    /**
     * 
     * Creates a polygon using the coordinates of the walls of the room.
     * 
     * @param walls an array of 2D coordinates representing the walls of the room.
     * @return a polygon object representing the shape of the room.
     */
    public void arrivedGuard() throws GalleryException {
        try {
            if (guardia == null) {
                guardia = new Guard(color);
                int[] posiciones = toSouth();
                // {{0,0},{20,0},{20,30},{60,30},{60,0},{80,0},{80,50},{0,50}}
                moveGuard(posiciones[1], posiciones[0]);
                makeVisible();
            } else {
                throw new GalleryException(GalleryException.RoomHasGuard);
            }
        } catch (GalleryException e) {
            throw new GalleryException(GalleryException.OutOfTheRoom);
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
                int[] pos = { x, y };
                posiciones.add(pos);
            } else {
                throw new GalleryException(GalleryException.OutOfTheRoom);
            }
        } else {
            throw new GalleryException(GalleryException.RoomHasNotGuard);
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

    /**
     * Returns the coordinates of the southernmost vertex of the room.
     *
     * @return an array of two integers, where the first integer represents the
     *         x-coordinate and the second integer
     *         represents the y-coordinate of the southernmost vertex of the room.
     */
    private int[] toSouth() {
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
    public void alarm(boolean on) throws GalleryException {
        try {
            alarma.turn(on);
        } catch (GalleryException e) {
            throw new GalleryException(GalleryException.AlarmNotChange);
        }
    }

    /**
     * Returns a boolean value indicating whether the alarm is turned off.
     *
     * @return boolean value representing the state of the alarm (on/off).
     */
    public boolean alarmTurnOf() {
        return alarma.state();
    }

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
            throw new GalleryException(GalleryException.RoomHasNotGuard);
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
            throw new GalleryException(GalleryException.RoomHasNotSculpture);
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
            throw new GalleryException(GalleryException.RoomHasNotSculpture);
        }
    }

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
    public boolean hasSculpture() {
        boolean hasSculpture = true;
        if (escultura == null) {
            hasSculpture = false;
        }
        return hasSculpture;
    }

    /**
     * Returns true if there has been a false alarm, false otherwise.
     * A false alarm occurs when the alarm is on and there is a sculpture in the
     * gallery.
     * 
     * @return a boolean indicating whether there has been a false alarm.
     */
    public boolean falseAlarm() {
        boolean falseAlarm = false;
        if (alarma.state() && escultura != null) {
            falseAlarm = true;
        }
        return falseAlarm;
    }

    /**
     * Calculates the distance traveled by the object based on its previous
     * positions.
     * 
     * @return a float value representing the total distance traveled
     */
    public float distanceTraveled() {
        float distancia = 0;
        if (posiciones.size() > 0) {
            for (int i = 1; i < posiciones.size(); i++) {
                distancia += calculate(posiciones.get(i - 1), posiciones.get(i));
                drawRoad();
            }
        }
        return distancia;
    }

    /**
     * Calculates the Euclidean distance between two points in a two-dimensional
     * space.
     *
     * @param punto1 the coordinates of the first point as an array of two integers
     * @param punto2 the coordinates of the second point as an array of two integers
     * @return the Euclidean distance between the two points as a float value
     */
    public float calculate(int[] punto1, int[] punto2) {
        float distance = (float) Math.sqrt(((punto2[0] - punto1[0]) * (punto2[0] - punto1[0]))
                + ((punto2[1] - punto1[1]) * (punto2[1] - punto1[1])));
        return distance;
    }

    /**
     * Returns the 2D integer array representing the walls of the gallery.
     * Each row of the array contains the x and y coordinates of the start
     * and end points of a wall.
     *
     * @return The 2D integer array representing the walls of the gallery.
     */
    public int[][] getWalls() {
        return walls;
    }

    /**
     * Verify if the point is inside of the room.
     * 
     * @param the coordinates the point
     * @return if the point is in the room.
     */
    public boolean containsPoint(int[] point) {
        return poligono.contains(point[0], point[1]);
    }

    /**
     * 
     * Draws a line representing the path traveled by the sculpture thief.
     * Each line segment represents the distance traveled between two adjacent
     * positions stored in the posiciones list.
     * The line color is set to gray.
     * The line objects are also stored in the repMovimiento array for later
     * removal.
     */
    private void drawRoad() {
        repMovimiento = new Wall[posiciones.size()];
        for (int i = 1; i < posiciones.size(); i++) {
            Wall line = new Wall(posiciones.get(i - 1)[0], length - posiciones.get(i - 1)[1], posiciones.get(i)[1],
                    length - posiciones.get(i)[1]);
            line.draw("gray");
            repMovimiento[i] = line;
        }
        Wall line = new Wall(posiciones.get(0)[0], length - posiciones.get(0)[1],
                posiciones.get(posiciones.size() - 1)[0], length - posiciones.get(posiciones.size() - 1)[1]);
        line.draw("gray");
        repMovimiento[0] = line;
    }
}
