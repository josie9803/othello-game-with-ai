package ui.panel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import ui.component.Button;
import ui.component.ButtonPresets;

import java.io.*;
import java.util.ArrayList;


public class MainMenu extends Panel {
	public BufferedImage image;
	ArrayList<Button> buttonList;
	
    public MainMenu() {
        setImage();
        setFont();
    	buttonList = ButtonPresets.menuPreset();
    	for (Button button : ButtonPresets.menuPreset()) {
            this.add(button);
            this.addMouseListener(button.mouse);
        }
    }
    public void setImage() {
		 try {
			image = ImageIO.read(getClass().getResourceAsStream("/res/background2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @Override
    public void paint(Graphics g) {
        super.setBackground(new Color(0x7EAA92));
        super.paint(g);
        g.drawImage(image, 0, 0, 1440, 1080, null);
        g.setFont(largeFont);
        g.setColor(new Color(0xFFFFFF));
		g.drawString("OTHELLO", 497, 129);    

		for (Button button : buttonList) {
			button.render(g);
		}

        revalidate();
    }
}