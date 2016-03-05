package Pieces;

import Board.Location;
import Board.Piece;
import Board.Player;

/**
 * Bishop class for the piece
 * @author David Guan 2/1/16
 */
public class Queen extends Piece {

    /**
     * Constructor for the Queen piece
     * @param initialLocation Initial location of the Queen
     * @param player Color of the player
     */
    public Queen(Location initialLocation, Player player){
        super(initialLocation, player);
    }

}

