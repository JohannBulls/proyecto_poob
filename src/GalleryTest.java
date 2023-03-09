import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class GalleryTest {
    /**
    * crea una galería y agrega una habitación con un conjunto de vértices dados. 
    * Luego, verifica si la habitación ha sido agregada correctamente y si tiene el nombre correcto.
    */
    @Test
    public void testBuildRoom() throws GalleryExecption {
        Gallery g = new Gallery(680, 1300);
        int[][] vertices = {{100, 100}, {200, 100}, {200, 200}, {100, 200}};
        g.buildRoom("red", vertices);
        assertEquals(1, g.rooms().length);
        assertEquals("red", g.rooms()[0]);
    }
    
    /**
     * crea una galería y agrega una habitación con un conjunto de vértices dados. 
     * Luego, intenta agregar una segunda habitación con el mismo nombre, lo que debería arrojar una excepción 
     * GalleryExecption porque el nombre ya está siendo utilizado. El test verifica si se produce la excepción y si su mensaje es el esperado.
       */
    @Test
    public void testBuildRoomWithExistingRoom() {
        Gallery g = new Gallery(680, 1300);
        int[][] vertices1 = {{100, 100}, {200, 100}, {200, 200}, {100, 200}};
        int[][] vertices2 = {{300, 100}, {400, 100}, {400, 200}, {300, 200}};
        try {
            g.buildRoom("red", vertices1);
            g.buildRoom("red", vertices2);
            fail("Expected GalleryExecption to be thrown");
        } catch (GalleryExecption e) {
            assertEquals(GalleryExecption.RoomExist, e.getMessage());
        }
    }
    
    /**
     * crea una galería y muestra una escultura en una habitación que aún no existe. 
     * Luego, intenta agregar una nueva habitación, lo que debería arrojar una excepción GalleryExecption porque 
     * no se puede agregar una habitación después de mostrar una escultura. El test verifica si se produce la excepción y si su mensaje es el esperado.   
    */
    @Test
    public void testBuildRoomAfterDisplayingSculpture() {
        Gallery g = new Gallery(680, 1300);
        int[][] vertices = {{100, 100}, {200, 100}, {200, 200}, {100, 200}};
        g.displaySculpture("red", 150, 150);
        try {
            g.buildRoom("red", vertices);
            fail("Expected GalleryExecption to be thrown");
        } catch (GalleryExecption e) {
            assertEquals(GalleryExecption.Problem, e.getMessage());
        }
    }
    
    /**
     * crea una galería, agrega una habitación con un conjunto de vértices dados y muestra una escultura en esa habitación. 
     * Luego, verifica si la galería está en un estado "correcto", es decir, si no hay habitaciones vacías ni esculturas superpuestas.   
    */
    @Test
    public void testDisplaySculpture() throws GalleryExecption {
        Gallery g = new Gallery(680, 1300);
        int[][] vertices = {{100, 100}, {200, 100}, {200, 200}, {100, 200}};
        g.buildRoom("red", vertices);
        g.displaySculpture("red", 150, 150);
        assertTrue(g.ok());
    }
    
    /**
     * crea una galería y trata de mostrar una escultura en una habitación que no existe, 
     * lo que debería arrojar una excepción GalleryExecption. El test verifica si se produce la excepción y si su mensaje es el esperado.
    */
    @Test
    public void testDisplaySculptureInNonExistingRoom() {
        Gallery g = new Gallery(680, 1300);
        try {
            g.displaySculpture("red", 150, 150);
            fail("Expected GalleryExecption to be thrown");
        } catch (GalleryExecption e) {
            assertEquals(GalleryExecption.RoomNotExist, e.getMessage());
        }
    }
    
    /**
     * crea una galería, agrega una habitación con un conjunto de vértices dados 
     * y luego agrega un guardia a esa habitación. Luego, verifica si la galería 
     * está en un estado "correcto", es decir, si no hay habitaciones vacías ni guardias en habitaciones vacías.
    */
    @Test
    public void testArriveGuard() throws GalleryExecption {
        Gallery g = new Gallery(680, 1300);
        int[][] vertices = {{100, 100}, {200, 100}, {200, 200}, {100, 200}};
        g.buildRoom("red", vertices);
        g.arriveGuard("red");
        assertTrue(g.ok());
    }
        
    /**
     * crea una galería y trata de agregar un guardia en una habitación que no existe, 
     * lo que debería arrojar una excepción GalleryExecption. El test verifica si se produce
     * la excepción y si su mensaje es el esperado.
    */
    @Test
    public void testArriveGuardInNonExistingRoom() {
        Gallery g = new Gallery(680, 1300);
        try {
            g.arriveGuard("red");
            fail("Expected GalleryExecption to be thrown");
        } catch (GalleryExecption e) {
            assertEquals(GalleryExecption.RoomNotExist, e.getMessage());
        }
    }
    
    /**
     * este test no tiene cuerpo de prueba ya que aún no se ha implementado la funcionalidad correspondiente.
    */
    @Test
    public void testMoveGuard() throws GalleryExecption {
        Gallery g = new Gallery(680, 1300);
        int[][] vertices = {{100, 100}, {200, 100}, {200, 200}, {100, 200}};
        g.buildRoom("red", vertices);
        g.arriveGuard("red");
        g.moveGuard("up", 50);
        assertTrue(g.ok());
    }

    
    @Test
    public void testMoveGuardToNonExistingRoom() {
        Gallery g = new Gallery(680, 1300);
        int[][] vertices = {{100, 100}, {200, 100}, {200, 200}, {100, 200}};
        g.buildRoom("red", vertices);
        g.arriveGuard("red");
        try {
            g.moveGuard("up", 500);
            fail("Expected GalleryExecption to be thrown");
        } catch (GalleryExecption e) {
            assertEquals(GalleryExecption.RoomNotExist, e.getMessage());
        }
    }

    @Test
    public void testMoveGuardOutsideRoom() {
        Gallery g = new Gallery(680, 1300);
        int[][] vertices = {{100, 100}, {200, 100}, {200, 200}, {100, 200}};
        g.buildRoom("red", vertices);
        g.arriveGuard("red");
        try {
            g.moveGuard("up", 500);
            fail("Expected GalleryExecption to be thrown");
        } catch (GalleryExecption e) {
            assertEquals(GalleryExecption.Problem, e.getMessage());
        }
    }

    @Test
    public void testMoveGuardWithNoGuard() {
        Gallery g = new Gallery(680, 1300);
        int[][] vertices = {{100, 100}, {200, 100}, {200, 200}, {100, 200}};
        g.buildRoom("red", vertices);
        try {
            g.moveGuard("up", 50);
            fail("Expected GalleryExecption to be thrown");
        } catch (GalleryExecption e) {
            assertEquals(GalleryExecption.Problem, e.getMessage());
        }
    }
}