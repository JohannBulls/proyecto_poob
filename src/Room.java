import java.util.ArrayList;
import java.util.List;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.lang.Math;

/**
 * Let me create an interate a room.
 *
 * @author Sebastian Zamora
 * @author Johann Amaya
 * @version 1.2
 */
public class Room
{
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
    private List<int[]> posiciones  = new ArrayList<>();
    private Wall[] repMovimiento;
    /**
     * Constructor for objects of class Room
     * @throw GalleryException
     */
    public Room(String color,int[][] polygon,int length) throws GalleryException
    {
        walls = polygon;
        this.color = color;
        this.length =length;
        lineas = buildWalls(walls);
        //boolean couldCreate = couldCreateRoom(walls);
        poligono = createPolygon(walls);
        /*if(!couldCreate){
            throw new GalleryException(GalleryException.CouldNotCreateRoom);
        }*/
    }

    /**
     * Let me make the walls of the rooms
     * @param  polygon the matrix with all vertices of the room
     * @return  the matirx of the walls
     */
    private Wall[] buildWalls(int[][] polygon)
    {
        Wall[] muros = new Wall[polygon.length];
        for(int i=1;i<polygon.length;i++){
            Wall line = new Wall(walls[i-1][0],length-walls[i-1][1],walls[i][0],length-walls[i][1]);
            muros[i] = line;
        }
        Wall line = new Wall(walls[0][0],length-walls[0][1],walls[walls.length-1][0],length-walls[walls.length-1][1]);
        muros[0] = line;
        return muros;
    }
    
    /**
     * Let me show the walls
     */
    public void makeVisible(){
        isVisible = true;
        if(isVisible){
            for(Wall i: lineas){
                i.draw(color);
            }
        }
        if(guardia != null){
            guardia.makeVisible();
        }
        if(escultura != null){
            escultura.makeVisible();
        }
    }
    
    /**
     * Let me erase the view of the walls
     */
    public void makeInvisible(){
        isVisible = false;
        if(!isVisible){
            for(Wall i: lineas){
                i.erase();
            }
        }
        if(guardia != null){
            guardia.makeInvisible();
        }
        if(escultura != null){
            escultura.makeInvisible();
        }
    }
    
    /**
     * Let me put a sculpture in a room.
     * @param x the x's position of the sculpture.
     * @param y the y's position of the sculpture.
     */
    public void displaySculpture(int x,int y)throws GalleryException{
        if(escultura == null){
            if(poligono.contains(x,y)){
                escultura = new Sculpture(color,x,y,length);
                escultura.makeVisible();
            }else{
                throw new GalleryException(GalleryException.OutOfTheRoom);
            }
        }else{
            throw new GalleryException(GalleryException.RoomHasSculpture);
        }
    }
    
    /**
     * let me create a polygon with the structure of the room
     * @param walls the list of the points of the polygon.
     * @return The new polygon.
     */
    private Polygon createPolygon(int[][] walls){
        int[] cordx = new int[walls.length];
        int[] cordy = new int[walls.length];
        for(int i = 0;i<walls.length;i++){
            cordx[i] = walls[i][0];
            cordy[i] = walls[i][1];
        }
        return new Polygon(cordx,cordy,walls.length);
    }
    
    /**
     * Let me create a guard on the room
     */
    public void arrivedGuard() throws GalleryException{
        try{
            if(guardia == null){
                guardia = new Guard(color);
                int[] posiciones = toSouth();
                //{{0,0},{20,0},{20,30},{60,30},{60,0},{80,0},{80,50},{0,50}}
                moveGuard(posiciones[1],posiciones[0],false);
                makeVisible();
            }else{
                throw new GalleryException(GalleryException.RoomHasGuard);
            }
        }catch(GalleryException e){
            throw new GalleryException(GalleryException.OutOfTheRoom);
        }
    }
    
    /**
     * Let me move the guard arround the room
     * @param x The x position.
     * @param y The y position.
     */
    public void moveGuard(int x,int y,boolean isThere) throws GalleryException{
        if(poligono.contains(x,y)){
            if(isThere){
                guardia.moveGuard(x,length-y);
            }else{
                guardia.moveGuard(x,length-y-5);
            }
        }
        else{
            throw new GalleryException(GalleryException.OutOfTheRoom);
        }
    }
    
    /**
     * Create the alarm of the room
     */
    public void alarm(int cantidadRooms,int length){
        alarma = new Alarm(cantidadRooms,length);
        repSala = new Rectangle();
        repSala.changeColor(color);
        repSala.changeSize(20,20);
        repSala.moveHorizontal(-70);
        repSala.moveHorizontal(length);
        repSala.moveVertical(-15);
        repSala.moveVertical(50*(cantidadRooms-1));
        repSala.makeVisible();
        alarma.makeVisible();
    }
    
