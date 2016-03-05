package Board;

import java.util.ArrayList;
import Pieces.*;

/**
 * A Piece class for the game pieces.
 * @author David Guan 2/1/16
 */
public class Piece {

    /**
     * Location and player of the piece
     * boolean to check if the piece has moved. For Pawns
     */
    protected Location currentLocation;
    public Player player;
    public boolean hasMoved = false;

    /**
     * ArrayList containing the locations of valid moves
     * boardSize to represent the size of the board, change for larger/smaller board
     */
    public ArrayList<Location> currentValidMoves = new ArrayList<>();
    protected int boardSize = 8;

    /**
     * Constructor for the piece object. Puts in the location and player for the piece
     * @param currentLocation Current Location of the piece
     * @param player The player that has control of this piece
     */
    public Piece(Location currentLocation, Player player){
        this.currentLocation = new Location(currentLocation.getX(), currentLocation.getY());
        this.player = new Player(player.getColor());
    }

    /**
     * Get x location for the piece
     * @return char consisting the x location of the piece
     */
    public char getLocationX(){
        return currentLocation.getX();
    }

    /**
     * Get y location for the piece
     * @return int consisting the y location of the piece
     */
    public int getLocationY(){
        return currentLocation.getY();
    }

    /**
     * Sets the new location of the piece
     * @param x New x location of the piece
     * @param y New y location of the piece
     */
    public void setLocation(char x, int y){
        currentLocation.setX(x);
        currentLocation.setY(y);
    }

    /**
     * Get the color of the player controlling this piece
     * @return string which defines the color of the player
     */
    public String getPlayerColor(){
        return player.getColor();
    }

    /**
     * Add the square to the right if it is a valid move
     * @param x current x position
     */
    public void rightXMoves(char x){
        x++;
        if (x < 'a' + boardSize){
            Location nextLocation = new Location(x, getLocationY());
            currentValidMoves.add(nextLocation);
        }
    }

    /**
     * Add the square to the left if it is a valid move
     * @param x current x position
     */
    public void leftXMoves(char x){
        x--;
        if (x >= 'a'){
            Location nextLocation = new Location(x, getLocationY());
            currentValidMoves.add(nextLocation);
        }
    }

    /**
     * Add the square above if it is a valid move
     * @param y current y position
     */
    public void upYMoves(int y){
        y++;
        if (y <= boardSize){
            Location nextLocation = new Location(getLocationX(), y);
            currentValidMoves.add(nextLocation);
        }
    }

    /**
     * Add the square below if it is a valid move
     * @param y current y position
     */
    public void downYMoves(int y){
        y--;
        if (y > 0){
            Location nextLocation = new Location(getLocationX(), y);
            currentValidMoves.add(nextLocation);
        }
    }

    /**
     * Add all the diagonal squares if it is a valid move
     * @param x current x position
     * @param y current y position
     */
    public void diagonalMoves(char x, int y){
        for (int i = y; i < boardSize; i++) {
            x++;
            y++;
            addLocation(x, y);
        }
        x = this.getLocationX();
        y = this.getLocationY();
        for (int i = y; i < boardSize; i++){
            x--;
            y++;
            addLocation(x, y);
        }
        x = this.getLocationX();
        y = this.getLocationY();
        for (int i = y; i > 0; i--){
            x--;
            y--;
            addLocation(x, y);
        }
        x = this.getLocationX();
        y = this.getLocationY();
        for (int i = y; i > 0; i--){
            x++;
            y--;
            addLocation(x, y);
        }
    }

    /**
     * Add surrounding diagonal squares if it is a valid move
     * @param x current x position
     * @param y current y position
     */
    public void singleDiagonalMoves(char x, int y){
        x++;
        y++;
        addLocation(x, y);
        x--;
        x--;
        addLocation(x, y);
        y--;
        y--;
        addLocation(x, y);
        x++;
        x++;
        addLocation(x, y);
    }

    /**
     * Add only the upper corners, for pawns
     * @param x current x position
     * @param y current y position
     */
    public void upDiagonalMoves(char x, int y){
        x++;
        y++;
        addLocation(x, y);
        x--;
        x--;
        addLocation(x, y);
    }

    /**
     * Add only the lower corners, for pawns
     * @param x current x position
     * @param y current y position
     */
    public void downDiagonalMoves(char x, int y){
        x--;
        y--;
        addLocation(x, y);
        x++;
        x++;
        addLocation(x, y);
    }
    /**
     * Adds the location of a move to the valid moves array if it's valid
     * @param x x position
     * @param y y position
     */
    public void addLocation(char x, int y) {
        if (validLocation(x, y)){
            Location nextLocation = new Location(x, y);
            currentValidMoves.add(nextLocation);
        }
    }

