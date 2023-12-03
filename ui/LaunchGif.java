package ui;
import java.util.Scanner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import db.DatabaseDiscussion;
import db.DatabaseUsers;
import ui.LoginInterface;
public class LaunchGif extends Window{

    private JFrame frame;
    private String gif_name="images/login.gif";
    
    public LaunchGif(String framename,DatabaseUsers users_db,DatabaseDiscussion disc_db) {
        super(framename);
        this.frame = super.getFrame();
        launch_gif(users_db,disc_db);
    }
    private void launch_gif(DatabaseUsers users_db,DatabaseDiscussion disc_db) {

        // Redimensionner gif 
        Image resizedImage = new ImageIcon(gif_name).getImage().getScaledInstance(960, 720, Image.SCALE_DEFAULT);
        JLabel gifLabel = new JLabel(new ImageIcon(resizedImage));

        frame.getContentPane().add(gifLabel, BorderLayout.CENTER);
        frame.setVisible(true);
        // ferme fenetre apres timer et appelle login interface 
        // 5790 de base
        Timer timer = new Timer(3200 , new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                // affiche login interface 
                LoginInterface loginInterface = new LoginInterface("Login Interface",users_db,disc_db);
            }
        });
        timer.setRepeats(false); // lance le timer une seule fois
        timer.start();
    }
}