    /**
     * Let me ubique the guard in the most southes position.
     * @return The position is most south.
     */
    private int[] toSouth(){
        int[] r = new int[2];
        r[0] = walls[0][1];
        r[1] =walls[0][0];
        for (int i=0; i<walls.length;i++){
            if(walls[i][1]<r[0]){
                r[0]= walls[i][1];
                r[1] =walls[i][0];
            }
        }
        for (int j=0; j<walls.length;j++){
            if (walls[j][1] == r[0] && walls[j][0]<r[1]){
                r[1] =walls[j][0];
            }
        }    
        return r;
    }
    
    /**
     * Activate or deactivate the alarm 
     */
    public void alarm(boolean on) throws GalleryException{
        try{
            alarma.turn(on);
        }catch(GalleryException e){
            throw new GalleryException(GalleryException.AlarmNotChange);
        }
    }
    
    /**
     * Let me know if the alarm is turn of
     * @return if the alarm is active or not.
     */
    public boolean alarmTurnOf(){
        return alarma.state();
    }
    
    /**
     * Return the guard's Positions
     * @throw GalleryException.
     * @return The guard's postions.
     */
    public int[] guardLocation()throws GalleryException{
        int[] location;
        if(guardia != null){
            location = guardia.location();
        }else{
            throw new GalleryException(GalleryException.RoomHasNotGuard);
        }
        return location;
    }
    
    /**
     * Return the sculpture's Positions
     * @throw GalleryException
     * @return The sculpture's postions.
     */
    public int[] sculptureLocation() throws GalleryException{
        int[] location;
        if(escultura != null){
            location = escultura.location();
        }else{
            throw new GalleryException(GalleryException.RoomHasNotSculpture);
        }
        return location;
    }
    
    /**
     * Let me kwon if the new room intersect.
     * @param polygon the position of the vertices of the polygon.
     * @throws GalleryException.
     * @return if the room cross with other.
     */
    public boolean intersect(int[][] polygon){
        Wall[] muros = buildWalls(polygon);
        boolean intersect = false;
        for(Wall u:lineas){
            for(Wall i:muros){
                if(u.getLine().intersectsLine(i.getLine())){
                    intersect = true;
                    break;
                }
            }
        }
        return intersect;
    }
    
    /**
     * Check if the room have an apropiate form.
     * @param polygon the position of the vertices of the polygon.
     * @throws GalleryException
     * @return if I could create the room or not.
     */
    public boolean couldCreateRoom(int[][] polygon) throws GalleryException{
        boolean intersect = true;
        int contador = 0;
        for(Wall u:lineas){
            for(Wall i:lineas){
                if(u.getLine().intersectsLine(i.getLine())){
                    if(contador > 1){
                        intersect = false;
                    }
                    contador++;
                }
            }
        }
        return intersect;
    }
    
    /**
     * Let me know if it is possible to steal the sculpture.
     * @throws GalleryException
     */
    public void steal() throws GalleryException{
        if(escultura != null){
            boolean guarded = guardSeeTheSculpture();
            if(!guarded){
                escultura.makeInvisible();
                escultura = null;
            }
        }else{
            throw new GalleryException(GalleryException.RoomHasNotSculpture);
        }
    }
    
    /**
     * Let me know if the guard could see the sculpture.
     * @return if the guard could see the sculpture.
     */
    private boolean guardSeeTheSculpture(){
        int[] posGuardia = guardia.location();
        int[] posEscultura = escultura.location();
        return poligono.contains(posGuardia[0],posGuardia[1],posEscultura[0],posEscultura[1]);
    }
    
    /**
     * Check if it has sculpture.
     * @return If the room has sculpture or not.
     */
    public boolean hasSculpture(){
        boolean hasSculpture = true;
        if(escultura == null){
            hasSculpture = false;
        }
        return hasSculpture;
    }
    
    /**
     * validate if the alarm was activated without having stolen the sculpture.
     * @return If the alarm went on without meaning. 
     */
    public boolean falseAlarm(){
        boolean falseAlarm = false;
        if(alarma.state() && escultura != null){
            falseAlarm = true;
        }
        return falseAlarm;
    }
    
    /**
     * Let me know the distance traveled for the guard to be able to see the sculpture.
     * @throw GalleryException
     * @return The distance that guard walk to see the sculpture.
     */
    public float distanceTraveled() throws GalleryException{
        float distancia =0;
        if(!guardSeeTheSculpture()){
            moveToSeeTheSculpture();
            for(int i =1;i<posiciones.size();i++){
                distancia += calculate(posiciones.get(i-1),posiciones.get(i));
            }
        }else{
            throw new GalleryException(GalleryException.GuardNotMove);
        }
        return distancia;
    }
    
    /**
     * Move the guard for he could see the sculpture.
     */
    private void moveToSeeTheSculpture(){
    
    }
    
    /**
     * calculate the length of the line
     * @return the distance into two points into the room.
     */
    private float calculate(int[] punto1,int[] punto2){
        float distance =(float) Math.sqrt(((punto2[0]-punto1[0])*(punto2[0]-punto1[0]))+((punto2[1]-punto1[1])*(punto2[1]-punto1[1])));
        return distance;
    }
}
