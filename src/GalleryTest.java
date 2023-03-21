import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class GalleryTest.
 * 
 * @author Sebastian Zamora
 * @author Johann Amaya
 * @version 1.5
 */
public class GalleryTest {

    private Gallery gallery;
    /**
     * En este método se crea una instancia de Gallery y se construye una sala "red" 
     * con un polígono de cuatro puntos. Luego se agrega un guardia y se coloca una 
     * escultura en la sala. Este método se ejecutará antes de cada prueba para 
     * asegurar un estado consistente de la instancia de Gallery.
    */
    public void setUp() {
        gallery = new Gallery(500, 500);
        int[][] polygon = { { 50, 50 }, { 50, 250 }, { 250, 250 }, { 250, 50 } };
        gallery.buildRoom("red", polygon);
        gallery.arriveGuard("red");
        gallery.displaySculpture("red", 200, 200);
    }

    /**
     * crea una galería y agrega una habitación con un conjunto de vértices dados.
     * Luego, verifica si la habitación ha sido agregada correctamente y si tiene el
     * nombre correcto.
     */
    @Test
    public void testBuildRoom() {
        Gallery g = new Gallery(1100, 680);
        int[][] vertices = { { 100, 100 }, { 200, 100 }, { 200, 200 }, { 100, 200 } };
        g.buildRoom("red", vertices);
        assertNull(g.getException());
        assertEquals(1, g.rooms().length);
        assertEquals("red", g.rooms()[0]);
    }

    /**
     * Verifica que el constructor de Gallery cree correctamente una galería y que
     * las banderas confirm y problem se establezcan correctamente.
     * Además, se comprueba que el campo exepcion sea nulo, lo que indica que no se
     * produjo ninguna excepción durante la construcción.
     */
    @Test
    public void testConstructor() {
        // Se definen los valores de entrada de ejemplo
        int[][] polygon = { { 0, 0 }, { 0, 100 }, { 100, 100 }, { 100, 0 } };
        int[] guard = { 50, 50 };
        int[] sculpture = { 75, 75 };

        // Se crea una instancia de Gallery con los valores de entrada definidos
        Gallery gallery = new Gallery(polygon, guard, sculpture);

        // Se comprueba que las banderas confirm y problem se establezcan correctamente
        assertTrue(gallery.confirm);
        assertFalse(gallery.problem);

        // Se comprueba que el campo exepcion sea nulo, lo que indica que no se produjo
        // ninguna excepción durante la construcción
        assertNull(gallery.exepcion);
    }

    /**
     * crea una galería y agrega una habitación con un conjunto de vértices dados.
     * Luego, intenta agregar una segunda habitación con el mismo nombre, lo que
     * debería arrojar una excepción
     * GalleryExecption porque el nombre ya está siendo utilizado. El test verifica
     * si se produce la excepción y si su mensaje es el esperado.
     */
    @Test
    public void testBuildRoomWithExistingRoom() {
        Gallery g = new Gallery(1100, 680);
        int[][] vertices1 = { { 100, 100 }, { 200, 100 }, { 200, 200 }, { 100, 200 } };
        int[][] vertices2 = { { 300, 100 }, { 400, 100 }, { 400, 200 }, { 300, 200 } };
        g.buildRoom("red", vertices1);
        g.buildRoom("red", vertices2);
        assertEquals(g.getException(), "La sala ya existe");
        assertFalse(g.ok());
    }

    /**
     * crea una galería y muestra una escultura en una habitación que aún no existe.
     * Luego, intenta agregar una nueva habitación, lo que debería arrojar una
     * excepción GalleryExecption porque
     * no se puede agregar una habitación después de mostrar una escultura. El test
     * verifica si se produce la excepción y si su mensaje es el esperado.
     */
    @Test
    public void testBuildRoomAfterDisplayingSculpture() {
        Gallery g = new Gallery(1100, 680);
        int[][] vertices = { { 100, 100 }, { 200, 100 }, { 200, 200 }, { 100, 200 } };
        g.displaySculpture("red", 150, 150);
        g.buildRoom("red", vertices);
        assertEquals(g.getException(), "La sala no existe");
    }

