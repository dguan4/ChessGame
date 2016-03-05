package Pieces;

import Board.Location;
import Board.Piece;
import Board.Player;

/**
 * Bishopawn Class
 * This pawn only moves and captures diagonally and is a combination of a Bishop and Pawn
 * Replaces all the pawns on both sides if used
 * @author David Guan 2/10/16
 */
public class Bishopawn extends Piece {

    /**
     * Constructor for the Diagonal Pawn
     * @param initialLocation initial location of the pawn
     * @param player Color of the player
     */
    public Bishopawn(Location initialLocation, Player player) {
        super(initialLocation, player);
    }
}
