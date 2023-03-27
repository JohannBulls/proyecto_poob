import java.lang.Math;
/**
 * Let me simulate and solve the problem
 * @author Sebastian Zamora
 * @author Johann Amaya
 * @version 1.0
 */
public class GalleryContest
{
    private Gallery galeria;
    Wall[] lineas;
    /**
     * Calculate the shortest distance that moves the guard to see the sculpture.
     */
    public float solve(int[][] polygon, int[] guard, int[] sculpture){
        float distance = 0;
        galeria = new Gallery(polygon,guard,sculpture);
        lineas = galeria.getRoom("black").getLineas();
        if(galeria.getRoom("black").guardSeeTheSculpture()){
            solution();
            distance =galeria.distanceTraveled("black");
        }
        return distance;
    }
    
    /**
     * Make the movements to the guard to see the sculpture
     */
    private void solution(){
        int[] sculptureLocation = galeria.sculptureLocation("black");
        int[] guardLocation = galeria.guardLocation("black");
        int[] sculpttureLineVision = verticeNearest(sculptureLocation,guardLocation);
        Wall guardVision = new Wall(sculpttureLineVision[0],sculpttureLineVision[1],guardLocation[0],guardLocation[1]);
        Wall sculptureVision = new Wall(sculpttureLineVision[0],sculpttureLineVision[1],sculptureLocation[0],sculptureLocation[1]);
        guardLocation = galeria.guardLocation("black");
        while(guardVision.intersect(sculptureVision)){
            sculpttureLineVision = verticeNearest(guardLocation,sculptureLocation);
            galeria.moveGuard("black",sculpttureLineVision[0],sculpttureLineVision[1]);
            guardVision = new Wall(sculpttureLineVision[0],sculpttureLineVision[1],300-guardLocation[0],300-guardLocation[1]);
        }
    }
    
    /**
     * Let me know the vertice nearest to move
     */
    private int[] verticeNearest(int[] startPoint,int[] endPoint){
        int[][] vertices = galeria.getVertices("black");
        int[] nearestPoint = {0,0};
        double distance = Math.hypot(endPoint[0]-startPoint[0],endPoint[1]-startPoint[1]);
        for(int[] i: vertices){
            double distanceToVertice = Math.hypot(i[0]-startPoint[0],i[1]-startPoint[1]);
            if(distanceToVertice < distance && galeria.getRoom("black").containsPoint(colocar(startPoint,i))){
                nearestPoint=colocar(startPoint,i);
                distance = distanceToVertice;
            }
        }
        System.out.println(nearestPoint[0]+"-"+nearestPoint[1]);
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
            point[1] =finish[1] +5;
        }else{
            point[1] =finish[1] -5;
        }
        return point;
    }
}
