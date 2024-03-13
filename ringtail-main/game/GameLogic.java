package game;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.util.ArrayList;

import ui.Driver;

public class GameLogic {

    private GameBoard board;
    private int currentColour;
    private ArrayList<int[]> legalMoves;

    private boolean isAutoplay = true;
    private int autoplayDelay = 500;

    private static GameLogic instance;

    private GameLogic(){
        board = new GameBoard();
        
        currentColour = GameBoard.BLACK;
        legalMoves = new ArrayList<int[]>();
        updateLegalMoves();

        instance = this;
    }

    public static GameLogic getInstance(){
        if(instance == null)
            instance = new GameLogic();
        return instance;
    }

    public static GameLogic resetInstance(){
        instance = new GameLogic();
        return instance;
    }

    public void setAutoplay(boolean value){
        isAutoplay = value;
    }
    
    public boolean getAutoplay() {
    	return isAutoplay;
    }
    
    public GameBoard getBoard() {
        return board;
    }

    public int getCurrentColour(){
        return currentColour;
    }

    public ArrayList<int[]> getLegalMoves(){
        return legalMoves;
    }

    public boolean hasEnded(){
        return legalMoves.isEmpty() || board.getCountEmpty() == 0;
    }

    public synchronized boolean setPiece(int boardX, int boardY){
        if(hasEnded())
            return false;
        boolean status = board.PutPiece(boardX, boardY, currentColour);
        if(status){
            colourSwitch();
            updateLegalMoves();

            if(isAutoplay){
                Driver.getGamePanel().refresh();
                int[] move = Autoplay.computeMove(legalMoves, board);
                try{
                    Thread.sleep(autoplayDelay);
                }
                catch(Exception e){ System.out.println(e); };

                board.PutPiece(move[0], move[1], currentColour);
                colourSwitch();
                updateLegalMoves();
            }
        }
        Driver.getGamePanel().refresh();
        return status;
    }

    public boolean save() throws Exception{
        try{
            File saveDir = new File(".\\save");
            if(!saveDir.exists()){
                saveDir.mkdir();
            }
            File saveFile = new File(".\\save\\game.sav");
            if(!saveFile.exists()){
                if(!saveFile.createNewFile()){
                    System.out.println("Unable to create save file.");
                    return false;
                }
            }
            FileWriter writer = new FileWriter(saveFile);

            writer.write(String.valueOf(currentColour) + '\n');

            for(int y = 0; y < GameBoard.boardLength; y++){
                for(int x = 0; x < GameBoard.boardLength; x++){
                    writer.write(board.GetPiece(x, y) + '0');
                }
            }
            writer.write('\n');
            writer.flush();
            writer.close();
        }
        catch(Exception e){
            throw e;
        }
        return true;
    }

    public boolean load() throws Exception{
        try{
            File saveFile = new File(".\\save\\game.sav");
            if(!saveFile.exists()){
                System.out.println("Save file not found.");
                return false;
            }
            if(!saveFile.canRead()){
                System.out.println("Unable to read save file.");
                return false;
            }
            BufferedReader reader = new BufferedReader(new FileReader(saveFile));
            
            currentColour = Integer.parseInt(reader.readLine());

            String boardState = reader.readLine();
            for(int y = 0; y < GameBoard.boardLength; y++){
                for(int x = 0; x < GameBoard.boardLength; x++){
                    board.SetPiece(x, y, boardState.charAt((y * GameBoard.boardLength + x)) - '0');
                }
            }
            updateLegalMoves();
            reader.close();
        }
        catch(Exception e){
            throw e;
        }
        return true;
    }
    
    private synchronized void updateLegalMoves(){
        legalMoves = board.getLegalMoves(currentColour);
    }

    private synchronized void colourSwitch(){
        currentColour = (currentColour == GameBoard.BLACK) ? GameBoard.WHITE : GameBoard.BLACK;
    }
}
