package Pieces;

import Board.Location;
import Board.Piece;
import Board.Player;

/**
 * Leapshop class
 * This piece can leap over pieces in a diagonal position
 * Basically a Bishop that can leap, a fairly strong piece
 * @author David Guan 2/10/16
 */
public class Leapshop extends Piece {

    /**
     * Constructor for the Leapshop piece
     * @param initialLocation Leapshop's initial location
     * @param player Color of the player
     */
    public Leapshop(Location initialLocation, Player player){
        super(initialLocation, player);
    }
}
