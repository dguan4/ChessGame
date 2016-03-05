package Board;

import Pieces.Leapshop;
import Pieces.Queen;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for the Piece class
 * @author David Guan 2/2/16
 */
public class PieceTest {

    /**
     * Tests the diagonal moves that gets added to valid moves array
     * @throws Exception
     */
    @Test
    public void testDiagonalMoves() throws Exception {
        Location newLoc = new Location('a', 1);
        Player player = new Player("White");
        Piece piece = new Piece(newLoc, player);
        piece.diagonalMoves(piece.getLocationX(),piece.getLocationY());
        assertEquals(piece.currentValidMoves.get(0).getX(), 'b');
        assertEquals(piece.currentValidMoves.get(0).getY(), 2);
        assertEquals(piece.currentValidMoves.get(1).getX(), 'c');
        assertEquals(piece.currentValidMoves.get(1).getY(), 3);
        assertEquals(piece.currentValidMoves.get(2).getX(), 'd');
        assertEquals(piece.currentValidMoves.get(2).getY(), 4);
        assertEquals(piece.currentValidMoves.get(3).getX(), 'e');
        assertEquals(piece.currentValidMoves.get(3).getY(), 5);
        assertEquals(piece.currentValidMoves.get(4).getX(), 'f');
        assertEquals(piece.currentValidMoves.get(4).getY(), 6);
        assertEquals(piece.currentValidMoves.get(5).getX(), 'g');
        assertEquals(piece.currentValidMoves.get(5).getY(), 7);
        assertEquals(piece.currentValidMoves.get(6).getX(), 'h');
        assertEquals(piece.currentValidMoves.get(6).getY(), 8);
        newLoc.setX('h');
        piece = new Piece(newLoc, player);
        piece.diagonalMoves(piece.getLocationX(), piece.getLocationY());
        assertEquals(piece.currentValidMoves.get(0).getX(), 'g');
        assertEquals(piece.currentValidMoves.get(0).getY(), 2);
        assertEquals(piece.currentValidMoves.get(1).getX(), 'f');
        assertEquals(piece.currentValidMoves.get(1).getY(), 3);
        assertEquals(piece.currentValidMoves.get(2).getX(), 'e');
        assertEquals(piece.currentValidMoves.get(2).getY(), 4);
        assertEquals(piece.currentValidMoves.get(3).getX(), 'd');
        assertEquals(piece.currentValidMoves.get(3).getY(), 5);
        assertEquals(piece.currentValidMoves.get(4).getX(), 'c');
        assertEquals(piece.currentValidMoves.get(4).getY(), 6);
        assertEquals(piece.currentValidMoves.get(5).getX(), 'b');
        assertEquals(piece.currentValidMoves.get(5).getY(), 7);
        assertEquals(piece.currentValidMoves.get(6).getX(), 'a');
        assertEquals(piece.currentValidMoves.get(6).getY(), 8);
    }

    /**
     * Tests the validity of a location object
     * @throws Exception
     */
    @Test
    public void testValidLocation() throws Exception {
        Location newLoc = new Location('a', 1);
        Player player = new Player("White");
        Piece temp = new Piece(newLoc, player);
        assertTrue(temp.validLocation('b', 5));
        assertFalse(temp.validLocation('t', 20));
        assertTrue(temp.validLocation('a', 8));
        assertTrue(temp.validLocation('h', 8));
        assertTrue(temp.validLocation('a', 1));
        assertTrue(temp.validLocation('h', 1));
        assertFalse(temp.validLocation('h', 9));
    }

    /**
     * Tests whether a certain move is valid for a piece
     * @throws Exception
     */
    @Test
    public void testValidMove() throws Exception {
        Board board = new Board();
        Location newLoc = new Location('a', 1);
        Player player = new Player("White");
        Queen temp = new Queen(newLoc, player);
        board.whitePieces.add(temp);
        board.whitePieces.get(0).updateValidMoves();
        newLoc.setX('a');
        newLoc.setY(2);
        assertTrue(board.whitePieces.get(0).validMove(newLoc));
        newLoc.setX('y');
        assertFalse(board.whitePieces.get(0).validMove(newLoc));
    }

    /**
     * Tests whether a piece gets its valid moves array updated
     * @throws Exception
     */
    @Test
    public void testUpdateValidMoves() throws Exception {
        Board board = new Board();
        Location newLoc = new Location('a', 1);
        Player player = new Player("White");
        Queen temp = new Queen(newLoc, player);
        board.whitePieces.add(temp);
        board.whitePieces.get(0).updateValidMoves();
        assertEquals(board.whitePieces.get(0).currentValidMoves.get(0).getX(), 'a');
        assertEquals(board.whitePieces.get(0).currentValidMoves.get(0).getY(), 2);
        assertEquals(board.whitePieces.get(0).currentValidMoves.get(1).getX(), 'a');
        assertEquals(board.whitePieces.get(0).currentValidMoves.get(1).getY(), 3);
        assertEquals(board.whitePieces.get(0).currentValidMoves.get(2).getX(), 'a');
        assertEquals(board.whitePieces.get(0).currentValidMoves.get(2).getY(), 4);
        assertEquals(board.whitePieces.get(0).currentValidMoves.get(3).getX(), 'a');
        assertEquals(board.whitePieces.get(0).currentValidMoves.get(3).getY(), 5);
        assertEquals(board.whitePieces.get(0).currentValidMoves.get(4).getX(), 'a');
        assertEquals(board.whitePieces.get(0).currentValidMoves.get(4).getY(), 6);
        assertEquals(board.whitePieces.get(0).currentValidMoves.get(5).getX(), 'a');
        assertEquals(board.whitePieces.get(0).currentValidMoves.get(5).getY(), 7);
        assertEquals(board.whitePieces.get(0).currentValidMoves.get(6).getX(), 'a');
        assertEquals(board.whitePieces.get(0).currentValidMoves.get(6).getY(), 8);
        Board customBoard = new Board();
        Leapshop leapshop = new Leapshop(newLoc, player);
        newLoc.setX('b');
        newLoc.setY(2);
        temp = new Queen(newLoc, player);
        customBoard.whitePieces.add(leapshop);
        customBoard.whitePieces.add(temp);
        customBoard.whitePieces.get(0).updateValidMoves();
        assertEquals(customBoard.whitePieces.get(0).currentValidMoves.get(0).getX(), 'b');
        assertEquals(customBoard.whitePieces.get(0).currentValidMoves.get(0).getY(), 2);
        assertEquals(customBoard.whitePieces.get(0).currentValidMoves.get(1).getX(), 'c');
        assertEquals(customBoard.whitePieces.get(0).currentValidMoves.get(1).getY(), 3);
        customBoard.setAllValidMoves("White");
        assertNotEquals(customBoard.whitePieces.get(0).currentValidMoves.get(0).getX(), 'b');
        assertNotEquals(customBoard.whitePieces.get(0).currentValidMoves.get(0).getY(), 2);
        assertEquals(customBoard.whitePieces.get(0).currentValidMoves.get(0).getX(), 'c');
        assertEquals(customBoard.whitePieces.get(0).currentValidMoves.get(0).getY(), 3);
    }
}