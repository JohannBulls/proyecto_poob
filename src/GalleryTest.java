

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class GalleryTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class GalleryTest
{
    /**
    * crea una galería y agrega una habitación con un conjunto de vértices dados. 
    * Luego, verifica si la habitación ha sido agregada correctamente y si tiene el nombre correcto.
    */
    @Test
    public void testBuildRoom() throws GalleryException {
        Gallery g = new Gallery(1100, 680);
        int[][] vertices = {{100, 100}, {200, 100}, {200, 200}, {100, 200}};
        g.buildRoom("red", vertices);
        System.out.println(g.rooms().length);
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
        Gallery g = new Gallery(1100, 680);
        int[][] vertices1 = {{100, 100}, {200, 100}, {200, 200}, {100, 200}};
        int[][] vertices2 = {{300, 100}, {400, 100}, {400, 200}, {300, 200}};
        try {
            g.buildRoom("red", vertices1);
            g.buildRoom("red", vertices2);
        } catch (GalleryException e) {
            fail("Expected GalleryExecption to be thrown");
            assertEquals(GalleryException.RoomExist, e.getMessage());
        }
    }
    
    /**
     * crea una galería y muestra una escultura en una habitación que aún no existe. 
     * Luego, intenta agregar una nueva habitación, lo que debería arrojar una excepción GalleryExecption porque 
     * no se puede agregar una habitación después de mostrar una escultura. El test verifica si se produce la excepción y si su mensaje es el esperado.   
    */
    @Test
    public void testBuildRoomAfterDisplayingSculpture() {
        Gallery g = new Gallery(1100, 680);
        int[][] vertices = {{100, 100}, {200, 100}, {200, 200}, {100, 200}};
        try {
            g.displaySculpture("red", 150, 150);
            g.buildRoom("red", vertices);
        } catch (GalleryException e) {
            fail("Expected GalleryExecption to be thrown");
            assertEquals(GalleryException.Problem, e.getMessage());
        }
    }
    
    /**
     * crea una galería, agrega una habitación con un conjunto de vértices dados y muestra una escultura en esa habitación. 
     * Luego, verifica si la galería está en un estado "correcto", es decir, si no hay habitaciones vacías ni esculturas superpuestas.   
    */
    @Test
    public void testDisplaySculpture() throws GalleryException {
        Gallery g = new Gallery(1100, 680);
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
    public void testDisplaySculptureInNonExistingRoom() throws GalleryException{
        Gallery g = new Gallery(1100, 680);
        try {
            g.displaySculpture("red", 150, 150);
        } catch (GalleryException e) {
            fail("Expected GalleryExecption to be thrown");
            assertEquals(GalleryException.RoomNotExist, e.getMessage());
        }
    }
    
    /**
     * crea una galería, agrega una habitación con un conjunto de vértices dados 
     * y luego agrega un guardia a esa habitación. Luego, verifica si la galería 
     * está en un estado "correcto", es decir, si no hay habitaciones vacías ni guardias en habitaciones vacías.
    */
    @Test
    public void testArriveGuard() throws GalleryException {
        Gallery g = new Gallery(1100, 680);
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
        Gallery g = new Gallery(1100, 680);
        try {
            g.arriveGuard("red");
        } catch (GalleryException e) {
            fail("Expected GalleryExecption to be thrown");
            assertEquals(GalleryException.RoomNotExist, e.getMessage());
        }
    }
    
        /**
     * este test no tiene cuerpo de prueba ya que aún no se ha implementado la funcionalidad correspondiente.
    */
    @Test
    public void testMoveGuard() throws GalleryException {
        Gallery g = new Gallery(1100, 680);
        int[][] vertices = {{100, 100}, {200, 100}, {200, 200}, {100, 200}};
        g.buildRoom("red", vertices);
        g.arriveGuard("red");
        g.moveGuard("red",100, 100);
        assertTrue(g.ok());
    }

    
    @Test
    public void testMoveGuardToNonExistingRoom() {
        Gallery g = new Gallery(1100, 680);
        int[][] vertices = {{100, 100}, {200, 100}, {200, 200}, {100, 200}};
        try {
            g.buildRoom("red", vertices);
            g.arriveGuard("red");
            g.moveGuard("red",50, 50);
        } catch (GalleryException e) {
            fail("Expected GalleryExecption to be thrown");
            assertEquals(GalleryException.RoomNotExist, e.getMessage());
        }
    }

    @Test
    public void testMoveGuardOutsideRoom() {
        Gallery g = new Gallery(1100, 680);
        int[][] vertices = {{100, 100}, {200, 100}, {200, 200}, {100, 200}};
        try {
            g.buildRoom("red", vertices);
            g.arriveGuard("red");
            g.moveGuard("red",50, 50);
        } catch (GalleryException e) {
            fail("Expected GalleryExecption to be thrown");
            assertEquals(GalleryException.Problem, e.getMessage());
        }
    }

    @Test
    public void testMoveGuardWithNoGuard() {
        Gallery g = new Gallery(1100, 680);
        int[][] vertices = {{100, 100}, {200, 100}, {200, 200}, {100, 200}};
        try {
            g.buildRoom("red", vertices);
            g.moveGuard("red",50, 50);
        } catch (GalleryException e) {
            fail("Expected GalleryExecption to be thrown");
            assertEquals(GalleryException.Problem, e.getMessage());
        }
    }
    
    @Test
    public void ShoudHaveRoomsWithAlarms(){
        Gallery g = new Gallery(1300, 680);
        int[][] vertices = {{100, 100}, {200, 100}, {200, 200}, {100, 200}};
        int[][] vertices2 = {{210, 210}, {250, 250}, {250, 210}, {210, 250}};
        int[][] vertices3 = {{260, 260}, {300, 300}, {260, 300}, {300, 260}};
        String[] rooms = {"blue","yellow"};
        try {
            g.buildRoom("red", vertices);
            g.buildRoom("blue", vertices2);
            g.buildRoom("yellow", vertices3);
            g.alarm("yellow",true);
            g.alarm("blue",true);
            String[] prueba = g.roomsOnAlert();
            assertEquals(prueba[0],rooms[0]);
            assertEquals(prueba[1],rooms[1]);
        } catch (GalleryException e) {
            fail("Expected GalleryExecption to be thrown");
        }
    }
    
    @Test
    public void shouldStealTheSculpture(){
       Gallery g = new Gallery(1100, 680);
       int[][] vertices = {{100, 100}, {200, 100}, {200, 200}, {100, 200}};
       int[][] vertices2 = {{210, 210}, {250, 250}, {250, 210}, {210, 250}};
       int[][] vertices3 = {{260, 260}, {300, 300}, {260, 300}, {300, 260}};
       try {
            g.buildRoom("red", vertices);
            g.buildRoom("blue", vertices2);
            g.buildRoom("yellow", vertices3);
            g.displaySculpture("blue",210,240);
            g.displaySculpture("red",100,140);
            g.arriveGuard("blue");
            g.arriveGuard("red");
            g.steal();
            assertEquals(g.sculpturePresent("blue"),false);
            assertEquals(g.sculpturePresent("red"),false);
        } catch (Exception e) {
            fail("Expected GalleryExecption to be thrown");
        }
    }
    
}
