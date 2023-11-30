package UI;
import java.util.Scanner;
import UI.LoginInterface;
import UI.WindowError;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window{

    private JFrame frame;
    public Window(String framename) {
        this(framename, 800, 600);
    }
    // Constructeur avec des dimensions spécifiées
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
    public static void main(String[] args){
        //LaunchGif test = new LaunchGif("Application");
        WindowError error = new WindowError("Error", "t es nul et puis user not connected sale con essaie d etre bg !");
    }
    public void updateUI(){
        /*
        * These lines update the UI
        * Repaint holds for all graphical changes (like color changes)
        * Revalidate holds for UI change (like button add/remove)
        */
        frame.repaint();
        frame.revalidate();
    }
}