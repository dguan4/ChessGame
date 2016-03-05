package Board;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Test for the Square class
 */
public class SquareTest {

    /**
     * Tests whether a certain piece got put onto a square
     * @throws Exception
     */
    @Test
    public void testGetPiece() throws Exception {
        Location newLoc = new Location('a', 1);
        Player player = new Player("White");
        Piece piece = new Piece(newLoc,player);
        Square square = new Square(newLoc,piece, Color.BLACK);
        assertTrue(square.getPiece().equals(piece));
    }

    /**
     * Tests whether a tile gets the correct color
     * @throws Exception
     */
    @Test
    public void testGetTileColor() throws Exception {
        Location newLoc = new Location('a', 1);
        Square square = new Square(newLoc,null,Color.BLACK);
        assertTrue(square.getTileColor().equals(Color.BLACK));
        assertFalse(square.getTileColor().equals(Color.white));
    }
}