package ui;
import java.util.Scanner;

import javax.swing.*;

import ui.LoginInterface;
import ui.WindowError;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.awt.event.KeyAdapter;

//txt label inside panel
public class CustomLabel{
    private JLabel label;
    private JPanel panel;
    public CustomLabel(String contenu,int size_txt,Color color_txt,int align,Color panel_color,int sizex,int sizey){
        label = new JLabel(contenu);
        label.setFont(new Font("SansSerif", Font.PLAIN, size_txt));
        label.setForeground(color_txt);
        panel = new JPanel(new FlowLayout(align));
        panel.add(label);
        panel.setBackground(panel_color);
        panel.setMaximumSize(new Dimension(sizex,sizey));
    }
    public JLabel getLabel(){
        return label;
    }
    public JPanel getPanel(){
        return panel;
    }

}