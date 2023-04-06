package gallery;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.awt.Polygon;
import java.text.DecimalFormatSymbols;
import java.text.DecimalFormat;

/**
 * Let me simulate and solve the problem
 * @author Sebastian Zamora
 * @author Johann Amaya
 * @version 1.3
 */
public class GalleryContest
{
    // Caso de Prueba 1
    // {{100,100},{120,100},{120,120},{200,120},{200,180},{100,180}},{110,100},{180,150}
    // Caso de Prueba 2
    // {{0,0},{20,0},{20,30},{60,30},{60,0},{80,0},{80,50},{0,50}},{10,10},{70,10}
    private Wall[] lineas;
    private List<int[]> posiciones;
    private Polygon poligono;
    /**
     * Calculate the shortest distance that moves the guard to see the sculpture.
     */
    public float solve(int[][] polygon, int[] guard, int[] sculpture){
        createPolygon(polygon);
        float distance = 0;
        buildlineas(polygon);
        if(guardSeeTheSculpture(guard,sculpture)){
            solution(guard,sculpture,polygon);
            distance =distanceTraveled();
        }
        return distance;
    }

    /**
     * Simulate the case that the user give
     */
    public float simulate(int[][] polygon, int[] guard, int[] sculpture){
        createPolygon(polygon);
        float distance = solve(polygon,guard,sculpture);
        Gallery galeria = new Gallery(polygon,guard,sculpture);
        if(distance > 0){
            for(int[] i:posiciones){
                galeria.moveGuard("black",i[0],i[1]);
            }
        }
        drawString();
        return distance;
    }

    /**
     * Make the movements to the guard to see the sculpture
     */
    private void solution(int[] guardLocation,int[]sculptureLocation,int[][] vertices){
        posiciones = new ArrayList<>();
        posiciones.add(guardLocation);
        int[] sculpttureLineVision = verticeNearest(sculptureLocation,guardLocation,vertices);
        int[] verticeLine = sculpttureLineVision;
        Wall guardVision = new Wall(sculpttureLineVision[0],sculpttureLineVision[1],guardLocation[0],guardLocation[1]);
        Wall sculptureVision = new Wall(sculpttureLineVision[0],sculpttureLineVision[1],sculptureLocation[0],sculptureLocation[1]);
        while(guardVision.intersect(sculptureVision)){
            sculpttureLineVision = verticeNearest(guardLocation,sculptureLocation,vertices);
            int[] pos = {sculpttureLineVision[0],sculpttureLineVision[1]};
            posiciones.add(pos);
            guardVision = new Wall(sculpttureLineVision[0],sculpttureLineVision[1],300-guardLocation[0],300-guardLocation[1]);
        }
        float[] inter=interPosition(sculptureLocation,verticeLine,sculpttureLineVision);
        int[] result ={(int)inter[0],(int)inter[1]};
        posiciones.add(result);
    }

    /**
     * Let me know the vertice nearest to move
     */
    private int[] verticeNearest(int[] startPoint,int[] endPoint,int[][] vertices){
        int[] nearestPoint = {0,0};
        double distance = Math.hypot(endPoint[0]-startPoint[0],endPoint[1]-startPoint[1]);
        for(int[] i: vertices){
            double distanceToVertice = Math.hypot(i[0]-startPoint[0],i[1]-startPoint[1]);
            if(distanceToVertice < distance && poligono.contains(colocar(startPoint,i)[0],colocar(startPoint,i)[1])){
                nearestPoint=colocar(startPoint,i);
                distance = distanceToVertice;
            }
        }
        return nearestPoint;
    }

    /**
     * let me put the point that move the guard.
     * @param start the chord coordinates of the start point.
     * @param finish the chord coordinates of the end point.
     * @return the chord coodinates of the point.
     */
    private int[] colocar(int[] start, int[] finish){
        int[] point={0,0};
        if(start[0]<finish[0]){
            point[0] =finish[0] +1;
        }else{
            point[0] =finish[0] -1;
        }
        if(start[1]<finish[1]){
            point[1] =finish[1] +1;
        }else{
            point[1] =finish[1] -1;
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
            Wall line = new Wall(polygon[i - 1][0],  300- polygon[i - 1][1], polygon[i][0], 300 - polygon[i][1]);
            lineas[i] = line;
        }
        Wall line = new Wall(polygon[0][0], 300 - polygon[0][1], polygon[polygon.length - 1][0],
                300 - polygon[polygon.length - 1][1]);
        lineas[0] = line;
    }

    /**
     * Let me know if the guard see the sculpture
     * @return if the guard see the sculpture
     */
    private boolean guardSeeTheSculpture(int[] posGuardia,int[] posEscultura){
        Wall vista = new Wall(posGuardia[0],300-posGuardia[1], posEscultura[0],300- posEscultura[1]);
        return vista.intersects(lineas);
    }

    /**
     * Found the point on the line vision of the sculpture.
     * @param esculIni  the sculpture's position.
     * @param esculFin  the vertice nearest to the sculpture.
     * @param guardIni  the guard's position.
     * @return the point on the line vision.
     */
    private float[] interPosition(int[] esculIni,int[] esculFin, int[] guardIni){
        float numero1 = esculFin[1]-esculIni[1];
        float numero2 = esculFin[0]-esculIni[0];
        float m1 = numero1/numero2;
        float m2=(-1)/m1;
        float[][]matriz={{m1,1,((m1*-esculFin[0])+esculFin[1])},{m2,1,((m2*-guardIni[0])+guardIni[1])}};
        matriz=gaussJordan(matriz);
        float x=matriz[0][2];
        float y=matriz[1][2];
        float[] interPositi={-x,y};
        return interPositi;
    }

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

            // Intercambiar la fila actual por la fila con el valor absoluto máximo en la columna i
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

    /**
     * Calculate the distance that the guard walks to see the sculpture.
     * @return The distance that walk the guard.
     */
    private float distanceTraveled(){
        float distance = 0;
        for(int i = 0; i<posiciones.size()-1;i++){
            distance += Math.hypot(posiciones.get(i+1)[0]-posiciones.get(i)[0],posiciones.get(i+1)[1]-posiciones.get(i)[1]);
            DecimalFormatSymbols extencion =new DecimalFormatSymbols();
            extencion.setDecimalSeparator('.');
            DecimalFormat formato1 = new DecimalFormat("#.000000", extencion);
            formato1.format(distance);
        }
        return distance;
    }

    /**
     * Create a polygon with the room's vertices.
     * @param lineas the list of the points of the polygon
     */
    private void createPolygon(int[][] lineas){
        int[] cordx = new int[lineas.length];
        int[] cordy = new int[lineas.length];
        for (int i = 0; i < lineas.length; i++) {
            cordx[i] = lineas[i][0];
            cordy[i] = lineas[i][1];
        }
        poligono = new Polygon(cordx, cordy, lineas.length);
    }

    /**
     * Let me create the lines that represented the route.
     */
    private void drawString(){
        for(int i = 0;i<posiciones.size()-1;i++){
            Wall recorido = new Wall(posiciones.get(i)[0],300-posiciones.get(i)[1],posiciones.get(i+1)[0],300-posiciones.get(i+1)[1]);
            recorido.draw("cyan");
        }
    }
}