    /**
     * Verifica que no se pueda crear una escultura en una habitación que ya tenga
     * una escultura.
     * Para ello, primero se construye una habitación con un conjunto de vértices
     * dados y se agrega una escultura.
     * Luego, se intenta agregar otra escultura en la misma habitación y se verifica
     * que se produzca una excepción
     * y que la bandera confirm se establezca en falso.
     */
    @Test
    public void shouldNotCreateSculptureInARoomThatHave() {
        Gallery g = new Gallery(1100, 680);
        int[][] vertices = { { 100, 100 }, { 200, 100 }, { 200, 200 }, { 100, 200 } };
        g.buildRoom("red", vertices);

        // Se agrega una escultura en la habitación
        g.displaySculpture("red", 150, 150);

        // Se intenta agregar otra escultura en la misma habitación
        g.displaySculpture("red", 100, 120);

        // Se verifica que se produzca una excepción y que la bandera confirm se
        // establezca en falso
        assertEquals(g.getException(), "La sala ya tiene escultura");
        assertFalse(g.ok());
    }

    /**
     * crea una galería, agrega una habitación con un conjunto de vértices dados y
     * muestra una escultura en esa habitación.
     * Luego, verifica si la galería está en un estado "correcto", es decir, si no
     * hay habitaciones vacías ni esculturas superpuestas.
     */
    @Test
    public void testDisplaySculpture() {
        Gallery g = new Gallery(1100, 680);
        int[][] vertices = { { 100, 100 }, { 200, 100 }, { 200, 200 }, { 100, 200 } };
        g.buildRoom("red", vertices);
        g.displaySculpture("red", 150, 150);
        assertEquals(g.getException(), null);
        assertTrue(g.ok());
    }

    /**
     * crea una galería y trata de mostrar una escultura en una habitación que no
     * existe,
     * lo que debería arrojar una excepción GalleryExecption. El test verifica si se
     * produce la excepción y si su mensaje es el esperado.
     */
    @Test
    public void testDisplaySculptureInNonExistingRoom() throws GalleryException {
        Gallery g = new Gallery(1100, 680);
        g.displaySculpture("red", 150, 150);
        assertEquals(g.getException(), "La sala no existe");
        assertEquals(g.ok(), false);
    }

    /**
     * crea una galería, agrega una habitación con un conjunto de vértices dados
     * y luego agrega un guardia a esa habitación. Luego, verifica si la galería
     * está en un estado "correcto", es decir, si no hay habitaciones vacías ni
     * guardias en habitaciones vacías.
     */
    @Test
    public void testArriveGuard() throws GalleryException {
        Gallery g = new Gallery(1100, 680);
        int[][] vertices = { { 100, 100 }, { 200, 100 }, { 200, 200 }, { 100, 200 } };
        g.buildRoom("red", vertices);
        g.arriveGuard("red");
        assertEquals(g.getException(), null);
        assertTrue(g.ok());
    }

    /**
     * crea una galería y trata de agregar un guardia en una habitación que no
     * existe,
     * lo que debería arrojar una excepción GalleryExecption. El test verifica si se
     * produce
     * la excepción y si su mensaje es el esperado.
     */
    @Test
    public void testArriveGuardInNonExistingRoom() {
        Gallery g = new Gallery(1100, 680);
        g.arriveGuard("red");
        assertEquals(g.getException(), "La sala no existe");
        assertFalse(g.ok());
    }

    /**
     * Test para verificar si un guardia se mueve a una posición válida dentro de
     * una habitación.
     * 
     * @throws GalleryException en caso de que se produzca un error en la galería
     */
    @Test
    public void testMoveGuard() throws GalleryException {
        Gallery g = new Gallery(1100, 680);
        int[][] vertices = { { 100, 100 }, { 200, 100 }, { 200, 200 }, { 100, 200 } };
        int[] posiciones = { 100, 150 };
        g.buildRoom("red", vertices);
        g.arriveGuard("red");
        g.moveGuard("red", 100, 150);
        assertEquals(g.getException(), null);
        assertEquals(g.guardLocation("red")[0], posiciones[0]);
        assertEquals(g.guardLocation("red")[1], posiciones[1]);
        assertTrue(g.ok());
    }

