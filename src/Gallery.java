import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Let me create a galery
 *
 * @author Sebastian Zamora
 * @author Johann Amaya
 * @version 1.3
 */
public class Gallery {
    private HashMap<String, Room> rooms = new HashMap();
    private int length;
    private int width;
    private String exepcion;
    private boolean confirm;
    private boolean problem = true;

    /**
     * Constructor for objects of class Galery.
     * 
     * @param length The length of the galery.
     * @param width  The width of the galery.
     */
    public Gallery(int length, int width) {
        Canvas galeria = Canvas.getCanvas("Galeria", length + 100, width);
        this.length = length;
        this.width = width;
        galeria.redraw1();
    }

    /**
     * Let me create a gallery whit the indiations of the problem
     * 
     * @param polygon   Is a matrix with all vertices of the room
     * @param guard     Is the positions of the guard.
     * @param sculpture Is the positions of the sculpture
     */
    public Gallery(int[][] polygon, int[] guard, int[] sculpture) {
        Canvas galeria = Canvas.getCanvas("Galeria", 400, 300);
        this.length = 300;
        this.width = 300;
        galeria.redraw1();
        confirm = true;
        problem = false;
        try {
            buildRoom("black", polygon);
            Room sala = rooms.get("black");
            makeVisible();
            sala.arrivedGuard();
            sala.moveGuard(guard[0], guard[1]);
            sala.displaySculpture(sculpture[0], sculpture[1]);
        } catch (Exception e) {
            exepcion = e.getMessage();
            confirm = false;
        }
    }

    /**
     * Let me create a room.
     * 
     * @param color   the color of the room's wall.
     * @param polygon the position of the vertices of the polygon.
     * @throws GalleryException
     */
    public void buildRoom(String color, int[][] polygon) {
        confirm = true;
        try {
            if (problem || rooms.size() == 0) {
                if (!rooms.containsKey(color)) {
                    // intersect(polygon);
                    if (confirm) {
                        Room room = new Room(color, polygon, width);
                        rooms.put(color, room);
                        rooms.get(color).alarm(rooms.size(), length);
                    } else {
                        throw new GalleryException(GalleryException.IntersectRoom);
                    }
                } else {
                    throw new GalleryException(GalleryException.RoomExist);
                }
            } else {
                throw new GalleryException(GalleryException.Problem);
            }
        } catch (GalleryException e) {
            exepcion = e.getMessage();
            confirm = false;
        }
    }

    /**
     * Let me put a sculpture in a room.
     * 
     * @param room the color of the room.
     * @param x    the x's position of the sculpture.
     * @param y    the y's position of the sculpture.
     */
    public void displaySculpture(String room, int x, int y) {
        confirm = true;
        try {
            if (rooms.containsKey(room)) {
                rooms.get(room).displaySculpture(x, y);
            } else {
                throw new GalleryException(GalleryException.RoomNotExist);
            }
        } catch (GalleryException e) {
            exepcion = e.getMessage();
            confirm = false;
        }
    }

    /**
     * Let me create a guard in a specific room.
     * 
     * @param room The name of the room.
     */
    public void arriveGuard(String room) {
        confirm = true;
        try {
            if (rooms.containsKey(room)) {
                rooms.get(room).arrivedGuard();
            } else {
                throw new GalleryException(GalleryException.RoomNotExist);
            }
        } catch (GalleryException e) {
            exepcion = e.getMessage();
            confirm = false;
        }
    }

    /**
     * Let me move a guard inside of the room.
     * 
     * @param room The name of the room.
     * @param x    The coorden x.
     * @param y    The coorden y.
     */
    public void moveGuard(String room, int x, int y) {
        confirm = true;
        try {
            if (rooms.containsKey(room)) {
                rooms.get(room).moveGuard(x, y);
            } else {
                throw new GalleryException(GalleryException.RoomNotExist);
            }
        } catch (GalleryException e) {
            exepcion = e.getMessage();
            confirm = false;
        }
    }

