
/**
 * Me permite crear un guardia
 *
 * @author Sebastian Zamora
 * @author Johann Amaya
 * @version 1.0
 */
public class Guard
{
    private Rectangle guardia;
    private int xPos;
    private int yPos;
    /**
     * Constructor for objects of class Guard
     */
    public Guard(String color)
    {
        guardia = new Rectangle();
        guardia.moveHorizontal(-70);
        guardia.moveVertical(-15);
        guardia.changeColor(color);
        guardia.changeSize(5,5);
    }

    /**
     * 
     */
    public void moveGuard(int x,int y)
    {
        guardia.moveHorizontal(x);
        guardia.moveVertical(y);
    }
    
    /**
     * Let me make visible the rooms on the Guard
     */
    public void makeVisible(){
        guardia.makeVisible();
    }
    
    /**
     * Let me make visible the rooms on the Guard
     */
    public void makeInvisible(){
        guardia.makeInvisible();
    }
    
    /**
     * Let me change the color of the guard.
     * @Param color the color of the room.
     */
    public void changeColor(String color){
        guardia.changeColor(color);
    }
}
