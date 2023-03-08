import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * Let me create a galery
 *
 * @author Sebastian Zamora
 * @author Johann Amaya
 * @version 1.3
 */
public class Gallery
{
    private HashMap<String,Room> rooms = new HashMap();
    private int length;
    private int width;
    private String exepcion;
    private boolean confirm;
    private boolean problem = true;
    /**
     * Constructor for objects of class Galery.
     * @Param length The length of the galery.
     * @Param width The width of the galery.
     */
    public Gallery(int length,int width)
    {
        Canvas galeria = Canvas.getCanvas("Galeria",length+100,width);
        this.length = length;
        this.width = width;
        galeria.redraw1();
    }
    
    /**
     * Let me create a gallery whit the indiations of the problem
     * @Param polygon Is a matrix with all vertices of the room
     * @Param guard Is the positions of the guard.
     * @Param sculpture Is the positions of the sculpture
     */
    public Gallery(int[][] polygon, int[] guard, int[] sculpture){
        new Gallery(300,300);
        confirm = true;
        problem = false;
        try{
            buildRoom("black",polygon);
            Room sala = rooms.get("black");
            makeVisible();
            sala.arrivedGuard();
            sala.moveGuard(guard[0],guard[1]);
            sala.displaySculpture(sculpture[0],sculpture[1]);
        }catch(Exception e){
            exepcion = e.getMessage();
            confirm = false;
        }
    }
    
    /**
     * Let me create a room.
     * @param color the color of the room's wall.
     * @param polygon the position of the vertices of the polygon.
     * @throws GalleryException
     */
    public void buildRoom(String color, int[][] polygon) throws GalleryException{
        confirm = true;
        try{
            if(problem || rooms.size()==0){
                if(!rooms.containsKey(color)){
                    intersect(polygon);
                    if(confirm){
                        Room room = new Room(color,polygon,width);
                        rooms.put(color,room);
                        rooms.get(color).alarm(rooms.size(),length);
                    }else{
                        throw new GalleryException(GalleryException.IntersectRoom);  
                    }
                }else{
                    throw new GalleryException(GalleryException.RoomExist);
                }
            }else{
                throw new GalleryException(GalleryException.Problem);
            }
        }catch(GalleryException e){
            exepcion = e.getMessage();
            confirm = false;
        }
    } 
    
    /**
     * Let me put a sculpture in a room.
     * @param room the color of the room.
     * @param x the x's position of the sculpture.
     * @param y the y's position of the sculpture.
     */
    public void displaySculpture(String room,int x,int y){
        confirm = true;
        try{
            if(rooms.containsKey(room)){
                rooms.get(room).displaySculpture(x,y);
            }else{
                throw new GalleryException(GalleryException.RoomNotExist);
            }
        }catch(GalleryException e){
            exepcion = e.getMessage();
            confirm = false;
        }
    }
    
    /**
     * Let me create a guard in a specific room.
     * @Param room The name of the room.
     */
    public void arriveGuard(String room){
        confirm = true;
        try{
            if(rooms.containsKey(room)){
                rooms.get(room).arrivedGuard();
            }else{
                throw new GalleryException(GalleryException.RoomNotExist);
            }
        }catch(GalleryException e){
            exepcion = e.getMessage();
            confirm = false;
        }
    }
    
    /**
     * Let me move a guard inside of the room.
     * @Param room The name of the room.
     * @Param x The coorden x.
     * @Param y The coorden y.
     */
    public void moveGuard(String room, int x, int y){
        confirm = true;
        try{
            rooms.get(room).moveGuard(x,y);
        }catch(GalleryException e){
            exepcion = e.getMessage();
            confirm = false;
        }
    }
    
    /**
     * Let's create the room's alarm.
     * @Param room The name of the room.
     * @Param on The alarm state.
     * @throws GalleryException
     */
    public void alarm(String room, boolean on)throws GalleryException{
        confirm = true;
        if(rooms.containsKey(room)){
            rooms.get(room).alarm(on);
        }else{
            confirm = false;
            throw new GalleryException(GalleryException.RoomNotExist);
        }
    }
    
    /**
     * Steal the room's sculpture if the guard doesn't see it.
     */
    public void steal(){
        confirm = true;
        try{
            for(String i:rooms.keySet()){
                rooms.get(i).steal();
            }
        }catch(GalleryException e){
            exepcion = e.getMessage();
            confirm = false;
        }
    }
    
    /**
     * Let me kwon the rooms that I have on the gallery
     */
    public String[] rooms(){
        String[] salas = new String[rooms.size()];
        int contador =0;
        for(String i:rooms.keySet()){
            salas[contador] = i;
            contador++;
        }
        Arrays.sort(salas);
        return salas;
    }
    
    /**
     * Let me know which the rooms tha it's alarms are turned on
     */
    public String[] roomsOnAlert(){
        ArrayList<String> roomsOnAlert = new ArrayList<String>();
        for(String room: rooms.keySet()){
            if(rooms.get(room).alarmTurnOf()){
                roomsOnAlert.add(room);
            }  
        }
        String[] room = new String[roomsOnAlert.size()];
        for(int i=0;i<roomsOnAlert.size();i++){
            room[i] = roomsOnAlert.get(i);
        }
        return room;
    }
    
    /**
     * Let me know the guard's position of the room.
     * @Param room the room's color.
     */
    public int[] guardLocation(String room){
        return rooms.get(room).guardLocation();
    }  
    
    /**
     * Let me know the sculpture's position of the room.
     * @Param room the room's color.
     */
    public int[] sculptureLocation(String room){
        return rooms.get(room).sculptureLocation();
    }
    
    /**
     * Let me know if the room has an sculpture
     * @param room The name of the room
     */
    public boolean sculpturePresent(String room){
        boolean hasSculpture = true;
        for(String i: rooms.keySet()){    
            hasSculpture = rooms.get(i).hasSculpture();
        }
        return hasSculpture;
    }
    
    /**
     * validate if the alarm was activated without having stolen the sculpture 
     */
    public String[] roomsWithFalseAlarm(){
        
    }
    
    /**
     * Let me make visible the rooms on the gallery
     */
    public void makeVisible(){
        for(String i: rooms.keySet()){    
            rooms.get(i).makeVisible();
        }
    }
    
    /**
     * Let me make visible the rooms on the gallery
     */
    public void makeInvisible(){
        for(String i: rooms.keySet()){    
            rooms.get(i).makeInvisible();
        }
    }
    
    /**
     * Lets me know if it intersects with another room
     * @param polygon the position of the vertices of the polygon.
     * @throws GalleryException
     */
    private void intersect(int[][] polygon)throws GalleryException{
        try{
            for(String r: rooms.keySet()){
                if(rooms.get(r).intersect(polygon)){
                    throw new GalleryException(GalleryException.IntersectRoom);
                }
            }
        }catch(GalleryException e){
            exepcion = e.getMessage();
            confirm = false;
        }
    }
    
    /**
     * Let me verify if the last method end successfully
     */
    public boolean ok(){
        if (exepcion != "ok"){
            System.out.println(exepcion);
            exepcion = "ok";
        }
        return confirm;
    }

    /**
     * Finish the simulator
     */
    public void finish(){
        System.exit(0);
    }
}