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
    private Color black_color=new Color(64, 68, 75);
    public Window(String framename) {
        this(framename, 960, 720);
    }
    public Window(String framename, int frameX, int frameY) {
        frame = new JFrame(framename);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameX, frameY);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
    public JFrame getFrame(){
        return frame;
    }
    public Color getblackColor(){
        return black_color;
    }
    public void updateUI(){
        frame.repaint();
        frame.revalidate();
    }
}