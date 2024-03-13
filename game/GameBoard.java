package game;
import java.util.ArrayList;

public class GameBoard {
    public static final int EMPTY = 0;
    public static final int BLACK = 1;
    public static final int WHITE = 2;

    public static final int boardLength = 8;

    private int countEmpty = 0;
    private int countBlack = 0;
    private int countWhite = 0;
    
    private int[][] board;

    /**
     * Initialises a GameBoard instance in its initial state.
     */
    GameBoard(){
        board = new int[8][8];
        board[3][3] = WHITE;
        board[3][4] = BLACK;
        board[4][3] = BLACK;
        board[4][4] = WHITE;
        updateCount();
    }

    /**
     * Initialises a GameBoard instance in the specified state.
     * @param loadedBoard The board state to instantiate from.
     * @throws Exception If there exists invalid colour values.
     */
    GameBoard(int[][] loadedBoard) throws Exception{
        board = new int[8][8];
        for (int yPos = 0; yPos < boardLength; yPos++) {
            for (int xPos = 0; xPos < boardLength; xPos++) {
                if(ColourCheck(loadedBoard[xPos][yPos])){
                    throw new Exception("Invalid piece colour");        // piece check
                }
                else
                    board[xPos][yPos] = loadedBoard[xPos][yPos];
            }
        }
        updateCount();
    }

    /**
     * Attempts to set a piece with specified coordinates and colour.
     * @param xPos X position of the piece to be set.
     * @param yPos Y position of the piece to be set.
     * @param colour Colour of the piece to be set
     */
    public void SetPiece(int xPos, int yPos, int colour){
        board[xPos][yPos] = colour;
    }

    /**
     * Checks colour of piece at specified coordinates.
     * @param xPos X position of the coordinates to be checked.
     * @param yPos Y position of the coordinates to be checked.
     * @return colour of the piece at the specified coordinates.
     */
    public int GetPiece(int xPos, int yPos){
        return board[xPos][yPos];
    }

    /**
     * Returns a copy of current board state.
     * @return deep copy of board state.
     */
    public int[][] GetBoard(){
        int[][] copy = new int[8][8];
        for(int y = 0; y < boardLength; y++){
            for(int x = 0; x < boardLength; x++){
                copy[x][y] = board[x][y];
            }
        }
        return copy;
    }

    public int getCountEmpty(){
        return countEmpty;
    }
    public int getCountBlack(){
        return countBlack;
    }
    public int getCountWhite(){
        return countWhite;
    }
    
    /**
     * Checks validity of colour.
     * @param colour Colour to be checked
     * @return Validity of colour.
     */
    public static boolean ColourCheck(int colour){
        return (colour < 0 || colour > 2);
    }

    /**
     * Checks validity of the specified coordinates.
     * @param xPos X position of the coordinates to be checked.
     * @param yPos Y position of the coordinates to be checked.
     * @return Validity of the coordinates.
     */
    public static boolean OutOfBoundsCheck(int xPos, int yPos){
        return (xPos < 0 || xPos > boardLength - 1 || yPos < 0 || yPos > boardLength - 1);
    }

    public ArrayList<int[]> getLegalMoves(int colour){
        ArrayList<int[]> legalMoves = new ArrayList<int[]>();
        for(int i = 0; i < boardLength; i++){
            for(int j = 0; j < boardLength; j++){
                if(board[i][j] == EMPTY){
                    if(!_aggregateCheckerList(i, j, colour).isEmpty())
                        legalMoves.add(new int[] {i, j});
                }
            }
        }
        return legalMoves;
    }

    /**
     * Attempts to put a piece with specified position and colour.
     * 
     * @param xPos X position of piece to be placed.
     * @param yPos Y position of piece to be placed.
     * @param Colour colour of piece to be placed.
     * @return Successfulness of the action.
     */
    public boolean PutPiece(int xPos, int yPos, int colour){
        ArrayList<int[]> flipList = _aggregateCheckerList(xPos, yPos, colour);
        if(flipList.isEmpty()){
            return false;
        }
        for (int[] coords : flipList) {
            SetPiece(coords[0], coords[1], colour);
        }
        SetPiece(xPos, yPos, colour);
        updateCount();
        return true;
    }

    public int captureCount(int x, int y, int colour){
        return _aggregateCheckerList(x, y, colour).size();
    }
    
    private void updateCount(){
        int tempBlack = 0;
        int tempWhite = 0;
        int tempEmpty = 0;
        for(int i = 0; i < boardLength; i++){
            for(int j = 0; j < boardLength; j++){
                if(board[i][j] == BLACK)
                    tempBlack++;
                else if(board[i][j] == WHITE)
                    tempWhite++;
                else
                    tempEmpty++;
            }
        }
        countBlack = tempBlack;
        countWhite = tempWhite;
        countEmpty = tempEmpty;
    }

    /**
     * Gives a list of pieces eligible to be flipped given a piece placement. 
     * This method shall not be invoked outside this class.
     * 
     * @param xPos X position of piece to be checked.
     * @param yPos Y position of piece to be checked.
     * @param colour Colour of piece to be checked.
     * @param xIncr Value to be incremented on xPos in each iteration. +1: right, -1, left
     * @param yIncr Value to be incremented on yPos in each iteration. +1: down, -1, up
     * @return List of pieces eligible to be flipped.
     */
    private ArrayList<int[]> _aggregateCheckerList(int xPos, int yPos, int colour){
        ArrayList<int[]> ret = new ArrayList<int[]>();
        //Northbound check
        ret.addAll(_Checker(xPos, yPos, colour, 0, -1));   //N
        //Northeastbound check
        ret.addAll(_Checker(xPos, yPos, colour, 1, -1));   //NE
        //Eastbound check
        ret.addAll(_Checker(xPos, yPos, colour, 1, 0));    //E
        //Southeastbound check
        ret.addAll(_Checker(xPos, yPos, colour, 1, 1));    //SE
        //Southbound check
        ret.addAll(_Checker(xPos, yPos, colour, 0, 1));    //S
        //Southwestbound check
        ret.addAll(_Checker(xPos, yPos, colour, -1, -1));  //SW
        //Westbound check
        ret.addAll(_Checker(xPos, yPos, colour, -1, 0));   //W
        //Northwestbound check
        ret.addAll(_Checker(xPos, yPos, colour, -1, 1));  //NW

        return ret;
    }
    private ArrayList<int[]> _Checker(int xPos, int yPos, int colour, int xIncr, int yIncr){
        //initialise variables
        int xTemp = xPos;
        int yTemp = yPos;
        ArrayList<int[]> tempList = new ArrayList<int[]>();
        ArrayList<int[]> finalList = new ArrayList<int[]>();

        //initialise logic loop
        xTemp += xIncr;
        yTemp += yIncr;

        //main logic loop
        while(!OutOfBoundsCheck(xTemp, yTemp)){
            //Case: no piece: flip no pieces
            if(board[xTemp][yTemp] == GameBoard.EMPTY)
                break;
            //Case: piece of same colour: flip all previously encountered opposite-coloured pieces
            if(board[xTemp][yTemp] == colour){
                if(tempList.size() > 0)
                    finalList.addAll(tempList);
                break;
            }
            //Case: piece of opposing colour: add to encountered opposite-coloured pieces
            int[] coords = new int[2];
            coords[0] = xTemp;
            coords[1] = yTemp;
            tempList.add(coords);
            xTemp += xIncr;
            yTemp += yIncr;
        }
        return finalList;
    }

}