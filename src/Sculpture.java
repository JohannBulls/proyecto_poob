
/**
 * Write a description of class Sculpture here.
 *
 * @author Sebastian Zamora
 * @author Johann Amaya
 * @version 1.0
 */
public class Sculpture
{
    private Circle circle;
    private static final int size=10;
    private int x,y;
    private int length;
    /**
     * Constructor for objects of class Sculpture
     */
    public Sculpture(String color,int x,int y,int length)
    {
         circle = new Circle();
         circle.changeColor(color);
         circle.changeSize(size);
         this.x=x;
         this.y=y;
         this.length=length;
         circle.moveVertical(length-y-15);
         circle.moveHorizontal(x-20);
    }

    /**
     * Let me make visible the rooms on the Sculpture
     */
    public void makeVisible(){
        circle.makeVisible();
    }
    
    /**
     * Let me make visible the rooms on the Sculpture
     */
    public void makeInvisible(){
        circle.makeInvisible();
    }
    
    /**
     * Let me change the color of the sculpture.
     * @Param color the color of the room.
     */
    public void changeColor(String color){
        circle.changeColor(color);
    }
    
    /**
     * Return its position
     */
    public int[] location(){
        int[] posiciones ={x,y};
        return posiciones;
    }
}
