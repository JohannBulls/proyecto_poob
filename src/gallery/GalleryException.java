package gallery;

/**
 * Let me create a galery
 *
 * @author Sebastian Zamora
 * @author Johann Amaya
 * @version 1.3
 */
public class GalleryException extends Exception {
    public static final String ROOM_EXIST = "La sala ya existe";
    public static final String PROBLEM = "No se puede crear otra sala ,ya que, se utilizo el constructor del problema.";
    public static final String ROOM_NOT_EXIST = "La sala no existe";
    public static final String ROOM_HAS_GUARD = "La sala ya tiene guardia";
    public static final String ROOM_HAS_NOT_GUARD = "La sala no tiene guardia";
    public static final String ROOM_HAS_SCULPTURE = "La sala ya tiene escultura";
    public static final String ROOM_HAS_NOT_SCULPTURE = "La sala no tiene escultura";
    public static final String OUT_OF_THE_ROOM = "La posicion no esta dentro de la sala";
    public static final String INTERSECT_ROOM = "No se puede crear la sala dado que se interseca con otra sala";
    public static final String COULD_NOT_CREATE_ROOM = "No se puede crear la sala dado que no tiene una forma adecuada";
    public static final String GUARD_NOT_MOVE = "El guardia no tiene la necesidad de moverse";
    public static final String ALARM_NOT_CHANGE = "No se cambio el estado de la alarma";
    public static final String STANDBY_ROOM = "Esta en una sala Standy, no puede colocar esculturas.";
    public static final String UNPROTECTED_ROOM = "Esta en una sala Unprotected, no tiene alarma.";
    public static final String STANDBY_STEAL = "Esta en una sala Standy, no hay escultura para robar.";
    public static final String STANDBY_NOT_SCULPTUER = "Esta es una sala Standy, no tiene escultura.";
    public static final String UNPROTECTED_FALSE_ALARM = "No puede devolver el estado de la alarma dado que la sala Unprotected no tiene alarma";
    public static final String HEAVY_SCULPTURE = "No puede robar la escultura ya que es muy PESADA.";

    /**
     * Lanza las exepciones creadas.
     * 
     * @param Recibe un mensaje String msm
     */
    public GalleryException(String msm) {
        super(msm);
    }
}