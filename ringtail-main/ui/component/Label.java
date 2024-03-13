package ui.component;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;

public abstract class Label extends JLabel {
	public static Font smallFont;
	public static Font bigFont;
	public static Font normalFont;
    int renderX;
    int renderY;

    public int getRenderX(){ return renderX; }
    public int getRenderY(){ return renderY; }
    
    @Override
    protected abstract void paintComponent(Graphics g);

    public void render(Graphics g){
        this.paintComponent(g);
    };
    
    public void setFont() {
   	    try {
   	    	smallFont = Font.createFont(Font.TRUETYPE_FONT, new File("Bungee-Regular.ttf")).deriveFont(20f);
   	    	bigFont = Font.createFont(Font.TRUETYPE_FONT, new File("Bungee-Regular.ttf")).deriveFont(40f);
   	    	normalFont = Font.createFont(Font.TRUETYPE_FONT, new File("Bungee-Regular.ttf")).deriveFont(25f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Bungee-Regular.ttf")));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		} 
    }
}
