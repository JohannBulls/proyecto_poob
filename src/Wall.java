
import java.awt.geom.Line2D;

/**
 * Let me create and modify Intersections.
 * 
 * @author Sebastian Zamora
 * @author Johann Amaya
 * @version 1.0
 */
public class Wall
{
    private Line2D line;
    /**
     * Constructor for objects of class Line
     */
    public Wall(double X1,double Y1,double X2,double Y2){
        line = new Line2D.Double(X1,Y1,X2,Y2);
    }

    /**
     * Draw a line in canvas
     * @ the line's color
     */
    public void draw(String color){
        Canvas canvas = Canvas.getCanvas();
        canvas.draw(this, color, line);
    }

    /**
     * Erase a line in canvas
     * @ the line's color
     */   
    public void erase(){
        Canvas canvas = Canvas.getCanvas();
        canvas.erase(this);
    }
    
    /**
     * Return the line
     * @Param linea The other line
     */
    public Line2D getLine(){
        return line;
    }
}
