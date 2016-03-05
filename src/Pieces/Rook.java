package Pieces;

import Board.Location;
import Board.Piece;
import Board.Player;

/**
 * Bishop class for the piece
 * @author David Guan 2/1/16
 */
public class Rook extends Piece {

    /**
     * Constructor for the Rook piece
     * @param initialLocation Initial location of the Rook
     * @param player Color of the player
     */
    public Rook(Location initialLocation, Player player){
        super(initialLocation, player);
    }

}
