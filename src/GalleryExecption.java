
/**
 * Let me create a galery
 *
 * @author Sebastian Zamora
 * @author Johann Amaya
 * @version 1.0
 */
public class GalleryExecption extends Exception
{
    public static final String RoomExist = "La sala ya existe";
    public static final String Problem = "No se puede crear otra sala ,ya que, se utilizo el constructor del problema.";
    public static final String RoomNotExist = "La sala no existe";
    public static final String RoomHasGuard = "La sala ya tiene guardia";
    public static final String RoomHasSculpture = "La sala ya tiene escultura";
    public static final String OutOfTheRoom = "La posicion no esta dentro de la sala";
    /**
     * Lanza las exepciones creadas.
     * @param Recibe un mensaje String msm
    */
    public GalleryExecption(String msm){
        super(msm);
    }
}
