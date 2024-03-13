package ui.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import ui.component.*;

public class Settings extends Panel {
	
	MenuBar menuBar;
	ArrayList<Button> buttonList;
	public final static int BUTTON_X = 410;

	public Settings() {
		menuBar = new MenuBar();
		buttonList = ButtonPresets.settingsPreset();
		for (Button button : buttonList) {
            this.add(button);
            this.addMouseListener(button.mouse);
        }
		setFont();
	}
	
	 @Override
    public void paint(Graphics g) {
        super.setBackground(new Color(0x919191));
        super.paint(g);
        g.setFont(smallFont);
        g.drawString("background color", BUTTON_X, 320);
        g.drawString("music volume", BUTTON_X, 426); 
        g.drawString("AI difficulty", BUTTON_X, 542); 
        g.drawString("highlight moves", BUTTON_X, 658); 

		menuBar.render(g);
		for (Button button : buttonList) {
			button.render(g);
		}

        revalidate();
    }
}
