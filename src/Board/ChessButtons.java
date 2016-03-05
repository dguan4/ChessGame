package Board;

import javax.swing.*;

/**
 * ChessButtons class for all the board's buttons
 * Creating this class that extends the JButton made things easier because I was able to store
 * the location of the button as well.
 * @author David Guan 2/16/16
 */
public class ChessButtons extends JButton {

    /**
     * x and y locations for the board
     */
    private char x;
    private int y;

    /**
     * Constructor class for the chess buttons
     * @param x x location
     * @param y y location
     */
    public ChessButtons (char x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x location of this button
     * @return char x
     */
    public char getXLoc() {
        return x;
    }

    /**
     * Returns the y location of this button
     * @return int y
     */
    public int getYLoc() {
        return y;
    }
}
