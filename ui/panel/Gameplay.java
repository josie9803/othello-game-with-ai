package ui.panel;

import ui.component.*;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

import game.*;

public class Gameplay extends Panel{
    private static final int BOARD_ORIGIN_X = 280;
    private static final int BOARD_ORIGIN_Y = 72;
    private static final int BOARD_SIZE = 8;
    private static final int[] BOARD_ORIGIN = {BOARD_ORIGIN_X, BOARD_ORIGIN_Y}; // (x, y)

    private static final Color bgColour = new Color(0x3F3F3F);
    
    private TilePiece[][] boardTile = new TilePiece[BOARD_SIZE][BOARD_SIZE];

    public static boolean hasTileMarker = true;
    MenuBar menuBar;
    GameStatus game;
    ArrayList<Button> buttonList;
    ArrayList<Button> winButtonList;
    
    
    public Gameplay(){
        menuBar = new MenuBar(); 

        buttonList = ButtonPresets.gamePreset(GameLogic.getInstance().getAutoplay());
        for (Button button : buttonList) {
            this.add(button);
            this.addMouseListener(button.mouse);
        }

        game = new GameStatus();
        
        winButtonList = ButtonPresets.winningPreset();
        for (Button button1 : winButtonList) {
            this.add(button1);
            this.addMouseListener(button1.mouse);
        }
    }

    @Override
    public void paint(Graphics g) {
        GameLogic instance = GameLogic.getInstance();
        GameBoard board = instance.getBoard();

        super.setBackground(bgColour);
        super.paint(g);

        game.render(g);
        
        for(int x = 0; x < BOARD_SIZE; x++){
            for(int y = 0; y < BOARD_SIZE; y++){
                boardTile[x][y] = new TilePiece(
                                        BOARD_ORIGIN[0] + x * TilePiece.LENGTH,
                                        BOARD_ORIGIN[1] + y * TilePiece.LENGTH,
                                        x,
                                        y,
                                        board.GetPiece(x, y));
                this.add(boardTile[x][y]);
                boardTile[x][y].render(g);
            }
        }
        
        if(hasTileMarker == true) {
	        int[] best = Autoplay.computeBestMove(instance.getLegalMoves(), board);
	        
	        for (int[] coords : instance.getLegalMoves()) {
	            TileMarker marker = new TileMarker(
	                                        boardTile[coords[0]][coords[1]], 
	                                        instance.getCurrentColour(),
	                                        (best != null && Arrays.equals(coords, best)));
	            this.add(marker);
	            marker.render(g);
	        }
        }

        menuBar.render(g);
        
        for (Button button : buttonList) {
            button.render(g);
        }
        
        if (instance.hasEnded()) {
	        for (Button button : winButtonList) {
	            button.render(g);
	        }
	        game.drawResult(g);
        }
       
        revalidate();
    }
}