    /**
     * Let's create the room's alarm.
     * 
     * @param room The name of the room.
     * @param on   The alarm state.
     */
    public void alarm(String room, boolean on) {
        confirm = true;
        try {
            if (rooms.containsKey(room)) {
                rooms.get(room).alarm(on);
            } else {
                confirm = false;
                throw new GalleryException(GalleryException.RoomNotExist);
            }
        } catch (GalleryException e) {
            exepcion = e.getMessage();
            confirm = false;
        }
    }

    /**
     * Steal the room's sculpture if the guard doesn't see it.
     */
    public void steal() {
        confirm = true;
        try {
            for (String r : rooms.keySet()) {
                rooms.get(r).steal();
            }
        } catch (GalleryException e) {
            exepcion = e.getMessage();
            confirm = false;
        }
    }

    /**
     * 
     * Returns an array of strings containing the names of all the rooms in the
     * house.
     * The rooms are sorted alphabetically.
     * 
     * @return an array of strings with the names of all the rooms in the house
     */
    public String[] rooms() {
        String[] salas = new String[rooms.size()];
        int contador = 0;
        for (String i : rooms.keySet()) {
            salas[contador] = i;
            contador++;
        }
        Arrays.sort(salas);
        return salas;
    }

    /**
     * 
     * Returns an array of the names of the rooms that currently have the alarm
     * turned off.
     * 
     * @return an array of the names of the rooms that currently have the alarm
     *         turned off
     */
    public String[] roomsOnAlert() {
        ArrayList<String> roomsOnAlert = new ArrayList<String>();
        for (String room : rooms.keySet()) {
            if (rooms.get(room).alarmTurnOf()) {
                roomsOnAlert.add(room);
            }
        }
        String[] room = new String[roomsOnAlert.size()];
        for (int i = 0; i < roomsOnAlert.size(); i++) {
            room[i] = roomsOnAlert.get(i);
        }
        Arrays.sort(room);
        return room;
    }

    /**
     * Returns the location of the guard in the specified room. If the room exists
     * in the gallery,
     * it returns an array with two integers representing the x and y coordinates of
     * the guard.
     * If the room does not exist, it throws a GalleryException with a message
     * indicating so.
     *
     * @param room The room to look for the guard in.
     * @return An array with two integers representing the x and y coordinates of
     *         the guard.
     * @throws GalleryException If the specified room does not exist in the gallery.
     */
    public int[] guardLocation(String room) {
        confirm = true;
        int[] location = { 0 };
        try {
            if (rooms.containsKey(room)) {
                location = rooms.get(room).guardLocation();
            } else {
                throw new GalleryException(GalleryException.RoomNotExist);
            }
        } catch (GalleryException e) {
            exepcion = e.getMessage();
            confirm = false;
        }
        return location;
    }

    /**
     * Returns the location of the sculpture in the specified room.
     * 
     * @param room the name of the room where the sculpture is located
     * @return an integer array representing the (x, y) coordinates of the sculpture
     * @throws GalleryException if the specified room does not exist in the gallery
     */
    public int[] sculptureLocation(String room) {
        confirm = true;
        int[] location = { 0 };
        try {
            if (rooms.containsKey(room)) {
                location = rooms.get(room).sculptureLocation();
            } else {
                throw new GalleryException(GalleryException.RoomNotExist);
            }
        } catch (GalleryException e) {
            exepcion = e.getMessage();
            confirm = false;
        }
        return location;
    }

    /**
     * 
     * Returns the total distance traveled by the guard in the specified room.
     * 
     * @param room the name of the room where the distance is being calculated.
     * @return the total distance traveled by the guard in the specified room.
     * @throws GalleryException if the specified room does not exist in the gallery.
     */
    public float distanceTraveled(String room) {
        float distance = 0;
        confirm = true;
        try {
            if (rooms.containsKey(room)) {
                distance = rooms.get(room).distanceTraveled();
                BigDecimal numero = BigDecimal.valueOf(distance);
                numero = numero.setScale(6, RoundingMode.HALF_UP);

                distance = numero.floatValue();
                System.out.println(distance);
            } else {
                throw new GalleryException(GalleryException.RoomNotExist);
            }
        } catch (GalleryException e) {
            exepcion = e.getMessage();
            confirm = false;
        }
        return distance;
    }

