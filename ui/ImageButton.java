package ui;
import javax.swing.*;

import commands.SendMessageCommand;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import user.User;
import discussion.*;
import db.*;
import message.*;

public class ImageButton {
    private JButton button;
    public JButton getButton(){
        return button;
    }
    public ImageButton(String nom_img,int sizex,int sizey,Runnable action){
        Image resizedImage = new ImageIcon(nom_img).getImage().getScaledInstance(sizex - 10,sizey - 10, Image.SCALE_DEFAULT);
        button = new JButton(new ImageIcon(resizedImage));
        button.setPreferredSize(new Dimension(sizex,sizey));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.run();
            }
        }); 
    }
}
