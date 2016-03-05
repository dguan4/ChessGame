package Board;

/**
 * Class for the location of the pieces and squares
 * @author David Guan 2/1/16
 */
public class Location {

    /**
     * Location of the piece. Note that y is int and x is char
     */
    private int y;
    private char x;

    /**
     * Constructor for the location of a piece or square
     * @param x The x location which goes from a-h
     * @param y The y location which goes from 1-8
     */
    public Location(char x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for the x location
     * @return The x location of the piece or square
     */
    public char getX(){
        return x;
    }

    /**
     * Setter for the x location
     * @param x The new x location of the piece or square
     */
    public void setX(char x){
        this.x = x;
    }

    /**
     * Getter for the y location
     * @return The y location of the piece or square
     */
    public int getY(){
        return y;
    }

    /**
     * Setter for the y location
     * @param y The new y location of the piece or square
     */
    public void setY(int y){
        this.y = y;
    }

    /**
     * Overloaded equals operator that checks whether two locations are the same
     * @param other Other location
     * @return true or false depending on if the location is the same
     */
    @Override
    public boolean equals(Object other){
        if (!(other instanceof Location)) {
            return false;
        }

        Location that = (Location) other;
        return (this.x == that.x && this.y == that.y);
    }

    /**
     * Figures out if another location has the same x location
     * @param other Other location to be compared to
     * @return true or false
     */
    public boolean sameX(Location other){
        return this.getX() == other.getX();
    }

    /**
     * Figures out if another location has the same y location
     * @param other Other location to be compared to
     * @return true or false
     */
    public boolean sameY(Location other){
        return this.getY() == other.getY();
    }

    /**
     * Figures out the direction if piece is in diagonal
     * @param other Other location to be compared to
     * @return true or false
     */
    public boolean sameDiag(Location other){
        return Math.abs(this.getX() - other.getX()) == Math.abs(this.getY() - other.getY());
    }

    /**
     * Determines if the other location is to the diagonal to the up right
     * @param other Other location to compare to
     * @return true or false
     */
    public boolean upRight(Location other){
        return this.getX() > other.getX() && this.getY() > other.getY();
    }

    /**
     * Determines if the other location is to the diagonal to the up left
     * @param other Other location to compare to
     * @return true or false
     */
    public boolean upLeft(Location other){
        return this.getX() < other.getX() && this.getY() > other.getY();
    }

    /**
     * Determines if the other location is to the diagonal to the down left
     * @param other Other location to compare to
     * @return true or false
     */
    public boolean downLeft(Location other){
        return this.getX() < other.getX() && this.getY() < other.getY();
    }

    /**
     * Determines if the other location is to the diagonal to the down right
     * @param other Other location to compare to
     * @return true or false
     */
    public boolean downRight(Location other){
        return this.getX() > other.getX() && this.getY() < other.getY();
    }

    /**
     * Simple check to see if this location is directly above
     * @param other other location
     * @return true or false
     */
    public boolean isAbove(Location other){
        return this.y + 1 == other.y;
    }

    /**
     * Simple check to see if this location is directly below
     * @param other other location
     * @return true or false
     */
    public boolean isBelow(Location other){
        return this.y - 1 == other.y;
    }
}

