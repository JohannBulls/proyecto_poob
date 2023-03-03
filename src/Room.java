import java.util.ArrayList;
import java.util.List;
import java.awt.Polygon;

/**
 * Write a description of class Room here.
 *
 * @author Sebastian Zamora
 * @author Johann Amaya
 * @version 1.0
 */
public class Room
{
    private int[][] walls;
    private boolean isVisible = false;
    private List<Wall> lines = new ArrayList<>();
    private String color;
    private Guard guardia;
    private int length;
    private Polygon poligono;
    private Sculpture escultura;
    /**
     * Constructor for objects of class Room
     */
    public Room(String color,int[][] polygon,int length)
    {
        walls = polygon;
        this.color = color;
        this.length =length;
        buildWalls(walls);
        createPolygon(walls);
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    private void buildWalls(int[][] polygon)
    {
        for(int i=1;i<polygon.length;i++){
            Wall line = new Wall(walls[i-1][0],length-walls[i-1][1],walls[i][0],length-walls[i][1]);
            lines.add(line);
        }
        Wall line = new Wall(walls[0][0],length-walls[0][1],walls[walls.length-1][0],length-walls[walls.length-1][1]);
        lines.add(line);
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
    public void displaySculpture(int x,int y)throws GalleryExecption{
        if(escultura == null){
            if(poligono.contains(x,y)){
                escultura = new Sculpture(color,x,y,length);
                escultura.makeVisible();
            }else{
                throw new GalleryExecption(GalleryExecption.OutOfTheRoom);
            }
        }else{
            throw new GalleryExecption(GalleryExecption.RoomHasSculpture);
        }
    }
    
    /**
     * let me create a polygon with the structure of the room
     * @param walls the list of the points of the polygon
     */
    private void createPolygon(int[][] walls){
        int[] cordx = new int[walls.length];
        int[] cordy = new int[walls.length];
        for(int i = 0;i<walls.length;i++){
            cordx[i] = walls[i][0];
            cordy[i] = walls[i][1];
        }
        poligono = new Polygon(cordx,cordy,walls.length);
    }
    
    /**
     * Let me create a guard on the room
     */
    public void arrivedGuard() throws GalleryExecption{
        if(guardia == null){
            guardia = new Guard(color);
            guardia.makeInvisible();
            guardia.makeVisible();
            moveGuard(walls[0][0],walls[0][1]);
        }else{
            throw new GalleryExecption(GalleryExecption.RoomHasGuard);
        }
    }
    
    /**
     * Let me move the guard arround the room
     * @param x The x position.
     * @param y The y position.
     */
    public void moveGuard(int x,int y) throws GalleryExecption{
        if(poligono.contains(x,y)){
            guardia.moveGuard(x+15,length-y-15);
            makeVisible();
        }else{
            throw new GalleryExecption(GalleryExecption.OutOfTheRoom);
        }
    }
}
