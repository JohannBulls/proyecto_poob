import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

/**
 * Let me simulate and solve the problem
 * 
 * @author Sebastian Zamora
 * @author Johann Amaya
 * @version 2.0
 */
public class GalleryContest {
    private Gallery galeria;
    Wall[] lineas;
    private List<int[]> posiciones = new ArrayList<>();

    /**
     * 
     * Solves the art museum problem by computing the shortest path a guard must
     * take
     * to see the sculpture without being seen by any intruder.
     * 
     * @param polygon   a 2D array of integers that represents the vertices of the
     *                  polygon.
     * @param guard     an array of integers that represents the starting position
     *                  of the guard.
     * @param sculpture an array of integers that represents the position of the
     *                  sculpture.
     * @return a float value that represents the shortest distance traveled by the
     *         guard.
     */
    public float solve(int[][] polygon, int[] guard, int[] sculpture) {
        float distance = 0;
        buildlineas(polygon);
        if (guardSeeTheSculpture(guard, sculpture)) {
            solution(guard, sculpture, polygon);
        }
        distance = galeria.distanceTraveled("black");
        return distance;
    }

    /**
     * 
     * Simulates the movement of the guard through the art museum by moving the
     * guard to
     * different positions and computing the shortest distance traveled each time.
     * 
     * @param polygon   a 2D array of integers that represents the vertices of the
     *                  polygon.
     * @param guard     an array of integers that represents the starting position
     *                  of the guard.
     * @param sculpture an array of integers that represents the position of the
     *                  sculpture.
     * @return a float value that represents the shortest distance traveled by the
     *         guard during the simulation.
     */
    public float simulate(int[][] polygon, int[] guard, int[] sculpture) {
        float distance = solve(polygon, guard, sculpture);
        galeria = new Gallery(polygon, guard, sculpture);
        for (int[] i : posiciones) {
            galeria.moveGuard("black", i[0], i[1]);
        }
        return distance;
    }

    /**
     * 
     * Computes the solution to the art museum problem by determining the path the
     * guard must take
     * to see the sculpture without being seen by any intruder.
     * 
     * @param guardLocation     an array of integers that represents the starting
     *                          position of the guard.
     * @param sculptureLocation an array of integers that represents the position of
     *                          the sculpture.
     * @param vertices          a 2D array of integers that represents the vertices
     *                          of the polygon.
     */
    private void solution(int[] guardLocation, int[] sculptureLocation, int[][] vertices) {
        int[] sculpttureLineVision = verticeNearest(sculptureLocation, guardLocation, vertices);
        Wall guardVision = new Wall(sculpttureLineVision[0], sculpttureLineVision[1], guardLocation[0],
                guardLocation[1]);
        Wall sculptureVision = new Wall(sculpttureLineVision[0], sculpttureLineVision[1], sculptureLocation[0],
                sculptureLocation[1]);
        while (guardVision.intersect(sculptureVision)) {
            sculpttureLineVision = verticeNearest(guardLocation, sculptureLocation, vertices);
            int[] pos = { sculpttureLineVision[0], sculpttureLineVision[1] };
            posiciones.add(pos);
            guardVision = new Wall(sculpttureLineVision[0], sculpttureLineVision[1], 300 - guardLocation[0],
                    300 - guardLocation[1]);
        }
        float[] inter = interPosition(sculptureLocation, sculpttureLineVision, guardLocation);
        int[] result = { (int) inter[0], (int) inter[1] };
        posiciones.add(result);
    }

    /**
     * 
     * Finds the nearest vertice of a polygon from a given start point to a given
     * end point.
     * 
     * @param startPoint an array of integers that represents the starting point.
     * @param endPoint   an array of integers that represents the ending point.
     * @param vertices   a 2D array of integers that represents the vertices of the
     *                   polygon.
     * @return an array of integers that represents the nearest vertice to the start
     *         point.
     */
    private int[] verticeNearest(int[] startPoint, int[] endPoint, int[][] vertices) {
        int[] nearestPoint = { 0, 0 };
        double distance = Math.hypot(endPoint[0] - startPoint[0], endPoint[1] - startPoint[1]);
        for (int[] i : vertices) {
            double distanceToVertice = Math.hypot(i[0] - startPoint[0], i[1] - startPoint[1]);
            if (distanceToVertice < distance && galeria.getRoom("black").containsPoint(colocar(startPoint, i))) {
                nearestPoint = colocar(startPoint, i);
                distance = distanceToVertice;
            }
        }
        return nearestPoint;
    }

