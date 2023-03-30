
/**
 * Let me simulate and solve the problem
 * 
 * @author Sebastian Zamora
 * @author Johann Amaya
 * @version 1.0
 */
public class Solve {
    private Gallery galeria;
    private int[][] vertices;

    /**
     * 
     * Solves the art gallery problem for a given polygon, guard position, and
     * sculpture position
     * 
     * @param polygon   an array of integer arrays representing the vertices of the
     *                  polygon in clockwise order
     * @param guard     an integer array representing the position of the guard
     * @param sculpture an integer array representing the position of the sculpture
     * @return a float representing the distance the guard traveled to patrol the
     *         gallery
     */
    public float solve(int[][] polygon, int[] guard, int[] sculpture) {
        galeria = new Gallery(polygon, guard, sculpture);
        vertices = galeria.getVertices("black");
        solution();
        return galeria.distanceTraveled("black");
    }

    /**
     * Make the movements to the guard to see the sculpture
     */
    public void solution() {

    }
}
