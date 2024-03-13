package ui.component;

import java.awt.Color;
import java.awt.Graphics;
import game.GameLogic;

public class GameStatus extends Label {
	
	public GameStatus() {	
		setFont();
	}

	@Override
	protected void paintComponent(Graphics g) {	
		if (!GameLogic.getInstance().hasEnded()) {
			drawTurn(g);
		}
		revalidate();
	}

	public void drawResult(Graphics g) {
		int countBlack = GameLogic.getInstance().getBoard().getCountBlack();
		int countWhite = GameLogic.getInstance().getBoard().getCountWhite();
		//draw background
		Color c = new Color(0,0,0,210);
		g.setColor(c);
		g.fillRoundRect(280, 72, 880, 880, 0, 0);
		//draw string
		g.setFont(bigFont);
		
		g.setColor(new Color(0xFFFFFF));
		g.drawString("white x " + countWhite, 610, 400);
		g.drawString("black x " + countBlack, 610, 480);
		
		g.setColor(new Color(0xFFD93D));
		
		if (countBlack > countWhite)
		{
			g.drawString("black wins !", 590, 560);
		}
		else if (countBlack < countWhite)
		{
			g.drawString("white wins !", 590, 560);
		}
		else 
		{
			g.drawString("draw !", 650, 560);
		}
		//draw message
		g.setFont(normalFont);
		g.setColor(c);
		g.drawString("no more moves", 1170, 470);
		g.drawString("can be made T.T", 1170, 500);
		
	}
	
	public void drawTurn(Graphics g) {
		int countBlack = GameLogic.getInstance().getBoard().getCountBlack();
		int countWhite = GameLogic.getInstance().getBoard().getCountWhite();
		int currColor = GameLogic.getInstance().getCurrentColour();
		g.setFont(bigFont);
		g.setColor(new Color(0x0));
		g.drawString("x " + countBlack, 300,1000);
		g.setColor(new Color(0xFFFFFF));
		g.drawString("x " + countWhite, 1050,1000);
		
		if (GameLogic.getInstance().getAutoplay() == false) {
			if (currColor == 1) {
				g.drawString("black", 1200, 500);
			}
			else if (currColor == 2) {
				g.drawString("white", 1200, 500);
			}
			g.drawString("turn!", 1200, 550);
		}
		else {
			if (currColor == 1) {
				g.drawString("your", 1200, 500);
				g.drawString("turn!", 1200, 550);
			}
			else {
				g.drawString("please", 1200, 500);
				g.drawString("wait..", 1200, 550);
			}
		}
	}

}
