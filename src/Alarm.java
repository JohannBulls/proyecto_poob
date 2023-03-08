
/**
 * Write a description of class Alam here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Alarm
{
    // instance variables - replace the example below with your own
    private boolean on;
    private Circle alarma;
    /**
     * Constructor for objects of class Alam
     */
    public Alarm(int cantidadRooms,int length)
    {
        alarma = new Circle();
        on = false;
        alarma.changeSize(20);
        alarma.changeColor("black");
        alarma.moveHorizontal(-20);
        alarma.moveHorizontal(length+40);
        alarma.moveVertical(-15);
        alarma.moveVertical(50*(cantidadRooms-1));
    }
    
    public void turn(boolean stade){
        if(stade != on){
            on = stade;
            if(stade){
                alarma.changeColor("red");
            }else{
                alarma.changeColor("black");
            }
        }
    }
    
    public void makeVisible(){
        alarma.makeVisible();
    }
    
    public void makeInvisible(){
        alarma.makeInvisible();
    }
    
    /**
     * Returns the alarm status
     */
    public boolean state(){
        return on;
    }
}
