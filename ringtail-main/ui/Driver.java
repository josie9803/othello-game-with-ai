package ui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import game.GameLogic;
import ui.panel.*;
import java.util.*;
import java.io.*;

//for background music
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Driver {
    private static JFrame frame;
    private static Panel currentPanel;
    private static Panel previousPanel;
    private static MainMenu menuPanel;
    private static Gameplay gamePanel;
    private static Settings settingsPanel;
    private static Clip bckgclip;
    private static int isPlayingBckg = 1;
    private static FloatControl gainControl;

    private static Driver instance;

    private Driver(){
        frame = new JFrame("Othello");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1440, 1080); 
        frame.setResizable(false);
        menuPanel = new MainMenu();
        gamePanel = new Gameplay();
        settingsPanel = new Settings();

        try //playing the music
        {
            String soundName = "bckgmusic.wav";
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            bckgclip = AudioSystem.getClip();
            bckgclip.open(audioInputStream);
            bckgclip.start(); //start to play the clip
            gainControl = (FloatControl) bckgclip.getControl(FloatControl.Type.MASTER_GAIN);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static synchronized Driver initialise(){
        instCheck();
        return instance;
    }

    public static Driver getInstance(){
        instCheck();
        return instance;
    }

    public static JFrame getFrame(){
        instCheck();
        return frame;
    }

    public static synchronized void changePanel(Panel panel){
        instCheck();
        if(currentPanel != null)
            frame.remove(currentPanel);
        if(previousPanel != null)
            previousPanel.removeAll();
        previousPanel = currentPanel;
        currentPanel = panel;
        frame.add(panel);
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }

    public static synchronized void display(Panel panel) {
        instCheck();
        currentPanel = panel;
        frame.add(panel);
        frame.setVisible(true);
    }

    public static Panel getCurrentPanel(){
        instCheck();
        return currentPanel;
    }

    public static Panel getPreviousPanel(){
        instCheck();
        return previousPanel;
    }

    public static MainMenu getMenuPanel(){
        instCheck();
        return menuPanel;
    }

    public static Gameplay getGamePanel(){
        instCheck();
        return gamePanel;
    }

    public static Settings getSettingsPanel(){
        instCheck();
        return settingsPanel;
    }

    public static void resetGame(boolean isAutoplay){
        GameLogic.resetInstance();
        GameLogic.getInstance().setAutoplay(isAutoplay);
        gamePanel = new Gameplay();
    }

    public static void toggleMusic(){
        if(isPlayingBckg == 1) {
            bckgclip.stop();
            isPlayingBckg = 0;
        } else{
            bckgclip.start();
            isPlayingBckg = 1;
        }
    }

    public static void setVolume(double gain) {
        float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
    }

    private static synchronized void instCheck(){
        if(instance == null)
            instance = new Driver();
    }

    public static void showHowToPlay() throws FileNotFoundException{
        Scanner scanner = new Scanner(new File("./gamesRules.txt")).useDelimiter("\\Z");
        String content = scanner.next();
        scanner.close();
        JOptionPane.showMessageDialog(frame,
                content,
                "How to Play",
                JOptionPane.PLAIN_MESSAGE);
    }
}