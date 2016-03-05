package Board;

import java.awt.*;

/**
 * Square class for the board
 * @author David Guan 1/31/16
 */
public class Square {

    /**
     * Location of the squares and current piece, if any, on the square
     */
    private Location currentLocation;
    private Piece piece;
    private Color tileColor;

    /**
     * Constructor for the location of the squares of the board.
     * @param currentLocation The location of the square
     * @param piece The piece on the square. This can be NULL
     * @param tileColor The color of the tile
     */
    public Square(Location currentLocation, Piece piece, Color tileColor){
        this.currentLocation = new Location(currentLocation.getX(), currentLocation.getY());
        this.piece = piece;
        this.tileColor = tileColor;
    }

    /**
     * Getter for the piece
     * @return the piece object on the square. May return NULL
     */
    public Piece getPiece(){
        return piece;
    }

    /**
     * Setter for the piece. Changes throughout the game
     * @param piece The current piece on the square. Can be set to NULL
     */
    public void setPiece(Piece piece){
        this.piece = piece;
    }

    /**
     * Gets the color of the tile, which might be needed when creating the GUI
     * @return String containing the tile color
     */
    public Color getTileColor(){
        return tileColor;
    }



}
