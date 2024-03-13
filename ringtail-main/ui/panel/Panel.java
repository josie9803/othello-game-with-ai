package ui.panel;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;

public abstract class Panel extends JPanel{
	public static Font smallFont;
	public static Font largeFont;
    @Override
    public void paint(Graphics g){
        super.paint(g);
    };
    public void refresh(){
        paint(this.getGraphics());
    }
    
    public void setFont() {
   	    try {
   	    	smallFont = Font.createFont(Font.TRUETYPE_FONT, new File("Bungee-Regular.ttf")).deriveFont(20f);
   	    	largeFont = Font.createFont(Font.TRUETYPE_FONT, new File("Bungee-Regular.ttf")).deriveFont(90f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Bungee-Regular.ttf")));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		} 
    }
}