    /**
     * Checks whether there is a sculpture in the specified room.
     *
     * @param room the name of the room to check
     * @return true if the room has a sculpture, false otherwise
     * @throws GalleryException if the specified room does not exist
     */
    public boolean sculpturePresent(String room) {
        boolean hasSculpture = true;
        try {
            if (rooms.containsKey(room)) {
                hasSculpture = rooms.get(room).hasSculpture();
            } else {
                throw new GalleryException(GalleryException.RoomNotExist);
            }
        } catch (GalleryException e) {
            exepcion = e.getMessage();
            confirm = false;
        }
        return hasSculpture;
    }

    /**
     * validate if the alarm was activated without having stolen the sculpture
     */
    public String[] roomsWithFalseAlarm() {
        List<String> auxiliar = new ArrayList<String>();
        for (String i : rooms.keySet()) {
            if (rooms.get(i).falseAlarm()) {
                auxiliar.add(i);
            }
        }
        String[] falseAlarm = new String[auxiliar.size()];
        for (int i = 0; i < auxiliar.size(); i++) {
            falseAlarm[i] = auxiliar.get(i);
        }
        return falseAlarm;
    }

    /**
     * Returns an array with the names of all the rooms in the gallery that have had
     * a false alarm triggered.
     *
     * @return String[] - Array with the names of all the rooms with a false alarm.
     */
    public void makeVisible() {
        for (String i : rooms.keySet()) {
            rooms.get(i).makeVisible();
        }
    }

    /**
     * Let me make visible the rooms on the gallery
     */
    public void makeInvisible() {
        for (String i : rooms.keySet()) {
            rooms.get(i).makeInvisible();
        }
    }

    /**
     * 
     * Checks if the given polygon intersects with any of the rooms in the gallery.
     * Throws a GalleryException with code "IntersectRoom" if an intersection is
     * found.
     * 
     * @param polygon a 2D array representing the vertices of the polygon to check
     *                for intersection.
     */
    public void intersect(int[][] polygon) {
        try {
            for (String r : rooms.keySet()) {
                if (rooms.get(r).intersect(polygon)) {
                    throw new GalleryException(GalleryException.IntersectRoom);
                }
            }
        } catch (GalleryException e) {
            exepcion = e.getMessage();
            confirm = false;
        }
    }

    /**
     * 
     * Returns an array of Strings with the names of all the rooms in the gallery.
     * The array is sorted in lexicographical order.
     * 
     * @return an array of Strings with the names of all the rooms in the gallery
     */
    public boolean ok() {
        if (exepcion != "ok") {
            System.out.println(exepcion);
            exepcion = "ok";
        }
        return confirm;
    }

    /**
     * Finish the simulator
     */
    public void finish() {
        System.exit(0);
    }

    /**
     * 
     * Returns the Room object associated with the specified name.
     * 
     * @param name the name of the room to retrieve
     * @return the Room object associated with the specified name
     * @throws GalleryException if the room does not exist in the gallery
     */
    public Room getRoom(String name) {
        try {
            if (!rooms.containsKey(name)) {
                throw new GalleryException(GalleryException.RoomNotExist);
            }
        } catch (GalleryException e) {
            exepcion = e.getMessage();
            confirm = false;
        }
        return rooms.get(name);
    }

    /**
     * 
     * Returns a matrix with the coordinates of the vertices of the walls of the
     * specified room.
     * 
     * @param room the name of the room
     * @return a matrix with the coordinates of the vertices of the walls of the
     *         specified room
     * @throws GalleryException if the room does not exist in the gallery
     */
    public int[][] getVertices(String room) {
        int[][] matrix = new int[0][0];
        confirm = true;
        try {
            if (rooms.containsKey(room)) {
                matrix = rooms.get(room).getWalls();
            } else {
                throw new GalleryException(GalleryException.RoomNotExist);
            }
        } catch (GalleryException e) {
            exepcion = e.getMessage();
            confirm = false;
        }
        return matrix;
    }
}