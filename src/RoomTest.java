
import static org.junit.Assert.*;
import org.junit.Test;

public class RoomTest {
    
    @Test
    public void testBuildWalls() {
        // Create a room with a square shape
        int[][] walls = {{0, 0}, {10, 0}, {10, 10}, {0, 10}};
        Room room = new Room("white", walls, 100);
        
        // Build the walls
        room.buildWalls(walls);
        
        // Check that the room has the expected number of walls
        assertEquals(4, room.lines.size());
        
        // Check that each wall has the expected start and end points
        assertEquals(0, room.lines.get(0).getX1());
        assertEquals(100, room.lines.get(0).getY1());
        assertEquals(10, room.lines.get(0).getX2());
        assertEquals(100, room.lines.get(0).getY2());
        
        assertEquals(10, room.lines.get(1).getX1());
        assertEquals(100, room.lines.get(1).getY1());
        assertEquals(10, room.lines.get(1).getX2());
        assertEquals(90, room.lines.get(1).getY2());
        
        assertEquals(10, room.lines.get(2).getX1());
        assertEquals(90, room.lines.get(2).getY1());
        assertEquals(0, room.lines.get(2).getX2());
        assertEquals(90, room.lines.get(2).getY2());
        
        assertEquals(0, room.lines.get(3).getX1());
        assertEquals(90, room.lines.get(3).getY1());
        assertEquals(0, room.lines.get(3).getX2());
        assertEquals(100, room.lines.get(3).getY2());
    }
}