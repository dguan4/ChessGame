package Board;

import Pieces.*;

import java.awt.Color;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Board class. Used for the board for the chess game
 * @author David Guan 1/31/16
 */
public class Board {

    /**
     * Limits of the chess board. Change these if we want a bigger board
     */
    private final int y = 8;
    private final char x = 'h';

    /**
     * Two dimensional array of the squares.
     */
    public Square[][] squares = new Square[y][y];

    /**
     * Array of the pieces white and black has on the field
     * Public because I will need to access and update it multiple times
     * Saves a lot of trouble if I can simply access it as a public variable
     */
    public ArrayList<Piece> whitePieces = new ArrayList<>();
    public ArrayList<Piece> blackPieces = new ArrayList<>();
    public ArrayList<Piece> allPieces = new ArrayList<>();

    /**
     * ChessGUI class for displaying the gui
     */
    public ChessGUI gui;

    /**
     * Default constructor for creating a Board.
     * Creates and initializes all the squares with no pieces
     */
    public Board(){
        for (int i = 0; i < y; i++){
            for (int j = 0; j < y; j++){
                Color tileColor;
                if ((i % 2 == 1 && j % 2 == 1) || (i % 2 == 0 && j % 2 == 0)){
                    tileColor = Color.WHITE;
                }
                else{
                    tileColor = new Color(0, 51, 102);
                }
                Location squareLocation = new Location((char)('a'+j), y-i);
                squares[i][j] = new Square(squareLocation, null, tileColor);
            }
        }
        //initializes the chess board gui
        gui = new ChessGUI(this);
    }


    /**
     * Method for adding all the pieces to the board
     * @param player Color of the player, determines the positions of the pieces
     * @param custom Boolean to see if this is a custom game or not
     */
    public void addPieces(Player player, boolean custom){
        if (player.getColor().equals("Black")){
            int pawnRow = 7;
            int backRow = 8;
            addAllPieces(pawnRow, backRow, player, blackPieces, custom);

        }
        else{
            int pawnRow = 2;
            int backRow = 1;
            addAllPieces(pawnRow, backRow, player, whitePieces, custom);
        }

    }

    /**
     * Helper function for the addPieces function
     * Adds all the pieces manually by setting their positions and adding to the array
     * @param pawnRow Row of the pawns
     * @param backRow Row of the other pieces
     * @param player Player controlling these pieces
     * @param array Array of pieces to add to
     * @param custom Boolean to see if this is a custom game or not
     */
    private void addAllPieces(int pawnRow, int backRow, Player player, ArrayList<Piece> array, boolean custom){
        if (!custom) {
            Pawn[] pawns = new Pawn[y];
            for (int i = 0; i < 8; i++) {
                Location pieceLocation = new Location((char) ('a' + i), pawnRow);
                pawns[i] = new Pawn(pieceLocation, player);
                array.add(pawns[i]);
            }
        }
        else {
            Bishopawn[] bishopawns = new Bishopawn[y];
            for (int i = 0; i < 8; i++){
                Location pieceLocation = new Location((char) ('a' + i), pawnRow);
                bishopawns[i] = new Bishopawn(pieceLocation, player);
                array.add(bishopawns[i]);
            }
        }
        Location pieceLocation = new Location('a', backRow);
        Rook rook = new Rook(pieceLocation, player);
        pieceLocation.setX('b');
        Knight knight = new Knight(pieceLocation, player);
        pieceLocation.setX('c');
        Bishop bishop = new Bishop(pieceLocation, player);
        Leapshop leapshop = new Leapshop(pieceLocation, player);
        pieceLocation.setX('d');
        Queen queen = new Queen(pieceLocation, player);
        pieceLocation.setX('e');
        King king = new King(pieceLocation, player);
        pieceLocation.setX('f');
        Bishop secondBishop = new Bishop(pieceLocation, player);
        Leapshop secondLeapshop = new Leapshop(pieceLocation, player);
        pieceLocation.setX('g');
        Knight secondKnight = new Knight(pieceLocation, player);
        pieceLocation.setX('h');
        Rook secondRook = new Rook(pieceLocation, player);
        if (custom){
            array.add(leapshop);
            array.add(secondLeapshop);
        }
        else {
            array.add(bishop);
            array.add(secondBishop);
        }
        array.add(rook);
        array.add(knight);
        array.add(queen);
        array.add(king);
        array.add(secondKnight);
        array.add(secondRook);
        allPieces.addAll(array);
        for (Piece piece : array){
            squares[piece.getLocationX() - 'a'][8 - piece.getLocationY()].setPiece(piece);
        }
    }

