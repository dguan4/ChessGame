package Board;

import Pieces.*;
import jdk.nashorn.internal.scripts.JO;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;
import javax.swing.border.*;

/**
 * GUI for the Chess Board
 * Chess images taken from Wikipedia: https://en.wikipedia.org/wiki/Chess_piece
 * Code Snippets taken from: http://stackoverflow.com/questions/21077322/create-a-chess-board-with-jpanel
 * @author David Guan 2/9/16
 */
public class ChessGUI {

    /**
     * Gui, the chess gui we're creating here
     * chessBoardSquares, the squares of JButtons that has all the squares
     * chessBoard, the board itself in the GUI
     * turn for whose turn it is. White for true, black for false
     * clicked for whether the piece has been clicked or not
     * firstGame boolean to check whether this game is the first game or not
     * board for the board class
     */
    private final JPanel gui = new JPanel(new BorderLayout(3,3));
    private JButton[][] chessBoardSquares = new JButton[8][8];
    private JPanel chessBoard;
    private Square[][] squares = new Square[8][8];
    public boolean turn = true;
    public boolean clicked = false;
    public boolean firstGame = true;
    public Board board;

    /**
     * Variables for the undo function
     * Pieces to check what has been moved
     * hasMoved boolean to check whether a move has been made. This affects the game loop
     * undoX and undoY to remember the previous x and y locations
     */
    public Piece pieceToMove;
    public boolean hasMoved = false;
    public Piece pieceCaptured;
    public Piece pieceToUnmove;
    public char undoX;
    public int undoY;

    /**
     * These variables are for the scoreboard in the game. Simply increments these when a game is done or not
     * String for the user's names
     * JLabels are for the scores. These are needed as a global since they add onto the score and need to be accessed
     * outside of the function
     */
    public int whiteScore = 0;
    public int blackScore = 0;
    public String whiteName;
    public String blackName;
    JLabel whiteLabel = new JLabel("WHITE: "+whiteScore);
    JLabel blackLabel = new JLabel("BLACK: "+blackScore);