    /**
     * Test para verificar si un guardia se mueve a una posición dentro de una
     * habitación que no existe.
     */
    @Test
    public void testMoveGuardToNonExistingRoom() {
        Gallery g = new Gallery(1100, 680);
        int[][] vertices = { { 100, 100 }, { 200, 100 }, { 200, 200 }, { 100, 200 } };
        g.arriveGuard("red");
        g.moveGuard("red", 50, 50);
        assertEquals(g.getException(), "La sala no existe");
        assertFalse(g.ok());
    }

    /**
     * Test para verificar si un guardia se mueve a una posición fuera de una
     * habitación.
     */
    @Test
    public void testMoveGuardOutsideRoom() {
        Gallery g = new Gallery(1100, 680);
        int[][] vertices = { { 100, 100 }, { 200, 100 }, { 200, 200 }, { 100, 200 } };
        g.buildRoom("red", vertices);
        g.arriveGuard("red");
        g.moveGuard("red", 50, 50);
        assertEquals(g.getException(), "La posicion no esta dentro de la sala");
        assertFalse(g.ok());
    }

    /**
     * Test para verificar si se intenta mover un guardia en una habitación donde no
     * hay ningún guardia.
     */
    @Test
    public void testMoveGuardWithNoGuard() {
        Gallery g = new Gallery(1100, 680);
        int[][] vertices = { { 100, 100 }, { 200, 100 }, { 200, 200 }, { 100, 200 } };
        g.buildRoom("red", vertices);
        g.moveGuard("red", 150, 150);
        assertEquals(g.getException(), "La sala no tiene guardia");
        assertFalse(g.ok());
    }

    /**
     * Test para verificar si las salas con alarmas están siendo notificadas
     * correctamente.
     */
    @Test
    public void shouldHaveRoomsWithAlarms() {
        Gallery g = new Gallery(130, 680);
        int[][] vertices = { { 100, 100 }, { 200, 100 }, { 200, 200 }, { 100, 200 } };
        int[][] vertices2 = { { 210, 210 }, { 250, 250 }, { 250, 210 }, { 210, 250 } };
        int[][] vertices3 = { { 260, 260 }, { 300, 300 }, { 260, 300 }, { 300, 260 } };
        String[] rooms = { "blue", "yellow" };
        g.buildRoom("red", vertices);
    }

    /**
     * Se crea una galería con un polígono y una escultura en una ubicación 
     * específica. Luego, se mueve un guardia y se activa una alarma. 
     * Finalmente, se verifica si el robo de la escultura funciona correctamente.
     */
    @Test
    public void testStealSculpture() {
        Gallery gallery = new Gallery(600, 400);
        int[][] polygon = { { 50, 50 }, { 50, 250 }, { 200, 250 }, { 200, 200 }, { 300, 200 }, { 300, 50 } };
        int[] guard = { 100, 100 };
        int[] sculpture = { 250, 150 };
        gallery.buildRoom("blue", polygon);
        gallery.arriveGuard("blue");
        gallery.moveGuard("blue", 150, 150);
        gallery.displaySculpture("blue", sculpture[0], sculpture[1]);
        gallery.alarm("blue", true);
        // assertFalse(gallery.steal());
        gallery.moveGuard("blue", 250, 150);
        // assertTrue(gallery.steal());
    }
    
    /**
     * Prueba el caso donde no hay un guardia en la habitación, por lo que el 
     * robo debería fallar. Crea una instancia de la clase Gallery con una 
     * habitación rectangular llamada "black" y una escultura en el centro de 
     * la habitación. Luego llama al método steal() y verifica que devuelve false.
    */
    @Test
    public void testStealWithoutGuard() {
        // Test stealing when there is no guard in the room
        Gallery gallery = new Gallery(500, 500);
        int[][] polygon = {{100, 100}, {100, 200}, {200, 200}, {200, 100}};
        gallery.buildRoom("black", polygon);
        gallery.displaySculpture("black", 150, 150);
        assertFalse(gallery.steal());
    }
    
