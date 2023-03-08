import java.util.ArrayList;
import java.util.List;
import java.awt.Polygon;
import java.awt.geom.Line2D;

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
    private Wall[] lines;
    private String color;
    private Guard guardia;
    private int length;
    private int width;
    private Polygon poligono;
    private Sculpture escultura;
    private Alarm alarma;
    private Rectangle repSala;
    /**
     * Constructor for objects of class Room
     * @throw GalleryException
     */
    public Room(String color,int[][] polygon,int length) throws GalleryException
    {
        try{
            walls = polygon;
            this.color = color;
            this.length =length;
            lines = buildWalls(walls);
            couldCreateRoom(walls);
            createPolygon(walls);
            poligono = createPolygon(walls);
        }catch(GalleryException e){
            throw new GalleryException(GalleryException.CouldNotCreateRoom);
        }
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
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
            for(Wall i: lines){
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
            for(Wall i: lines){
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
     * @param walls the list of the points of the polygon
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
        if(guardia == null){
            guardia = new Guard(color);
            guardia.makeInvisible();
            guardia.makeVisible();
            int[] posiciones = toSouth();
            //{{0,0},{20,0},{20,30},{60,30},{60,0},{80,0},{80,50},{0,50}}
            moveGuard(posiciones[1],posiciones[0]+5);
        }else{
            throw new GalleryException(GalleryException.RoomHasGuard);
        }
    }
    
    /**
     * Let me move the guard arround the room
     * @param x The x position.
     * @param y The y position.
     */
    public void moveGuard(int x,int y) throws GalleryException{
        if(poligono.contains(x,y)){
            guardia.moveGuard(x+15,length-y-15);
            makeVisible();
        }else{
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
     * Let me ubique the guard in the most southes position
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
    public void alarm(boolean on){
        alarma.turn(on);
    }
    
    /**
     * Let me know if the alarm is turn of
     */
    public boolean alarmTurnOf(){
        return alarma.state();
    }
    
    /**
     * Return the guard's Positions
     */
    public int[] guardLocation(){
        return guardia.location();
    }
    
    /**
     * Return the sculpture's Positions
     */
    public int[] sculptureLocation(){
        return escultura.location();
    }
    
    /**
     * Let me kwon if the new room intersect.
     * @param polygon the position of the vertices of the polygon.
     * @throws GalleryException.
     */
    public boolean intersect(int[][] polygon){
        Wall[] muros = buildWalls(polygon);
        boolean intersect = false;
        for(Wall u:lines){
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
     */
    public void couldCreateRoom(int[][] polygon) throws GalleryException{
        boolean intersect = false;
        int contador = 0;
        for(Wall u:lines){
            for(Wall i:lines){
                if(u.getLine().intersectsLine(i.getLine())){
                    if(contador > 1){
                        intersect = true;
                        break;
                    }
                    contador++;
                }
            }
        }
        if(!intersect){
            throw new GalleryException(GalleryException.CouldNotCreateRoom);
        }
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
     */
    private boolean guardSeeTheSculpture(){
        int[] posGuardia = guardia.location();
        int[] posEscultura = escultura.location();
        return poligono.contains(posGuardia[0],posGuardia[1],posEscultura[0],posEscultura[1]);
    }
    
    /**
     * Check if it has sculpture. 
     */
    public boolean hasSculpture(){
        boolean hasSculpture = true;
        if(escultura == null){
            hasSculpture = false;
        }
        return hasSculpture;
    }
}
