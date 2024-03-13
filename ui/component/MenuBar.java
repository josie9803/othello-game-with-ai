package ui.component;

import java.awt.Color;
import java.awt.Graphics;

public class MenuBar extends Label {

    private static final int LENGTH = 1440;
    private static final int HEIGHT = 56;

    private static final int MENUBAR_X = 0;
    private static final int MENUBAR_Y = 0;

    public MenuBar() {
        this.renderX = MENUBAR_X;
        this.renderY = MENUBAR_Y;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(0xD9D9D9));
        g.fillRect(0, MENUBAR_Y, LENGTH, HEIGHT);
        g.setColor(new Color(0x0));
        g.drawRect(0, MENUBAR_Y, LENGTH, HEIGHT);
        revalidate();
    }

}