    /** Prueba el caso donde hay un guardia en la habitación pero no está 
     * mirando la escultura. Nuevamente, se crea una instancia de Gallery, 
     * se construye una habitación rectangular llamada "black" con una 
     * escultura en el centro y se coloca un guardia en la habitación. 
     * El guardia se mueve a la posición de la escultura y luego se llama 
     * al método steal() y se verifica que devuelve false.
    */
    @Test
    public void testStealWithGuard() {
        // Test stealing when there is a guard in the room
        Gallery gallery = new Gallery(500, 500);
        int[][] polygon = {{100, 100}, {100, 200}, {200, 200}, {200, 100}};
        gallery.buildRoom("black", polygon);
        gallery.arriveGuard("black");
        gallery.moveGuard("black", 150, 150);
        gallery.displaySculpture("black", 150, 150);
        assertFalse(gallery.steal());
    }
    
    /** Prueba el caso donde el guardia está mirando la escultura, 
     * lo que debería permitir el robo. Nuevamente, se crea una 
     * instancia de Gallery, se construye una habitación rectangular 
     * llamada "black" con una escultura en el centro y se coloca un 
     * guardia en la habitación. El guardia se mueve a la posición de 
     * la escultura, pero esta vez se mueve de nuevo a una posición 
     * donde puede ver la escultura. Luego llama al método steal() 
     * y verifica que devuelve true.
    */
    @Test
    public void testStealWithGuardLooking() {
        // Test stealing when the guard is looking at the sculpture
        Gallery gallery = new Gallery(500, 500);
        int[][] polygon = {{100, 100}, {100, 200}, {200, 200}, {200, 100}};
        gallery.buildRoom("black", polygon);
        gallery.arriveGuard("black");
        gallery.moveGuard("black", 150, 150);
        gallery.displaySculpture("black", 150, 150);
        gallery.moveGuard("black", 100, 100);
        assertTrue(gallery.steal());
    }

    /**
     * Se verifica si se pueden crear habitaciones correctamente y se 
     * almacenan en la galería.
    */
    @Test
    public void testRooms() {
        assertEquals(1, gallery.rooms.size());
        int[][] polygon2 = { { 150, 150 }, { 200, 150 }, { 200, 200 }, { 150, 200 } };
        gallery.buildRoom("green", polygon2);
        assertEquals(2, gallery.rooms.size());
        assertEquals("red", gallery.rooms.keySet().toArray()[0]);
        assertEquals("green", gallery.rooms.keySet().toArray()[1]);
    }

    /**
     * Se verifica si el método roomsOnAlert() 
     * funciona correctamente para habitaciones con y sin alarma activada.
    */
    @Test
    public void testRoomsOnAlert() {
        Gallery gallery = new Gallery(300, 300);
        int[][] polygon1 = {{0, 0}, {0, 100}, {100, 100}, {100, 0}};
        int[][] polygon2 = {{100, 100}, {100, 200}, {200, 200}, {200, 100}};
        int[][] polygon3 = {{100, 0}, {100, -100}, {200, -100}, {200, 0}};
        gallery.buildRoom("black", polygon1);
        gallery.buildRoom("white", polygon2);
        gallery.buildRoom("red", polygon3);
        gallery.arriveGuard("black");
        gallery.arriveGuard("white");
        gallery.arriveGuard("red");
        gallery.moveGuard("black", 50, 50);
        gallery.moveGuard("white", 150, 150);
        gallery.moveGuard("red", 150, -50);
        gallery.alarm("black", true);
        gallery.alarm("red", true);
        assertTrue(gallery.roomsOnAlert().contains("black"));
        assertTrue(gallery.roomsOnAlert().contains("red"));
        assertFalse(gallery.roomsOnAlert().contains("white"));
        gallery.moveGuard("white", 100, 100);
        gallery.alarm("white", true);
        assertTrue(gallery.roomsOnAlert().contains("black"));
        assertTrue(gallery.roomsOnAlert().contains("red"));
        assertTrue(gallery.roomsOnAlert().contains("white"));
    }

