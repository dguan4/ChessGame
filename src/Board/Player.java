package Board;

/**
 * Player class for the players
 * @author David Guan 2/1/16
 */
public class Player {

    /**
     * Color of the player
     */
    private String color;

    /**
     * Constructor for the player.
     * @param color The color of the player, white or black
     */
    public Player(String color){
        this.color = color;
    }

    /**
     * Getter for the player color
     * @return String consisting the color
     */
    public String getColor(){
        return color;
    }

}
