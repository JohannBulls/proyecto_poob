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
     * @param width The width of the galery.
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
     * @param polygon Is a matrix with all vertices of the room
     * @param guard Is the positions of the guard.
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
                    //intersect(polygon);
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
     * @param x The coorden x.
     * @param y The coorden y.
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
     * @param on The alarm state.
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
     * Let me kwon the rooms that I have on the gallery
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
     * Let me know which the rooms tha it's alarms are turned on
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
     * Let me know the guard's position of the room.
     * 
     * @param room the room's color.
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
     * Let me know the sculpture's position of the room.
     * 
     * @param room the room's color.
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
     * Let me know the distance traveled for the guard to be able to see the
     * sculpture.
     * 
     * @param room The room's sculpture.
     * @return the distance that 
     */
    public float distanceTraveled(String room) {
        float distance = 0;
        confirm = true;
        try {
            if (rooms.containsKey(room)) {
                distance = rooms.get(room).distanceTraveled();
                BigDecimal numero = BigDecimal.valueOf(distance);
                numero =numero.setScale(6,RoundingMode.HALF_UP);
                
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
     * Let me know if the room has an sculpture
     * 
     * @param room The name of the room
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
     * Let me make visible the rooms on the gallery
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
     * Lets me know if it intersects with another room
     * 
     * @param polygon the position of the vertices of the polygon.
     */
    public void intersect(int[][] polygon) {
        try {
            for(String r : rooms.keySet()) {
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
     * Let me verify if the last method end successfully
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
     * Let me get a room
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
     * Get the vertices of the room.
     * @param the name of the room.
     */
    public int[][] getVertices(String room){
        int[][] matrix = new int[0][0];
        confirm = true;
        try {
            if (rooms.containsKey(room)) {
                matrix= rooms.get(room).getWalls();
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