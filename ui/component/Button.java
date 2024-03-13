package ui.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import javax.swing.SwingConstants;

public class Button extends Label{

    private static final int LENGTH = 170;
    private static final int HEIGHT = 36;

    Color fillColour;
    AttributedString buttonString;
    Runnable callback = null;
    public ButtonMouseListener mouse;

    class ButtonMouseListener extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e){
            if(boundCheck(e.getX(), e.getY())){
                if(callback != null)
                    callback.run();
                else{
                    // System.out.println("Unbound button at (" + renderX + ", " + renderY + ") clicked");
                }
            }
        }
    }
   
    public Button(int x, int y, int colour, Runnable callback){ 
        this(x, y, colour, (AttributedString) null, callback); 
    }

    public Button(int x, int y, int colour, String string, Runnable callback){
        this(x, y, colour, defaultAttrString(string), callback);
    }

    public Button(int x, int y, int colour, AttributedString string, Runnable callback){
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setBounds(renderX, renderY, LENGTH, HEIGHT); 
        this.renderX = x;
        this.renderY = y;
        this.fillColour = new Color(colour);
        this.callback = callback;
        this.mouse = new ButtonMouseListener();
        this.addMouseListener(mouse);
        if(string != null){
            this.buttonString = string;
        }
        setFont();
    }
    
    public void setColour(Color colour){
        this.fillColour = colour;
    }

    public void setAttrString(AttributedString string){
        this.buttonString = string;
    }

    public void setRunnable(Runnable runnable){
        this.callback = runnable;
    }
    
    protected boolean boundCheck(int x, int y){
        return x > renderX && x < renderX + LENGTH && y > renderY && y < renderY + HEIGHT;
    }

    private static AttributedString defaultAttrString(String string){
        AttributedString result = new AttributedString(string);
        result.addAttribute(TextAttribute.SIZE, 25);
        result.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        result.addAttribute(TextAttribute.FOREGROUND, new Color(0xFFFFFF));
        result.addAttribute(TextAttribute.FONT, smallFont);
        return result;
    }
    
    @Override
    protected void paintComponent(Graphics g){
        g.setColor(fillColour);
        g.fillRect(renderX, renderY, LENGTH, HEIGHT);
        g.setColor(new Color(0x0));
        g.drawRect(renderX, renderY, LENGTH, HEIGHT);
        g.setFont(smallFont);
        if(buttonString != null){
            g.drawString(buttonString.getIterator(), renderX + 15 , renderY + HEIGHT / 4 * 3);
        }
        revalidate();     
    }
}