    /**
     * Function to sanitize all the pieces' valid inputs
     */
    public void setAllValidMoves(String color){
        for (Piece piece : allPieces){
            piece.updateValidMoves();
        }
        if (color.equals("White")) {
            ArrayList<Piece> mine = whitePieces;
            ArrayList<Piece> theirs = blackPieces;
            sanitizeInput(mine, theirs);
        }
        else {
            ArrayList<Piece> mine = blackPieces;
            ArrayList<Piece> theirs = whitePieces;
            sanitizeInput(mine, theirs);
        }
    }

    /**
     * Helper function to sanitize valid moves of all the pieces
     * First this function goes through the valid moves of all the player's pieces, i.e. mine
     *
     * It checks for King since Kings have special methods such that it cannot move to a location that it will be in check
     * Then it checks the rest of the pieces, including the King's, to see if any of our own pieces occupy our valid move set
     *
     * Afterwards, for pieces that cover move than just squares surrounding it, i.e. riders, it sanitizes those to make sure the piece
     * doesn't jump pass the pieces.
     * @param mine The array of the player's pieces
     * @param theirs The array of the opponent's pieces
     */
    private void sanitizeInput(ArrayList<Piece> mine, ArrayList<Piece> theirs) {
        ArrayList<Location> removeLoc = new ArrayList<>();
        for (Piece all : mine) {
            for (Iterator<Location> it = all.currentValidMoves.iterator(); it.hasNext(); ) {
                Location tempLocation = it.next();
                if (all instanceof King){
                    for (Piece other : theirs){
                        for (Location moves : other.currentValidMoves){
                            if (tempLocation.equals(moves)){
                                //it.remove();
                                removeLoc.add(tempLocation);
                            }
                        }
                    }
                }
            }
            all.currentValidMoves.removeAll(removeLoc);
            removeLoc.clear();
            for (Iterator<Location> it = all.currentValidMoves.iterator(); it.hasNext(); ) {
                Location tempLocation = it.next();
                for (Piece other : mine) {
                    if (other.currentLocation.equals(tempLocation) && !other.equals(all)) {
                        //it.remove();
                        removeLoc.add(tempLocation);
                    }
                }
            }
            all.currentValidMoves.removeAll(removeLoc);
            removeLoc.clear();
            for (Piece other : mine){
                if (!all.equals(other)) {
                    sanitizeQBR(all, other);
                }
            }
            for (Piece other : theirs){
                sanitizeQBR(all, other);
            }
            all.currentValidMoves.trimToSize();
        }

    }

    /**
     * Sanitizes the Queen, Bishop, and Rook pieces since all other pieces move directly to a location and is a rider
     * These pieces shouldn't be able to jump over pieces which is what this function checks and sanitizes properly
     * @param all All the pieces of the player
     * @param other Other player's pieces
     */
    private void sanitizeQBR(Piece all, Piece other) {
        //added an arraylist to get rid of invalid moves
        ArrayList<Location> removeLoc = new ArrayList<>();
        if (all instanceof Queen || all instanceof Rook){
            for (ListIterator<Location> it2 = all.currentValidMoves.listIterator(); it2.hasNext();){
                Location otherLoc = it2.next();
                //if we have the same X, check the Y locations to see if my valid moves are in that direction
                //basically checks if i have moves that go past a piece
                if (other.currentLocation.sameX(otherLoc)){
                    if ((all.getLocationY() < other.getLocationY() && otherLoc.getY() > other.getLocationY()) || (all.getLocationY() > other.getLocationY() && otherLoc.getY() < other.getLocationY())){
                        //it2.remove();
                        removeLoc.add(otherLoc);
                    }
                }

                //similar idea here to check if we have the same Y
                if (other.currentLocation.sameY(otherLoc)){
                    if ((all.getLocationX() < other.getLocationX() && otherLoc.getX() > other.getLocationX()) || (all.getLocationX() > other.getLocationX() && otherLoc.getX() < other.getLocationX())) {
                        //it2.remove();
                        removeLoc.add(otherLoc);
                    }
                }
            }
            all.currentValidMoves.removeAll(removeLoc);
            removeLoc.clear();
        }

        if (all instanceof Queen || all instanceof Bishop){
            for (Iterator<Location> it2 = all.currentValidMoves.iterator(); it2.hasNext();){
                Location otherLoc = it2.next();
                //these are simply checking if the locations ore in a diagonal
                //couldn't really find a way to refactor this out nicely so i left it
                if (other.currentLocation.sameDiag(all.currentLocation)) {
                    if (other.currentLocation.upRight(all.currentLocation)) {
                        if (otherLoc.upRight(other.currentLocation)) {
                            //it2.remove();
                            removeLoc.add(otherLoc);
                        }
                    }
                    if (other.currentLocation.downRight(all.currentLocation)) {
                        if (otherLoc.downRight(other.currentLocation)) {
                            //it2.remove();
                            removeLoc.add(otherLoc);
                        }
                    }
                    if (other.currentLocation.upLeft(all.currentLocation)) {
                        if (otherLoc.upLeft(other.currentLocation)) {
                            //it2.remove();
                            removeLoc.add(otherLoc);
                        }
                    }
                    if (other.currentLocation.downLeft(all.currentLocation)) {
                        if (otherLoc.downLeft(other.currentLocation)) {
                            //it2.remove();
                            removeLoc.add(otherLoc);
                        }
                    }
                }
            }
            all.currentValidMoves.removeAll(removeLoc);
            removeLoc.clear();
        }
    }

