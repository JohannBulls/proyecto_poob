import java.util.HashMap;
import java.util.Arrays;

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
    private String exepcion;
    private boolean confirm;
    private boolean problem;
    /**
     * Constructor for objects of class Galery.
     * @author length The length of the galery.
     * @author width The width of the galery.
     */
    public Gallery(int length,int width)
    {
        Canvas galeria = Canvas.getCanvas("Galeria",length,width);
        this.length = length;
        problem = false;
        galeria.redraw1();
    }
    
    /**
     * Let me create a gallery whit the indiations of the problem
     * @Param polygon Is a matrix with all vertices of the room
     * @Param guard Is the positions of the guard.
     * @Param sculpture Is the positions of the sculpture
     */
    public Gallery(int[][] polygon, int[] guard, int[] sculpture){
        new Gallery(680,1300);
        confirm = true;
        try{
            buildRoom("black",polygon);
            problem = true;
            Room sala = rooms.get("black");
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
     * @throws 
     */
    public void buildRoom(String color, int[][] polygon) throws GalleryExecption{
        confirm = true;
        try{
            if(!problem){
                if(!rooms.containsKey(color)){
                    Room room = new Room(color,polygon,length);
                    rooms.put(color,room);
                }else{
                    throw new GalleryExecption(GalleryExecption.RoomExist);
                }
            }else{
                throw new GalleryExecption(GalleryExecption.Problem);
            }
        }catch(GalleryExecption e){
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
                throw new GalleryExecption(GalleryExecption.RoomNotExist);
            }
        }catch(GalleryExecption e){
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
                throw new GalleryExecption(GalleryExecption.RoomNotExist);
            }
        }catch(GalleryExecption e){
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
        }catch(GalleryExecption e){
            exepcion = e.getMessage();
            confirm = false;
        }
    }
    
    /**
     * Let's create the room's alarm.
     * @Param room The name of the room.
     * @Param on The alarm state.
     */
    public void alarm(String room, boolean on){
    
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
    /*
    public String[] roomsOnAlert(){
    
    }
    
    public int[] guardLocation(String room){
    
    }  
    
    public int[] sculptureLocation(String room){
    
    }*/
    
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

