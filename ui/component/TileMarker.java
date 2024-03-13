package ui.component;

import java.awt.Color;
import java.awt.Graphics;

public class TileMarker extends Tile{
    public static final int RADIUS_CENTRE = 8;
    public static final int OFFSET_CENTRE = 2;
    public static final int RADIUS_OUTLINE = RADIUS_CENTRE + OFFSET_CENTRE;
    public static final int PIECE_OFFSET = LENGTH / 2 - RADIUS_OUTLINE;

    public static final int RADIUS_CENTRE_HIGHLIGHT = 12;
    public static final int OFFSET_CENTRE_HIGHLIGHT = 4;
    public static final int RADIUS_OUTLINE_HIGHLIGHT = RADIUS_CENTRE_HIGHLIGHT + OFFSET_CENTRE_HIGHLIGHT;
    public static final int PIECE_OFFSET_HIGHLIGHT = LENGTH / 2 - RADIUS_OUTLINE_HIGHLIGHT;

    private static Color highlightColour = new Color(0xFF0D86);
    private static boolean showHighlight = true;
    private boolean isHighlight = false;

    public TileMarker(int renderX, int renderY, int state, boolean highlight){
        this.renderX = renderX;
        this.renderY = renderY;
        this.state = state;
        this.isHighlight = highlight;
    }

    public TileMarker(TilePiece tp, int state, boolean highlight){
        this(tp.getRenderX(), tp.getRenderY(), state, highlight);
    }

    public static void setShowHighlight(boolean val){
        showHighlight = val;
    }

    public static void toggleShowHighlight(){
        showHighlight = !showHighlight;
    }

    public static boolean getShowHighlight(){
        return showHighlight;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(isHighlight && showHighlight){
            g.setColor(highlightColour);
            g.fillOval(renderX + PIECE_OFFSET_HIGHLIGHT, renderY + PIECE_OFFSET_HIGHLIGHT, RADIUS_OUTLINE_HIGHLIGHT * 2, RADIUS_OUTLINE_HIGHLIGHT * 2);
            g.setColor(getPieceColour(this.state));
            g.fillOval(renderX + PIECE_OFFSET_HIGHLIGHT + OFFSET_CENTRE_HIGHLIGHT, renderY + PIECE_OFFSET_HIGHLIGHT + OFFSET_CENTRE_HIGHLIGHT, RADIUS_CENTRE_HIGHLIGHT * 2, RADIUS_CENTRE_HIGHLIGHT * 2);
        }
        else{
            g.setColor(getPieceInverseColour(this.state));
            g.fillOval(renderX + PIECE_OFFSET, renderY + PIECE_OFFSET, RADIUS_OUTLINE * 2, RADIUS_OUTLINE * 2);
            g.setColor(getPieceColour(this.state));
            g.fillOval(renderX + PIECE_OFFSET + OFFSET_CENTRE, renderY + PIECE_OFFSET + OFFSET_CENTRE, RADIUS_CENTRE * 2, RADIUS_CENTRE * 2);
        }
        revalidate();
    }
}