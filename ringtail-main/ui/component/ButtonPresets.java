package ui.component;

import java.io.FileNotFoundException;
import java.text.AttributedString;
import java.util.ArrayList;
import java.awt.Color;

import game.GameLogic;
import ui.Driver;

public class ButtonPresets {
    public static final int COLOUR_BUTTON_DEF = 0x3F3F3F;
    public static final int COLOUR_BUTTON_ALERT = 0xBF3F3F;
    public static final int COLOUR_BUTTON_HIGHLIGHT = 0x1A5D1A;

    public static ArrayList<Button> gamePreset(boolean isAutoplay) {
        final int MENUBAR_BUTTON_Y = 8;
        ArrayList<Button> result = new ArrayList<Button>();

        Button exitButton = new Button(1232, MENUBAR_BUTTON_Y, COLOUR_BUTTON_ALERT, "Exit game", new Runnable() {
            @Override
            public void run() {
                System.exit(0);
            }
        });

        Button settingsButton = new Button(200, MENUBAR_BUTTON_Y, COLOUR_BUTTON_DEF, "Settings", new Runnable() {
            @Override
            public void run() {
                Driver.changePanel(Driver.getSettingsPanel());
            }
        });

        if(isAutoplay){
            Button saveButton = new Button(500, MENUBAR_BUTTON_Y, COLOUR_BUTTON_DEF, "Save", new Runnable() {
                public void run(){
                    try{
                        GameLogic.getInstance().save();
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });

            Button loadButton = new Button(800, MENUBAR_BUTTON_Y, COLOUR_BUTTON_DEF, "Load", new Runnable() {
                public void run(){
                    try{
                        GameLogic.getInstance().load();
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
            
            result.add(saveButton);
            result.add(loadButton);
        }

        result.add(exitButton);
        result.add(settingsButton);
        return result;
    }
    
    public static ArrayList<Button> menuPreset() {
        final int MENU_BUTTON_X = 635;
        ArrayList<Button> result = new ArrayList<Button>();
        
        Button multiplayerButton = new Button(MENU_BUTTON_X, 440, COLOUR_BUTTON_HIGHLIGHT, "Multiplayer", new Runnable() {
            @Override
            public void run(){ 
                Driver.resetGame(false);
                Driver.changePanel(Driver.getGamePanel()); 
            }
        });
        Button AIButton = new Button(MENU_BUTTON_X, 500, COLOUR_BUTTON_HIGHLIGHT, "Play vs AI", new Runnable() {
            @Override
            public void run(){ 
                Driver.resetGame(true);
                Driver.changePanel(Driver.getGamePanel());
            }
        });
        //Load from 
        Button loadButton = new Button(MENU_BUTTON_X, 560, COLOUR_BUTTON_HIGHLIGHT, "Load (vs AI)", new Runnable() {
            @Override
            public void run(){ 
                try{
                Driver.resetGame(true);
                Driver.changePanel(Driver.getGamePanel()); 
                GameLogic.getInstance().load();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        Button assistButton = new Button(MENU_BUTTON_X, 620, COLOUR_BUTTON_DEF, "How to play", new Runnable() {
            @Override
            public void run() {
                try {
                    Driver.showHowToPlay();
                } catch (FileNotFoundException ioe) {
                    ioe.printStackTrace();
                }
            }
        });
        Button toggleButton = new Button(MENU_BUTTON_X, 680, COLOUR_BUTTON_DEF, "music on/off", new Runnable() {
            @Override
            public void run() {
                Driver.toggleMusic();
            }
        });
        Button settingsButton = new Button(MENU_BUTTON_X, 740, COLOUR_BUTTON_DEF, "Settings", new Runnable() {
            @Override
            public void run() {
                Driver.changePanel(Driver.getSettingsPanel());
            }
        });
        Button exitButton = new Button(MENU_BUTTON_X, 800, COLOUR_BUTTON_ALERT, "Exit Game", new Runnable() {
            @Override
            public void run() {
                System.exit(0);
            }
        });
        result.add(toggleButton);
        result.add(AIButton);
        result.add(loadButton);
        result.add(multiplayerButton);
        result.add(assistButton);
        result.add(settingsButton);
        result.add(exitButton);
        return result;
    }

    public static ArrayList<Button> settingsPreset(){
        final int BUTTON_Y = 8;
        final int BUTTON_X = 823;
        ArrayList<Button> result = new ArrayList<Button>();
        
        Button settingsButton = new Button(200, BUTTON_Y, COLOUR_BUTTON_HIGHLIGHT, "Settings", null);
        Button backToGameButton = new Button(1232, BUTTON_Y, COLOUR_BUTTON_ALERT, "Back to Game", new Runnable(){
            @Override
            public void run(){ Driver.changePanel(Driver.getGamePanel()); }
        });
        Button backToMenuButton = new Button(700, BUTTON_Y, COLOUR_BUTTON_DEF, "Main Menu", new Runnable(){
            @Override
            public void run(){ Driver.changePanel(Driver.getMenuPanel()); }
        });
        
        //background
        ArrayList<AttributedString> backgroundLabel = new ArrayList<AttributedString>();
        backgroundLabel.add(new AttributedString("Red"));
        backgroundLabel.add(new AttributedString("Blue"));
        backgroundLabel.add(new AttributedString("Yellow"));
        backgroundLabel.add(new AttributedString("Cyan"));
        backgroundLabel.add(new AttributedString("Magnenta"));
        backgroundLabel.add(new AttributedString("Green"));

        ArrayList<Color> backgroundColour = new ArrayList<Color>();
        backgroundColour.add(new Color (0x880000)); //red
        backgroundColour.add(new Color (0x000088)); //blue
        backgroundColour.add(new Color (0x666600)); //yellow
        backgroundColour.add(new Color (0x006666)); //cyan
        backgroundColour.add(new Color (0x660066)); //magnenta
        backgroundColour.add(new Color (0x008800)); // green
        
        Button backgroundOptions = new Transformation(BUTTON_X, 286, 0x008800,"Green", backgroundLabel, backgroundColour, null, 1);
        
         //music
        ArrayList<AttributedString> musicLabel = new ArrayList<AttributedString>();
        musicLabel.add(new AttributedString("100%"));
        musicLabel.add(new AttributedString("80%"));
        musicLabel.add(new AttributedString("60%"));
        musicLabel.add(new AttributedString("40%"));
        musicLabel.add(new AttributedString("20%"));
        musicLabel.add(new AttributedString("Muted"));

        ArrayList<Color> musicColour = new ArrayList<Color>();
        musicColour.add(new Color (0x00AB08)); 
        musicColour.add(new Color (0x00C301));
        musicColour.add(new Color (0x26D701)); 
        musicColour.add(new Color (0x4DED30));
        musicColour.add(new Color (0x95F985));   
        musicColour.add(new Color (0x3F3F3F)); 
       
        Button soundOptions = new Transformation(BUTTON_X, 402, 0x00AB08, "100%", musicLabel, musicColour, null, 2);

        //AI
        ArrayList<AttributedString> AILabel = new ArrayList<AttributedString>();
        AILabel.add(new AttributedString("Easy"));
        AILabel.add(new AttributedString("Medium"));
        AILabel.add(new AttributedString("Hard"));
        AILabel.add(new AttributedString("Expert"));

        ArrayList<Color> AIColour = new ArrayList<Color>();
        AIColour.add(new Color (0x00AA00)); 
        AIColour.add(new Color (0xA0A000)); 
        AIColour.add(new Color (0xA000A0)); 
        AIColour.add(new Color (0xA00000)); 
       
        Button difficultyOptions = new Transformation(BUTTON_X, 518, 0x00AA00, "Easy", AILabel, AIColour, null, 3);

        //highlight moves
        ArrayList<AttributedString> highlightLabel = new ArrayList<AttributedString>();
        highlightLabel.add(new AttributedString("Best"));
        highlightLabel.add(new AttributedString("Legal"));
        highlightLabel.add(new AttributedString("None"));

        ArrayList<Color> hightlightColour = new ArrayList<Color>();
        hightlightColour.add(new Color (0x00AA00)); 
        hightlightColour.add(new Color (0xA0A000)); 
        hightlightColour.add(new Color (0xA00000)); 
        
        Button highlightMovesOptions = new Transformation(BUTTON_X, 634, 0x00AA00, "Best", highlightLabel, hightlightColour, null, 4);

        result.add(backToGameButton);
        result.add(backToMenuButton);
        result.add(settingsButton);
        result.add(backgroundOptions);
        result.add(soundOptions);
        result.add(difficultyOptions);
        result.add(highlightMovesOptions);

        return result;
    }
    
    public static ArrayList<Button> winningPreset(){
    	final int BUTTON_X = 50;
		ArrayList<Button> result = new ArrayList<Button>();
		Button playAgainButton = new Button(BUTTON_X, 400, 0x00AA00, "play again", new Runnable() { //645,600
        @Override
        public void run(){  	
        	Driver.resetGame(GameLogic.getInstance().getAutoplay());
        	Driver.changePanel(Driver.getGamePanel());
        }
        });
		Button mainMenuButton = new Button(BUTTON_X, 500, 0x9E9FA5, "main menu", new Runnable() {
            @Override
            public void run() {     	
                Driver.changePanel(Driver.getMenuPanel());
            }
        });
		result.add(playAgainButton);
		result.add(mainMenuButton);
		return result;
    }
}
