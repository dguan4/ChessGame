package Board;

import Pieces.Pawn;
import Pieces.Queen;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static java.lang.Thread.sleep;

/**
 * Game Logic class
 * @author David Guan 2/7/16
 */
public class Game {

    /**
     * Main method to start the game logic
     * @param args Argument to main function
     */
    public static void main(String[] args) throws InterruptedException {
        Board board = new Board();
        Player white = new Player("White");
        Player black = new Player("Black");
        board.addPieces(white, false);
        board.addPieces(black, false);
        JFrame window = new JFrame("Chess Game");
        window.add(board.gui.getGui());
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.pack();
        window.setMinimumSize(window.getSize());
        window.setVisible(true);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        board.setAllValidMoves("White");
        board.setAllValidMoves("Black");
        while (true) {
            if (board.gui.hasMoved) {
                board.setAllValidMoves("White");
                board.setAllValidMoves("Black");
            }
        }
    }
}
