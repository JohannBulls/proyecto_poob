
import java.awt.geom.Line2D;

/**
 * Let me create and modify Intersections.
 * 
 * @author Sebastian Zamora
 * @author Johann Amaya
 * @version 1.2
 */
public class Wall {
    private Line2D line;
    private Canvas canvas;
    private double[] posiciones = new double[4];

    /**
     * Constructs a new Wall object with two specified endpoints (X1, Y1) and (X2,
     * Y2).
     *
     * @param X1 the x-coordinate of the first endpoint
     * @param Y1 the y-coordinate of the first endpoint
     * @param X2 the x-coordinate of the second endpoint
     * @param Y2 the y-coordinate of the second endpoint
     */
    public Wall(double X1, double Y1, double X2, double Y2) {
        line = new Line2D.Double(X1, Y1, X2, Y2);
        posiciones[0] = X1;
        posiciones[1] = Y1;
        posiciones[2] = X2;
        posiciones[3] = Y2;
        canvas = Canvas.getCanvas();
    }

    /**
     * 
     * Draws the wall with the specified color on the canvas.
     * 
     * @param color The color in which to draw the wall.
     */
    public void draw(String color) {
        canvas.draw(this, color, line);
    }

    /**
     * Erase a line in canvas
     * @ the line's color
     */
    public void erase() {
        canvas.erase(this);
    }

    /**
     * Return the line
     * 
     * @Param linea The other line
     * @return the line.
     */
    public Line2D getLine() {
        return line;
    }

    /**
     * Return the points of start and finish of the line.
     * 
     * @return an int list with the points of the line.
     */
    public double[] getPosiciones() {
        return posiciones;
    }

    /**
     * Verify if it intersect with other wall
     * 
     * @Param wall The other wall
     */
    public boolean intersect(Wall lines) {
        double[] posicion = lines.getPosiciones();
        return line.intersectsLine(posicion[0], posicion[1], posicion[2], posicion[3]);
    }

    /**
     * 
     */
    public boolean intersects(Wall[] lines) {
        boolean flag = false;
        for (Wall i : lines) {
            if (intersect(i)) {
                flag = true;
            }
        }
        return flag;
    }
}
