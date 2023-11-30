package UI;
import java.util.Scanner;
import UI.LoginInterface;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LaunchGif extends Window{

    private JFrame frame;
    private String gif_name="login.gif";
    
    public LaunchGif(String framename) {
        super(framename);
        this.frame = super.getFrame();
        launch_gif();
    }
    private void launch_gif() {
 

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
                LoginInterface loginInterface = new LoginInterface("Login Interface");
            }
        });
        timer.setRepeats(false); // lance le timer une seule fois
        timer.start();
    }
}