package ui.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;

import game.GameLogic;

public class TilePiece extends Tile{
    public static final int RADIUS_CENTRE = 44;
    public static final int OFFSET_CENTRE = 3;
    public static final int RADIUS_OUTLINE = RADIUS_CENTRE + OFFSET_CENTRE;
    public static final int PIECE_OFFSET = LENGTH / 2 - RADIUS_OUTLINE;

    private static Color tileColourCentre = new Color(0x008800);
    private static Color tileColourOutline = new Color(0x0);

    int boardX;
    int boardY;

    TileMouseListener mouse;

    class TileMouseListener extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e){
            // System.out.println("tile (" + boardX + ", " + boardY + ") Clicked");
            if(state == EMPTY)
                GameLogic.getInstance().setPiece(boardX, boardY); 
        }
    }

    public TilePiece(int renderX, int renderY, int boardX, int boardY, int colour){
        this.setBounds(renderX, renderY, LENGTH, LENGTH);
        this.renderX = renderX;
        this.renderY = renderY;
        this.boardX = boardX;
        this.boardY = boardY;
        this.state = colour;
        this.mouse = new TileMouseListener();
        this.addMouseListener(mouse);
    }
    
    public static void setTileColour(Color renderColour){
        tileColourCentre = renderColour;
    }

    public static void setTileOutline(Color renderColour){
        tileColourOutline = renderColour;
    }

    @Override
    protected void paintComponent(Graphics g){
        g.setColor(tileColourCentre);
        g.fillRect(renderX, renderY, LENGTH, LENGTH);
        g.setColor(tileColourOutline);
        g.drawRect(renderX, renderY, LENGTH, LENGTH);

        if(state == BLACK || state == WHITE){
            g.setColor(getPieceInverseColour(this.state));
            g.fillOval(renderX + PIECE_OFFSET, renderY + PIECE_OFFSET, RADIUS_OUTLINE * 2, RADIUS_OUTLINE * 2);
            g.setColor(getPieceColour(this.state));
            g.fillOval(renderX + PIECE_OFFSET + OFFSET_CENTRE, renderY + PIECE_OFFSET + OFFSET_CENTRE, RADIUS_CENTRE * 2, RADIUS_CENTRE * 2);
        }  
        revalidate();
    };
}
