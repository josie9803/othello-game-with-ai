package ui.component;

import java.awt.Color;

public abstract class Tile extends Label{
    public static final int LENGTH = 110;

    public static final int EMPTY = 0;
    public static final int BLACK = 1;
    public static final int WHITE = 2;

    protected static Color[] pieceColour = {new Color(0x0), new Color(0xFFFFFF)};
    
    int state;
    
    public static void setPieceColour(int team, Color renderColour){
        if (team == BLACK || team == WHITE)
            pieceColour[team] = renderColour;
        else
            System.out.println("Invalid team.");
    }

    public static Color getPieceColour(int colour){
        if (colour == BLACK || colour == WHITE)
            return colour == BLACK ? pieceColour[0] : pieceColour[1];
        else {
            System.out.println("Invalid team."); 
            return null;
        }
    }

    public static Color getPieceInverseColour(int colour){
        if(colour == BLACK || colour == WHITE)
            return colour == BLACK ? pieceColour[1] : pieceColour[0];
        else {
            System.out.println("Invalid team."); 
            return null;
        }
    }

}
