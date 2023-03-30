
/**
 * Let me simulate and solve the problem
 * @author Sebastian Zamora
 * @author Johann Amaya
 * @version 1.0
 */
public class Solve
{
    private Gallery galeria;
    private int[][] vertices;
    /**
     * Calculate the shortest distance that moves the guard to see the sculpture.
     */
    public float solve(int[][] polygon, int[] guard, int[] sculpture){
        galeria = new Gallery(polygon,guard,sculpture);
        vertices = galeria.getVertices("black");
        solution();
        return galeria.distanceTraveled("black");
    }

    /**
     * Make the movements to the guard to see the sculpture
     */
    public void solution(){

    }
}
