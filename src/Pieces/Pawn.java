package Pieces;

import Board.Location;
import Board.Piece;
import Board.Player;

/**
 * Bishop class for the piece
 * @author David Guan 2/1/16
 */
public class Pawn extends Piece {

    /**
     * Constructor for the Pawn piece
     * @param initialLocation Initial location of the Pawn
     * @param player Color of the player
     */
    public Pawn(Location initialLocation, Player player){
        super(initialLocation, player);
    }

}

