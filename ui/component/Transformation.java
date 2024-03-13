package ui.component;

import java.awt.Color;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.ArrayList;

import game.Autoplay;
import ui.Driver;
import ui.panel.Gameplay;

public class Transformation extends Button {
	public static final int BACKGROUND = 1;
    public static final int MUSIC = 2;
    public static final int AI = 3;
    public static final int MOVES = 4;
    
    int state;
    ArrayList<AttributedString> labelTexts = new ArrayList<AttributedString>();
    ArrayList<Color> colors = new ArrayList<Color>();			 
    private int clickCount = 0;
    
    public Transformation(int x, int y, int colour, String string, ArrayList<AttributedString> label, ArrayList<Color> value, Runnable callback, int state){
        super(x, y, colour, string, callback);
        for (int i = 0; i < label.size(); i++) {
        		labelTexts.add(label.get(i));
        }
        for (int i = 0; i < value.size(); i++) {
        		colors.add(value.get(i));
        }
        this.setRunnable(new Runnable() {
	        	@Override
	        	public void run() {
	        		clickCount = (clickCount + 1) % labelTexts.size();
	        		if (state == 1)
	        			changeTileColor(); 
	        		else if (state == 2)
	        			changeMusicVolume();
	        		else if (state == 3)
	        			changeDiffLevel();
	        		else if (state == 4)
	        			changeHighlightMoves();
	        	}
        });
    }	 
	public void changeHighlightMoves() {
		formatLabelTexts();	
		if (clickCount == 2) {
			Gameplay.hasTileMarker = false;
		}
		else {
			TileMarker.toggleShowHighlight();
			Gameplay.hasTileMarker = true;
		}
	}
	public void changeDiffLevel() {
		formatLabelTexts();
		Autoplay.setDifficulty(clickCount);	//clickCount correspond to diff level
	}
	public void changeMusicVolume() {
		formatLabelTexts();	
		Double[] volumes = {1.0d, 0.8d, 0.6d, 0.4d, 0.2d, 0.0d};
		Driver.setVolume(volumes[clickCount]);
	}
	
	public void changeTileColor() {
		formatLabelTexts();
		TilePiece.setTileColour(colors.get(clickCount));
	}
	
	public void formatLabelTexts() {
		labelTexts.get(clickCount).addAttribute(TextAttribute.SIZE, 25);
		labelTexts.get(clickCount).addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
		labelTexts.get(clickCount).addAttribute(TextAttribute.FOREGROUND, new Color(0xFFFFFF));
		labelTexts.get(clickCount).addAttribute(TextAttribute.FONT, smallFont);
		this.setColour(colors.get(clickCount));
		this.setAttrString(labelTexts.get(clickCount));
		Driver.getSettingsPanel().refresh();
	}
}