    /**
     * let me put the point that move the guard.
     * 
     * @param start  the chord coordinates of the start point.
     * @param finish the chord coordinates of the end point.
     * @return the chord coodinates of the point.
     */
    private int[] colocar(int[] start, int[] finish) {
        int[] point = { 0, 0 };
        if (start[0] < finish[0]) {
            point[0] = finish[0] + 1;
        } else {
            point[0] = finish[0] - 1;
        }
        if (start[1] < finish[1]) {
            point[1] = finish[1] + 1;
        } else {
            point[1] = finish[1] - 1;
        }
        return point;
    }

    /**
     * Let me make the walls of the rooms
     * 
     * @param polygon the matrix with all vertices of the room
     * @return the matirx of the walls
     */
    private void buildlineas(int[][] polygon) {
        lineas = new Wall[polygon.length];
        for (int i = 1; i < polygon.length; i++) {
            Wall line = new Wall(polygon[i - 1][0], 300 - polygon[i - 1][1], polygon[i][0], 300 - polygon[i][1]);
            lineas[i] = line;
        }
        Wall line = new Wall(polygon[0][0], 300 - polygon[0][1], polygon[polygon.length - 1][0],
                300 - polygon[polygon.length - 1][1]);
        lineas[0] = line;
    }

    /**
     * Let me know if the guard see the sculpture
     * 
     * @return if the guard see the sculpture
     */
    private boolean guardSeeTheSculpture(int[] posGuardia, int[] posEscultura) {
        Wall vista = new Wall(posGuardia[0], 300 - posGuardia[1], posEscultura[0], 300 - posEscultura[1]);
        return vista.intersects(lineas);
    }

    /**
     * 
     * Calculates the intersection point of two lines given their start and end
     * points.
     * 
     * @param esculIni an array of integers that represents the start point of the
     *                 sculpture line.
     * @param esculFin an array of integers that represents the end point of the
     *                 sculpture line.
     * @param guardIni an array of integers that represents the start point of the
     *                 guard's line of sight.
     * @return an array of floats that represents the intersection point of the two
     *         lines.
     */
    private float[] interPosition(int[] esculIni, int[] esculFin, int[] guardIni) {
        float m1 = (esculFin[1] - esculIni[1]) / (esculFin[0] - esculIni[0]);
        float m2 = (-1) / m1;

        float[][] matriz = { { m1, -1, (-(m1 * esculIni[0]) + esculIni[1]) },
                { m2, -1, (-(m2 * guardIni[0]) + guardIni[1]) } };

        matriz = gaussJordan(matriz);

        float x = matriz[0][2];
        float y = matriz[1][2];
        float[] interPositi = { x, y };
        return interPositi;

    }

    /**
     * 
     * Applies Gauss-Jordan elimination to a matrix to solve a system of linear
     * equations.
     * 
     * @param matriz The matrix representing the system of equations.
     * @return The matrix in row echelon form, with the solutions to the system in
     *         the last column.
     */
    private static float[][] gaussJordan(float[][] matriz) {
        int filas = matriz.length;
        int columnas = matriz[0].length;

        for (int i = 0; i < filas; i++) {
            // Encontrar la fila con el valor absoluto máximo en la columna i
            int maxFila = i;
            for (int j = i + 1; j < filas; j++) {
                if (Math.abs(matriz[j][i]) > Math.abs(matriz[maxFila][i])) {
                    maxFila = j;
                }
            }

            // Intercambiar la fila actual por la fila con el valor absoluto máximo en la
            // columna i
            float[] temp = matriz[i];
            matriz[i] = matriz[maxFila];
            matriz[maxFila] = temp;

            // Hacer que el elemento diagonal sea igual a 1
            float diagonal = matriz[i][i];
            for (int j = i; j < columnas; j++) {
                matriz[i][j] /= diagonal;
            }

            // Hacer que los demás elementos en la columna i sean igual a 0
            for (int j = 0; j < filas; j++) {
                if (j != i) {
                    float factor = matriz[j][i];
                    for (int k = i; k < columnas; k++) {
                        matriz[j][k] -= factor * matriz[i][k];
                    }
                }
            }
        }

        return matriz;
    }
}
