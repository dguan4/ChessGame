package Board;

import Pieces.Pawn;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.*;

/**
 * Test Plan for the Chess GUI
 * @author David Guan 2/10/16
 */
public class ChessGUITest {

    /**
     * Test adding pieces to certain buttons
     * Testers should test if the pieces are adding to their respective locations
     * Such as Queen being on the right color, pieces being on the right color
     * @throws Exception
     */
    @Test
    public void testAddPieceToButtons() throws Exception {

    }

    /**
     * Test to see the location of pieces after a move
     * Testers should try to interact with the GUI and move a piece
     * i.e. pawn to e4. White should be the only player to make this move
     * so testers have to disallow black to make any moves in this test
     * @throws Exception
     */
    @Test
    public void testGuiAfterOneMove() throws Exception {

    }

    /**
     * Pieces should show up in their respective rows and columns
     * i.e. Black on rows 7 and 8 and White on rows 1 and 2
     * Queen should be on color and have bishops next to the King and Queen
     *
     * After one move, should test to make sure the board changes like so: http://i2.365chess.com/images/pieces/alpha/50/wp.png
     *
     */


    /**
     * Test winlose conditions. If the player presses forfeit, a button should pop up saying the other side wins
     * Similarly, if someone checkmates, then one side should win
     * If there is a stalemate, then there should be a draw button or indicator
     */

    /**
     * Capturing pieces should have an indicator as well. Perhaps a capture sound or visuals. Test that there is something
     * happening upon capturing a piece, or perhaps display it to the side of the GUI
     */

    /**
     * Clicking a new game should start a new game but there should be clear consensus on both sides. Players
     * need to click yes to accept a new game and keep score check. There should be a score counter somewhere in the GUI
     * that updates as players win/lose.
     */

    /**
     * Test that opposing player cannot make moves during the player's turn. Should be an indicator saying
     * that it is not this player's turn yet.
     */
}