    /**
     * Se verifica si el método guardLocation() funciona correctamente 
     * cuando se mueve un guardia.
    */
    @Test
    public void testGuardLocation() {
        Gallery gallery = new Gallery(300, 300);
        int[][] polygon = {{0, 0}, {0, 100}, {100, 100}, {100, 0}};
        int[] guard = {50, 50};
        gallery.buildRoom("black", polygon);
        gallery.arriveGuard("black");
        gallery.moveGuard("black", guard[0], guard[1]);
        assertEquals(guard[0], gallery.guardLocation("black")[0]);
        assertEquals(guard[1], gallery.guardLocation("black")[1]);
        gallery.moveGuard("black", 75, 75);
        assertEquals(75, gallery.guardLocation("black")[0]);
        assertEquals(75, gallery.guardLocation("black")[1]);
    }

    /**
     * Se verifica si el método sculptureLocation() funciona correctamente 
     * cuando se muestra una escultura.
    */
    @Test
    public void testSculptureLocation() {
        assertNull(gallery.sculptureLocation("red"));
        gallery.displaySculpture("red", 60, 60);
        assertArrayEquals(new int[] { 60, 60 }, gallery.sculptureLocation("red"));
    }

    /**
     * Se verifica si el método distanceTraveled() funciona correctamente 
     * cuando se mueve un guardia.   
    */
    @Test
    public void testDistanceTraveled() {
        gallery.arriveGuard("red");
        gallery.moveGuard("red", 80, 80);
        assertEquals(40, gallery.distanceTraveled("red"));
        gallery.moveGuard("red", 90, 90);
        assertEquals(50, gallery.distanceTraveled("red"));
    }

    /**
     * Se verifica si el método sculpturePresent() funciona correctamente 
     * cuando se muestra una escultura.
    */
    @Test
    public void testSculpturePresent() {
        assertFalse(gallery.sculpturePresent("red"));
        gallery.displaySculpture("red", 60, 60);
        assertTrue(gallery.sculpturePresent("red"));
    }
    
    /**
     * Se verifica que si no se ha construido ninguna sala, el arreglo 
     * devuelto es de longitud cero.
    */
    @Test
    public void testRoomsWithFalseAlarm_noRooms() {
        assertEquals(0, gallery.roomsWithFalseAlarm().length);
    }
    
    /**
     * Se verifica que si se construye una sala y se activa la alarma, 
     * el arreglo devuelto es de longitud cero.
    */
    @Test
    public void testRoomsWithFalseAlarm_noFalseAlarm() {
        gallery.buildRoom("red", new int[][]{{0,0}, {0,10}, {10,10}, {10,0}});
        gallery.alarm("red", true);
        assertEquals(0, gallery.roomsWithFalseAlarm().length);
    }
    
    /** 
     * Se verifica que si se construye una sala y se desactiva la 
     * alarma, el arreglo devuelto contiene el nombre de esa sala.
    */
    @Test
    public void testRoomsWithFalseAlarm_falseAlarm() {
        gallery.buildRoom("red", new int[][]{{0,0}, {0,10}, {10,10}, {10,0}});
        gallery.alarm("red", false);
        String[] roomsWithFalseAlarm = gallery.roomsWithFalseAlarm();
        assertEquals(1, roomsWithFalseAlarm.length);
        assertTrue(roomsWithFalseAlarm[0].equals("red"));
    }
    
    /** Se construyen cuatro salas, se activa la alarma en dos y 
     * se desactiva en otras dos. Se verifica que el arreglo 
     * devuelto contiene los nombres de las salas donde la alarma 
     * está desactivada (red y green).
    */
    @Test
    public void testRoomsWithFalseAlarm_multipleRooms() {
        gallery.buildRoom("red", new int[][]{{0,0}, {0,10}, {10,10}, {10,0}});
        gallery.buildRoom("blue", new int[][]{{10,0}, {10,10}, {20,10}, {20,0}});
        gallery.buildRoom("green", new int[][]{{0,10}, {0,20}, {10,20}, {10,10}});
        gallery.buildRoom("yellow", new int[][]{{10,10}, {10,20}, {20,20}, {20,10}});
        gallery.alarm("red", false);
        gallery.alarm("blue", true);
        gallery.alarm("green", false);
        gallery.alarm("yellow", false);
        String[] roomsWithFalseAlarm = gallery.roomsWithFalseAlarm();
        assertEquals(2, roomsWithFalseAlarm.length);
        assertTrue(roomsWithFalseAlarm[0].equals("red"));
        assertTrue(roomsWithFalseAlarm[1].equals("green"));
    }

