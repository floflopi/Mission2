package ui;
import java.util.Scanner;

import javax.swing.*;

import ui.LoginInterface;
import ui.WindowError;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window{

    private JFrame frame;
    public Window(String framename,boolean fullscreen) {
        this(framename, 1200, 720,fullscreen);
    }
    public Window(String framename, int frameX, int frameY,boolean fullscreen) {
        frame = new JFrame(framename);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameX, frameY);
        if (fullscreen)
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
    public void closeWindow(){
        frame.dispose();
    }
    public JFrame getFrame(){
        return frame;
    }
    public static Color getblackColor(){
        return new Color(64, 68, 75);
    }
    public static JPanel getEmptyPanel(int sizex,int sizey){
        JPanel emptyPanel = new JPanel();
        emptyPanel.setBackground(getblackColor());
        emptyPanel.setMaximumSize(new Dimension(sizex,sizey));
        return emptyPanel;
    }
    public void updateUI(){
        frame.repaint();
        frame.revalidate();
    }
}