    /**
     * Constructor function to create an initial board with 8x8 squares.
     * Called within the Board initialization function
     */
    public ChessGUI(Board board){
        /**
         * This try-catch found from stackoverflow
         * http://stackoverflow.com/questions/1065691/how-to-set-the-background-color-of-a-jbutton-on-the-mac-os
         * Button background color wasn't working until this was implemented. Not sure why
         **/
        try {
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
        } catch (Exception e) {
            e.printStackTrace();
        }
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));

        //Toolbar for the GUI, this is where all the buttons for new game and everything goes
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        gui.add(toolBar, BorderLayout.PAGE_START);
        JButton newGame = new JButton("New Game");
        newGame.addActionListener(event -> newGame());
        toolBar.add(newGame);
        JButton ngCustom = new JButton("NG Custom");
        ngCustom.addActionListener(event -> ngCustom());
        toolBar.add(ngCustom);
        JButton undo = new JButton("Undo");
        undo.addActionListener(event -> undoMove());
        toolBar.add(undo);
        JButton forfeit = new JButton("Forfeit");
        forfeit.addActionListener(event -> forfeit());
        toolBar.add(forfeit);
        toolBar.addSeparator();
        toolBar.add(whiteLabel);
        toolBar.addSeparator();
        toolBar.add(blackLabel);

        //Create the new chessboard, a 9x9 grid since we need to add in the letters/numbers
        chessBoard = new JPanel(new GridLayout(0, 9));
        chessBoard.setBorder(new LineBorder(Color.BLACK));
        gui.add(chessBoard);
        this.board = board;
        initializeSquares(board);
        addSquaresToBoard();
    }

    /**
     * New game function for creating a normal game. This does not add custom pieces
     * This simply asks both sides if they want to start a new game. If both say Yes
     * start a new game
     */
    private void newGame(){
        int whiteOption = JOptionPane.showConfirmDialog(null, "White, do you want to start a new game?", "Choose", JOptionPane.YES_NO_OPTION);
        int blackOption = JOptionPane.showConfirmDialog(null, "Black, do you want to start a new game?", "Choose", JOptionPane.YES_NO_OPTION);
        boolean custom = false;
        createGame(whiteOption, blackOption, custom);
    }

    /**
     * New Game Custom function for creating a custom game. This adds in custom pieces
     * Replacing pawns with bishopawns and bishops with leapshops
     * The images are simply upside down pieces
     * Simply asks both sides for confirmation and creates another game
     */
    private void ngCustom() {
        int whiteOption = JOptionPane.showConfirmDialog(null, "White, do you want to start a new custom game?", "Choose", JOptionPane.YES_NO_OPTION);
        int blackOption = JOptionPane.showConfirmDialog(null, "Black, do you want to start a new custom game?", "Choose", JOptionPane.YES_NO_OPTION);
        boolean custom = true;
        createGame(whiteOption, blackOption, custom);
    }

    /**
     * Creates the game by first checking the input from both sides. Nothing happens if either says no
     * If it is the first game, decrement the score since we increment later
     * Clear the board and then create a new one, adding both players and pieces
     * Adds the pieces onto the board after onto the gui and updates the scores
     * Resets all the boolean and pieces to its initial configuration
     * @param whiteOption White side's answer
     * @param blackOption Black side's answer
     * @param custom Custom game or not
     */
    private void createGame(int whiteOption, int blackOption, boolean custom) {
        if (whiteOption == JOptionPane.YES_OPTION && blackOption == JOptionPane.YES_OPTION) {
            whiteName = JOptionPane.showInputDialog(null, "Input White Name");
            blackName = JOptionPane.showInputDialog(null, "Input Black Name");
            if (firstGame){
                whiteScore--;
                blackScore--;
                firstGame = false;
            }
            clearBoard();
            board = new Board();
            Player white = new Player("White");
            Player black = new Player("Black");
            board.addPieces(white, custom);
            board.addPieces(black, custom);
            for (Piece piece : board.allPieces) {
                addPieceToButtons(piece);
            }
            whiteScore++;
            blackScore++;
            updateScores();
            turn = true;
            clicked = false;
            pieceCaptured = null;
            pieceToMove = null;
            pieceToUnmove = null;
        }
    }

    /**
     * Simple function to update the scores
     * Sets the text to include the white score number
     */
    private void updateScores() {
        whiteLabel.setText(whiteName+": "+whiteScore);
        blackLabel.setText(blackName+": "+blackScore);
    }

    /**
     * Clears the board by removing all the pieces from it
     */
    private void clearBoard() {
        for (int i = 0; i < chessBoardSquares.length; i++){
            for (int j = 0; j < chessBoardSquares.length; j++){
                chessBoardSquares[i][j].setIcon(null);
            }
        }
    }

    /**
     * Forfeit function.I figured this was short enough that it didn't require a refactored method
     * Asks on the player's turn if they want to forfeit, then increments the opposing player's score counter
     */
    private void forfeit(){
        if (turn){
            int whiteOption = JOptionPane.showConfirmDialog(null, "White, do you want to forfeit the game?", "Choose", JOptionPane.YES_NO_OPTION);
            if (whiteOption == JOptionPane.YES_OPTION){
                blackScore++;
                updateScores();
            }
        }
        else {
            int blackOption = JOptionPane.showConfirmDialog(null, "Black, do you want to forfeit the game?", "Choose", JOptionPane.YES_NO_OPTION);
            if (blackOption == JOptionPane.YES_OPTION){
                whiteScore++;
                updateScores();
            }
        }
    }

    /**
     * Initializes the GUI chessboard with all the squares
     */
    private void initializeSquares(Board board) {
        Insets buttonMargin = new Insets(0, 0, 0, 0);
        for (int i = 0; i < chessBoardSquares.length; i++){
            for (int j = 0; j < chessBoardSquares[i].length; j++){
                ChessButtons button = new ChessButtons((char)(j + 'a'), 8 - i);
                button.setMargin(buttonMargin);
                //Chess images in the Images folder is 50 pixels x 50 pixels
                ImageIcon icon = new ImageIcon(new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB));
                button.setIcon(icon);
                //sets the squares to white or blue based on location
                button.setBackground(board.squares[j][i].getTileColor());
                button.addActionListener(event -> buttonPressed(board, button.getXLoc(), button.getYLoc()));
                chessBoardSquares[j][i] = button;
            }
        }
        squares = board.squares;
    }

    /**
     * Button listener for the board's buttons
     * This function in particular is refactored to use another function, it simply passes in a side and a square
     * @param board board we are playing on
     * @param x new tentative x location
     * @param y new tentative y location
     */
    private void buttonPressed(Board board, char x, int y){
        Square square = board.squares[(char)(x - 'a')][8 - y];
        if (turn){
            String side = "White";
            buttonMovePiece(board, x, y, square, side);
        }
        else {
            String side = "Black";
            buttonMovePiece(board, x, y, square, side);
        }
    }

    /**
     * Moves the piece on the board. This works in two ways:
     * First, it checks if there is a piece on the square within the click and checks if the piece is ours
     * Then it will get that piece and change booleans
     *
     * Next once clicked again, it will check if we can move it and if so, change booleans again
     * Otherwise only change the clicked boolean since it's still our turn
     * @param board board we are playing on
     * @param x new x location to go to
     * @param y new y location to go to
     * @param square square to get/move to
     * @param side side we are on
     */
    private void buttonMovePiece(Board board, char x, int y, Square square, String side) {
        if (square.getPiece() != null && square.getPiece().getPlayerColor().equals(side)){
            if (!clicked){
                hasMoved = false;
                pieceToMove = square.getPiece();
                clicked = !clicked;
            }
        }
        else {
            //returns if we're clicking on another player's piece
            if (square.getPiece() != null && !square.getPiece().getPlayerColor().equals(side) && !clicked){
                JOptionPane.showMessageDialog(null, "It is not your turn");
                return;
            }
            Location newLoc = new Location(x, y);
            //gets the captured piece. Important for undo-ing moves
            for (Piece piece : board.allPieces){
                if (piece.currentLocation.equals(newLoc)){
                    pieceCaptured = piece;
                }
            }
            //once pieceToMove is set, get its location for undo-ing moves
            undoX = pieceToMove.getLocationX();
            undoY = pieceToMove.getLocationY();

            //if this moves properly, then reset all the turns and clicks
            if (board.movePiece(newLoc, pieceToMove)) {
                turn = !turn;
                clicked = !clicked;
                hasMoved = true;
                pieceToUnmove = pieceToMove;
                Player black = new Player("Black");
                if (board.endGame()){
                    JOptionPane.showMessageDialog(null, "Checkmate!");
                }
                else {
                    if (board.inCheckmate(black)){
                        JOptionPane.showMessageDialog(null, "Check!");
                    }
                }
                board.setAllValidMoves("White");
                board.setAllValidMoves("Black");
            }
            // otherwise, we cannot move to this location and display a message saying so
            else {
                JOptionPane.showMessageDialog(null, "You cannot move there");
                clicked = !clicked;
            }
        }
    }

    /**
     * Adds all the initialized squares to the board
     */
    private void addSquaresToBoard() {
        //adds squares in a 9x9 thing to display numbers and letters of the board
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (j == 0){
                    chessBoard.add(new JLabel("" + (8-i), SwingConstants.CENTER));
                    chessBoard.add(chessBoardSquares[j][i]);
                }
                else {
                    chessBoard.add(chessBoardSquares[j][i]);
                }
            }
        }
        //add the last row
        chessBoard.add(new JLabel(""));

        chessBoard.add(new JLabel("A", SwingConstants.CENTER));
        chessBoard.add(new JLabel("B", SwingConstants.CENTER));
        chessBoard.add(new JLabel("C", SwingConstants.CENTER));
        chessBoard.add(new JLabel("D", SwingConstants.CENTER));
        chessBoard.add(new JLabel("E", SwingConstants.CENTER));
        chessBoard.add(new JLabel("F", SwingConstants.CENTER));
        chessBoard.add(new JLabel("G", SwingConstants.CENTER));
        chessBoard.add(new JLabel("H", SwingConstants.CENTER));
    }

    /**
     * Add images to the buttons, depending on the player color
     */
    public final void addPieceToButtons(Piece piece) {
        if (piece.getPlayerColor().equals("White")){
            String color = "White.png";
            addPiecesToOneSide(piece, color);
        }
        else {
            String color = "Black.png";
            addPiecesToOneSide(piece, color);
        }
    }

    /**
     * Adds the pieces to a certain side, depending on the player color
     * @param piece Piece to add
     * @param color Color of the side
     */
    private void addPiecesToOneSide(Piece piece, String color) {
        if (piece instanceof Bishop){
            addSpecificPieceImage(piece, "src/Images/Bishop-"+color);
        }
        if (piece instanceof King){
            addSpecificPieceImage(piece, "src/Images/King-"+color);
        }
        if (piece instanceof Knight){
            addSpecificPieceImage(piece, "src/Images/Knight-"+color);
        }
        if (piece instanceof Pawn){
            addSpecificPieceImage(piece, "src/Images/Pawn-"+color);
        }
        if (piece instanceof Queen){
            addSpecificPieceImage(piece, "src/Images/Queen-"+color);
        }
        if (piece instanceof Rook){
            addSpecificPieceImage(piece, "src/Images/Rook-"+color);
        }
        if (piece instanceof Bishopawn){
            addSpecificPieceImage(piece, "src/Images/Bishopawn-"+color);
        }
        if (piece instanceof Leapshop){
            addSpecificPieceImage(piece, "src/Images/Leapshop-"+color);
        }
    }

    /**
     * Adds the image to the respective piece on their respective button
     * @param piece Piece to add to the image
     * @param color Color of the piece
     */
    private void addSpecificPieceImage(Piece piece, String color){
        ImageIcon icon = new ImageIcon(color);
        chessBoardSquares[piece.getLocationX()-'a'][8-piece.getLocationY()].setIcon(icon);
    }

    /**
     * Deletes the piece off the button
     * Simply sets the icon to null.We handle the board's pieces locations elsewhere
     * @param piece piece to delete off the button
     */
    public void deletePiece(Piece piece){
        chessBoardSquares[piece.getLocationX()-'a'][8-piece.getLocationY()].setIcon(null);
    }

    /**
     * Undo move function. First, we check if we have a piece to unmove.
     * That is, if this is the first turn, there is no move to undo
     * Then it deletes the piece from its current spot, checks if it just captured a piece
     * And moves it back to its old location
     */
    public void undoMove(){
        if (pieceToUnmove != null){
            deletePiece(pieceToUnmove);
            if (pieceCaptured != null && pieceCaptured.getLocationX() == pieceToUnmove.getLocationX() && pieceCaptured.getLocationY() == pieceToUnmove.getLocationY()){
                addPieceToButtons(pieceCaptured);
            }
            pieceToUnmove.setLocation(undoX, undoY);
            addPieceToButtons(pieceToUnmove);
            turn = !turn;
            clicked = false;
        }
    }

    /**
     * Getter function to return the chessBoard
     * @return JComponent which is the chessBoard JPanel
     */
    public final JComponent getChessBoard(){
        return chessBoard;
    }

    /**
     * Getter function to return the gui itself
     * @return JComponent which is the gui
     */
    public final JComponent getGui() {
        return gui;
    }


}