    /**
     * Se verifica si la ventana de visualización de la galería es visible.
    */
    @Test
    public void testMakeVisible() {
        gallery.buildRoom("yellow", new int[][] { { 0, 0 }, { 100, 0 }, { 100, 100 }, { 0, 100 } });
        gallery.makeVisible();
        Canvas canvas = Canvas.getCanvas("Galeria");
        assertNotNull(canvas, "The canvas should not be null");
        assertTrue(canvas.isVisible(), "The canvas should be visible");
    }
    
    /**
     * Este método de prueba verifica que la galería se vuelva invisible 
     * después de llamar al método makeInvisible(). Primero se construye 
     * una sala en la galería, se hace visible y luego se hace invisible. 
     * Finalmente, se verifica que el canvas de la galería no esté visible 
     * utilizando el método assertFalse() de JUnit.
    */
    @Test
    public void testMakeInvisible() {
        gallery.buildRoom("pink", new int[][] { { 0, 0 }, { 100, 0 }, { 100, 100 }, { 0, 100 } });
        gallery.makeVisible();
        gallery.makeInvisible();
        assertFalse(Canvas.getCanvas("Galeria").isVisible(), "The canvas should be invisible");
    }

    /**
     * Este método de prueba verifica el método intersect() de la clase 
     * Gallery, que devuelve un booleano que indica si un polígono dado 
     * intersecta con cualquier otra sala en la galería. Se prueba con 
     * dos polígonos diferentes, uno que no debería intersectar y otro 
     * que debería. Se utiliza el método assertTrue() o assertFalse() 
     * de JUnit para verificar si el resultado es el esperado.
    */
    @Test
    public void testIntersect() {
        assertTrue(gallery.intersect(new int[][] { { 0, 0 }, { 100, 0 }, { 100, 100 }, { 0, 100 } }),
                "The polygon should not intersect");
        assertFalse(gallery.intersect(new int[][] { { 0, 0 }, { 50, 50 }, { 100, 0 }, { 50, 100 }, { 0, 50 } }),
                "The polygon should intersect");
    }
    
    /**
     * Este método de prueba verifica que la excepción lanzada por la clase 
     * Gallery sea correcta cuando se construye una sala que intersecta con 
     * otra sala. Se crea una instancia de la clase Gallery con una sala que 
     * intersecta con otra sala, se construye la sala en la galería y se verifica 
     * que el método ok() de la clase Gallery devuelva false y que el método 
     * getException() de la clase Gallery devuelva un mensaje de excepción 
     * específico. Se utiliza el método assertFalse() y assertEquals() de JUnit 
     * para verificar si el resultado es el esperado.
    */
    @Test
    public void testGetException() {
        int[][] polygon = {{0, 0}, {0, 5}, {5, 5}, {5, 0}};
        Gallery gallery = new Gallery(polygon, new int[]{3, 3}, new int[]{2, 2});
        
        gallery.buildRoom("red", polygon);
        assertFalse(gallery.ok());
        assertEquals("The room intersects with another room.", gallery.getException());
    }
    
    /**
     * Se verifica si el método getVertices() devuelve los 
     * vértices correctos para una habitación dada.
    */
    @Test
    public void test_GetVertices(){
        int[][] polygon = {{0, 0}, {0, 5}, {5, 5}, {5, 0}};
        Gallery gallery = new Gallery(polygon, new int[]{3, 3}, new int[]{2, 2});

        int[][] expectedVertices = {{0, 0}, {0, 5}, {5, 5}, {5, 0}};
        int[][] actualVertices = gallery.getVertices("black");
        assertArrayEquals(expectedVertices, actualVertices);
    }
}
