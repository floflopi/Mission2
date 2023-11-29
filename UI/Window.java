package UI;
import java.util.Scanner;
import UI.LoginInterface;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window{

    private JFrame frame;

    public Window(String framename) {
        this(framename, 1100, 550);
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
        LaunchGif test = new LaunchGif("Application");         
        }
    }
//}