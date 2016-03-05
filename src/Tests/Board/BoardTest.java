package Board;

import Pieces.*;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Board test for the board class
 * @author David Guan 2/2/16
 */
public class BoardTest {

    /**
     * Global chess board and custom board with custom pieces
     */
    private static Board chessBoard;
    private static Board customBoard;

    /**
     * Initialize the board before doing anything
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        chessBoard = new Board();
        Player white = new Player("White");
        Player black = new Player("Black");
        chessBoard.addPieces(white, false);
        chessBoard.addPieces(black, false);
        customBoard = new Board();
        customBoard.addPieces(white, true);
        customBoard.addPieces(black, true);
    }

    /**
     * Test to add pieces in the class
     */
    @Test
    public void testAddPieces() {
        ArrayList<Piece> white1 = chessBoard.whitePieces;
        ArrayList<Piece> black1 = chessBoard.blackPieces;
        assertEquals(white1.size(), 16);
        assertEquals(black1.size(), 16);
        assertEquals(customBoard.whitePieces.size(), 16);
        assertEquals(customBoard.blackPieces.size(), 16);
        Player white = new Player("White");
        Location initialLocation = new Location('e', 1);
        King king1 = new King(initialLocation, white);
        for (Piece piece : white1){
            if (piece instanceof King){
                assertEquals('e' ,piece.getLocationX());
                assertEquals(1, piece.getLocationY());
                assertTrue(king1.equals(piece));
            }
            if (piece instanceof Queen){
                assertEquals('d', piece.getLocationX());
                assertEquals(1, piece.getLocationY());
            }
        }
        for (Piece piece : customBoard.whitePieces){
            if (piece instanceof Bishopawn){
                assertEquals(2, piece.getLocationY());
            }
            if (piece instanceof Leapshop){
                assertNotEquals(2, piece.getLocationY());
            }
        }
    }

    /**
     * Test after adding pieces to test valid moves of the pieces
     */
    @After
    public void testSetAllValidMoves() {
        chessBoard.allPieces.get(0).upYMoves(2);
        ArrayList<Location> locations = chessBoard.allPieces.get(0).currentValidMoves;
        Location tempLocation = new Location('a', 3);
        Player white = new Player("White");
        Pawn tempPawn = new Pawn(tempLocation, white);
        chessBoard.allPieces.add(tempPawn);
        chessBoard.setAllValidMoves(white.getColor());
        assertFalse(locations==chessBoard.allPieces.get(11).currentValidMoves);
    }

    /**
     * Test to see if the King is in check
     */
    @Test
    public void testInCheck() {
        Board tempBoard = new Board();
        Location tempLoc = new Location('a', 1);
        Player white = new Player("White");
        Player black = new Player("Black");
        Queen tempQueen = new Queen(tempLoc, white);
        tempLoc.setX('b');
        tempLoc.setY(2);
        King tempKing = new King(tempLoc, black);
        tempBoard.whitePieces.add(tempQueen);
        tempBoard.blackPieces.add(tempKing);
        tempBoard.whitePieces.get(0).updateValidMoves();
        tempBoard.blackPieces.get(0).updateValidMoves();
        assertTrue(tempBoard.inCheck(black));
    }

    /**
     * Test to see if the King is in checkmate
     */
    @Test
    public void testInCheckmate() {
        Board tempBoard = new Board();
        Location tempLoc = new Location('a', 1);
        Player white = new Player("White");
        Player black = new Player("Black");
        King tempKing = new King(tempLoc, black);
        tempLoc.setX('b');
        Queen tempQueen = new Queen(tempLoc, white);
        tempLoc.setX('c');
        Rook tempRook = new Rook(tempLoc, white);
        tempBoard.whitePieces.add(tempQueen);
        tempBoard.whitePieces.add(tempRook);
        tempBoard.blackPieces.add(tempKing);
        tempBoard.whitePieces.get(0).updateValidMoves();
        tempBoard.whitePieces.get(1).updateValidMoves();
        tempBoard.blackPieces.get(0).updateValidMoves();
        tempBoard.setAllValidMoves(black.getColor());
        tempBoard.setAllValidMoves(white.getColor());
        assertTrue(tempBoard.inCheckmate(black));
    }

    /**
     * Test to see if the king is in stalemate
     */
    @Test
    public void testStalemate() {
        Board tempBoard = new Board();
        Player white = new Player("White");
        Player black = new Player("Black");
        Location tempLoc = new Location('a', 1);
        King tempKing = new King(tempLoc, black);
        tempLoc.setX('c');
        tempLoc.setY(2);
        Queen tempQueen = new Queen(tempLoc, white);
        tempLoc.setY(5);
        Pawn tempPawn = new Pawn(tempLoc, white);
        tempBoard.whitePieces.add(tempQueen);
        tempBoard.blackPieces.add(tempKing);
        tempBoard.whitePieces.add(tempPawn);
        tempBoard.whitePieces.get(0).updateValidMoves();
        tempBoard.blackPieces.get(0).updateValidMoves();
        tempBoard.setAllValidMoves(white.getColor());
        tempBoard.setAllValidMoves(black.getColor());
        assertTrue(tempBoard.stalemate(black));
    }

    /**
     * Test to see if you can move a piece
     */
    @Test
    public void testMovePiece()  {
        Board tempBoard = new Board();
        Player white = new Player("White");
        Player black = new Player("Black");
        tempBoard.addPieces(white, false);
        tempBoard.addPieces(black, false);
        tempBoard.whitePieces.get(0).updateValidMoves();
        tempBoard.setAllValidMoves("White");
        Location tempLocation = new Location('a', 3);
        tempBoard.movePiece(tempLocation, tempBoard.whitePieces.get(0));
        assertTrue(tempBoard.whitePieces.get(0).currentLocation.equals(tempLocation));
        tempLocation.setY(2);
        tempBoard.movePiece(tempLocation, tempBoard.whitePieces.get(0));
        assertFalse(tempBoard.whitePieces.get(0).currentLocation.equals(tempLocation));
    }
}