    /**
     * Check whether the tentative x and y are valid within the chess board, i.e. not out of bounds
     * @param x tentative x location
     * @param y tentative y location
     * @return true or false based on whether it is valid
     */
    public boolean validLocation(char x, int y){
        if (x >= 'a' && x < 'a' + boardSize){
            if (y > 0 && y <= boardSize){
                return true;
            }
        }
        return false;
    }

    /**
     * Deciding whether the new location is a valid move
     * @param newLocation The tentative new location
     * @return true or false based on the function
     */
    public boolean validMove(Location newLocation){
        for (Location nextLocation : currentValidMoves){
            if (nextLocation.equals(newLocation)){
                return true;
            }
        }
        return false;
    }

    /**
     * Equals operator for Piece class
     * @param other other Piece to compare
     * @return true or false
     */
    @Override
    public boolean equals(Object other){
        if (!(other instanceof Piece)){
            return false;
        }

        Piece that = (Piece) other;
        return this.currentLocation.equals(that.currentLocation);
    }

    /**
     * Updates the valid moves for the piece
     * Note that this doesn't account for other pieces
     * Simply updates through finding instances of classes
     */
    public void updateValidMoves(){
        if (this instanceof Pawn){
            if (this.getPlayerColor().equals("White")) {
                if (!hasMoved){
                    upYMoves(getLocationY());
                    upYMoves(getLocationY()+1);
                    upDiagonalMoves(getLocationX(), getLocationY());
                }
                else {
                    currentValidMoves.clear();
                    upYMoves(getLocationY());
                    upDiagonalMoves(getLocationX(), getLocationY());
                }
            }
            else {
                if (!hasMoved){
                    downYMoves(getLocationY());
                    downYMoves(getLocationY()-1);
                    downDiagonalMoves(getLocationX(), getLocationY());
                }
                else {
                    currentValidMoves.clear();
                    downYMoves(getLocationY());
                    downDiagonalMoves(getLocationX(), getLocationY());
                }
            }
        }
        if (this instanceof Bishop){
            currentValidMoves.clear();
            diagonalMoves(getLocationX(), getLocationY());
        }
        if (this instanceof King){
            currentValidMoves.clear();
            upYMoves(getLocationY());
            downYMoves(getLocationY());
            leftXMoves(getLocationX());
            rightXMoves(getLocationX());
            singleDiagonalMoves(getLocationX(),getLocationY());
        }
        if (this instanceof Queen){
            currentValidMoves.clear();
            for (int i = getLocationY(); i < boardSize; i++){
                upYMoves(i);
            }
            for (int i = getLocationY(); i > 0; i--){
                downYMoves(i);
            }
            for (char i = getLocationX(); i < ('a'+boardSize-1); i++){
                rightXMoves(i);
            }
            for (char i = getLocationX(); i > 'a'; i--){
                leftXMoves(i);
            }
            diagonalMoves(getLocationX(),getLocationY());
        }
        if (this instanceof Rook){
            currentValidMoves.clear();
            for (int i = getLocationY(); i < boardSize; i++){
                upYMoves(i);
            }
            for (int i = getLocationY(); i > 0; i--){
                downYMoves(i);
            }
            for (char i = getLocationX(); i < ('a'+boardSize); i++){
                rightXMoves(i);
            }
            for (char i = getLocationX(); i > 'a'; i--){
                leftXMoves(i);
            }
        }
        if (this instanceof Knight){
            currentValidMoves.clear();
            char x = getLocationX();
            int y = getLocationY();
            x++;
            x++;
            y++;
            addLocation(x, y);
            y++;
            x--;
            addLocation(x, y);
            x--;
            x--;
            addLocation(x, y);
            x--;
            y--;
            addLocation(x, y);
            y--;
            y--;
            addLocation(x, y);
            y--;
            x++;
            addLocation(x, y);
            x++;
            x++;
            addLocation(x, y);
            x++;
            y++;
            addLocation(x, y);
        }
        if (this instanceof Bishopawn){
            currentValidMoves.clear();
            singleDiagonalMoves(getLocationX(), getLocationY());
        }
        if (this instanceof Leapshop){
            currentValidMoves.clear();
            diagonalMoves(getLocationX(),getLocationY());
        }
    }
}