    /**
     * Determines whether the player's King is in check
     * @param player Current player
     * @return true or false determining if in check
     */
    public boolean inCheck(Player player){
        if (player.getColor().equals("White")){
            return checkHelper(whitePieces, blackPieces);
        }
        return checkHelper(blackPieces, whitePieces);
    }

    /**
     * Helper function for inCheck
     * Simply checks all the opponent's current valid moves in their pieces and if it can find one such that
     * the King is being attacked, then it is a check
     * Note: A check is a checkmate as well so this function doesn't differentiate that
     * @param player Current player's pieces
     * @param other Other player's pieces
     * @return true or false
     */
    private boolean checkHelper(ArrayList<Piece> player, ArrayList<Piece> other){
        for (Piece pieces : player){
            if (pieces instanceof King){
                for (Piece otherPieces : other){
                    for (Location location : otherPieces.currentValidMoves){
                        if (pieces.currentLocation.equals(location)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Function to determine if the player is in checkmate
     * First checks if the player is in check before checking for checkmate
     * @param player Current player
     * @return true or false determining checkmate
     */
    public boolean inCheckmate(Player player){
        if (inCheck(player)){
            if (player.getColor().equals("White")){
                ArrayList<Piece> mine = whitePieces;
                return checkmateHelp(player, mine);
            }
            else {
                ArrayList<Piece> mine = blackPieces;
                return checkmateHelp(player, mine);
            }
        }
        return false;
    }

    /**
     * Helper function for the checkmate function
     * Firstly it checks if the King piece has any possible moves and if it is the only piece left
     * Otherwise, if it can still move to another spot, check if this spot will make the King be in Check
     * Otherwise, see if another piece can move to block this
     * @param player Color of the player
     * @param mine The array of pieces of said color
     * @return true or false
     */
    private boolean checkmateHelp(Player player, ArrayList<Piece> mine) {
        for (Piece piece : mine){
            if (piece instanceof King){
                if (piece.currentValidMoves.size() == 0 && mine.size() == 1){
                    return true;
                }
                else {
                    for (Location loc : piece.currentValidMoves){
                        char x = piece.getLocationX();
                        int y = piece.getLocationY();
                        piece.setLocation(loc.getX(), loc.getY());
                        if (!inCheck(player)){
                            return false;
                        }
                        piece.setLocation(x, y);
                    }
                }
                for (Location loc : piece.currentValidMoves){
                    char x = piece.getLocationX();
                    int y = piece.getLocationY();
                    piece.setLocation(loc.getX(), loc.getY());
                    if (!inCheck(player)){
                        return false;
                    }
                    piece.setLocation(x, y);
                }
            }
        }
        return true;
    }

    /**
     * Stalemate function to check if the player is in a stalemate
     * @param player Player to check
     * @return true or false
     */
    public boolean stalemate(Player player){
        if (!inCheck(player)) {
            if (whitePieces.size() == 1 && blackPieces.size() == 1){
                return true;
            }
            if (whitePieces.size() == 1 && blackPieces.size() != 1 || whitePieces.size() != 1 && blackPieces.size() == 1)
            if (player.getColor().equals("White")) {
                ArrayList<Piece> mine = whitePieces;
                return stalemateHelp(mine);
            }
            ArrayList<Piece> mine = blackPieces;
            return stalemateHelp(mine);
        }
        return false;
    }

    /**
     * Helper function for the stalemate function
     * Checks if I only have one piece left, if so, then if my King cannot move then it's a stalemate
     * Otherwise if there is a piece left that can be moved, then not in stalemate
     * @param mine Array of pieces of the player
     * @return true or false
     */
    private boolean stalemateHelp(ArrayList<Piece> mine) {
        if (mine.size() == 1){
            return mine.get(0).currentValidMoves.size() == 0;
        }
        else {
            for (Piece piece : mine){
                if (piece.currentValidMoves.size() != 0){
                    return false;
                }
            }
            return true;
        }
    }


    /**
     * Moves the piece to a new location
     * First checks if the move is a valid move before doing anything by comparing to the valid moves array
     * @param newLocation Location that the piece is trying to go to
     * @param piece Piece that is trying to move
     * @return true or false
     */
    public boolean movePiece(Location newLocation, Piece piece){
        if (piece.validMove(newLocation)){
            if (piece.getPlayerColor().equals("White")) {
                ArrayList<Piece> mine = whitePieces;
                ArrayList<Piece> their = blackPieces;
                return movePieceHelp(newLocation, piece, mine, their);
            }
            else{
                ArrayList<Piece> mine = blackPieces;
                ArrayList<Piece> their = whitePieces;
                return movePieceHelp(newLocation, piece, mine, their);
            }
        }
        return false;
    }

    /**
     * Helper function of the movePiece function
     * Checks if the pawn is trying to capture a move diagonally
     * Else simply checks if the new location that it's trying to move to is the same as its valid move
     * @param newLocation new location to move to
     * @param piece piece trying to move
     * @param mine The array of pieces of the piece trying to move
     * @param their The other array of pieces
     * @return true or false
     */
    private boolean movePieceHelp(Location newLocation, Piece piece, ArrayList<Piece> mine, ArrayList<Piece> their) {
        if (piece instanceof Pawn){
            for (Piece other : their) {
                if (newLocation.isAbove(piece.currentLocation) && newLocation.equals(other.currentLocation)) {
                    squares[piece.getLocationX() - 'a'][8 - piece.getLocationY()].setPiece(null);
                    gui.deletePiece(piece);
                    piece.setLocation(newLocation.getX(), newLocation.getY());
                    squares[piece.getLocationX() - 'a'][8 - piece.getLocationY()].setPiece(piece);
                    gui.addPieceToButtons(piece);
                    return true;
                } else {
                    if (newLocation.isBelow(piece.currentLocation) && newLocation.equals(other.currentLocation)) {
                        squares[piece.getLocationX() - 'a'][8 - piece.getLocationY()].setPiece(null);
                        gui.deletePiece(piece);
                        piece.setLocation(newLocation.getX(), newLocation.getY());
                        squares[piece.getLocationX() - 'a'][ 8- piece.getLocationY()].setPiece(piece);
                        gui.addPieceToButtons(piece);
                        return true;
                    }
                }
            }
            /**if (newLocation.isAbove(piece.currentLocation) || newLocation.isBelow(piece.currentLocation)){
                if (newLocation.sameDiag(piece.currentLocation)){
                    return false;
                }
            }**/
        }
        for (Piece myPiece : mine) {
            if (newLocation.equals(myPiece.currentLocation)) {
                return false;
            }
        }
        squares[piece.getLocationX() - 'a'][8 - piece.getLocationY()].setPiece(null);
        gui.deletePiece(piece);
        piece.setLocation(newLocation.getX(), newLocation.getY());
        squares[piece.getLocationX() - 'a'][8 - piece.getLocationY()].setPiece(piece);
        gui.addPieceToButtons(piece);
        return true;
    }

    /**
     * Simple end game function that checks whether the game has ended or not. Returns a boolean determining whether
     * the game has ended or not and increments the score counter
     * @return true or false
     */
    public boolean endGame() {
        Player white = new Player("White");
        Player black = new Player("Black");
        if (inCheckmate(white)){
            gui.blackScore++;
            return true;
        }
        else if(inCheckmate(black)){
            gui.whiteScore++;
            return true;
        }
        return false;


    }

}
