package UI;
import java.util.Scanner;
import UI.LoginInterface;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LaunchGif extends Window{

    private JFrame frame;

    public LaunchGif(String framename) {
        this(framename, 1100, 550);
    }
    // Constructeur avec des dimensions spécifiées
    public LaunchGif(String framename, int frameX, int frameY) {
        super(framename,frameX,frameY);
        this.frame = super.getFrame();
        launch_gif();

    }

    private void launch_gif() {
        // cree un gif label  et place le au centre de la fenetre 
        JLabel gifLabel = new JLabel(new ImageIcon("discord.gif"));
        frame.getContentPane().add(gifLabel, BorderLayout.CENTER);
        frame.setVisible(true);
        // ferme fenetre apres timer et appelle login interface 
        Timer timer = new Timer(5790, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                // affiche login interface 
                LoginInterface loginInterface = new LoginInterface();
            }
        });
        timer.setRepeats(false); // lance le timer une seule fois
        timer.start();
    }
}