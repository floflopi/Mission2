package UI;
import java.util.Scanner;
import UI.LoginInterface;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainUI{

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Exemple de GIF");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Charger un GIF depuis un fichier (ajustez le chemin selon votre fichier GIF)
        ImageIcon gifIcon = new ImageIcon("discord.gif");

        // Créer un JLabel pour afficher le GIF
        JLabel gifLabel = new JLabel(gifIcon);

        // Ajouter le JLabel au contenu de la fenêtre
        frame.getContentPane().add(gifLabel, BorderLayout.CENTER);
        double factor=1f;
        frame.setSize((int) (1100 * factor), (int) (550 * factor));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Timer timer = new Timer(5790, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fermez la première interface (splash screen)
                frame.dispose();

                // Affichez la deuxième interface (login)
                //LoginInterface loginInterface = new LoginInterface();
                //loginInterface.createAndShowGUI2();
            }
        });

        // Démarrez le timer
        timer.setRepeats(false); // Set to false to run only once
        timer.start();
    }
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
        /* 
        // Create window with title "application" that stop the program when closed
        JFrame frame = new JFrame("Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Creating a panel that will hold the buttons
        JPanel panel = new JPanel();
        
        // Setup frame with the panel
        frame.add(panel);
        frame.setSize(400, 400);
        frame.setVisible(true); // show the window

        // Creation of a button that will be shown dynamically 
        JButton buttonTemp = new JButton("Dynamic click me");
        buttonTemp.addActionListener((actionEvent) -> {
            System.out.println("dynamic click");
        });

        // Main loop
        Scanner in = new Scanner(System.in);
        while (true) {
            String line = in.nextLine();
            String[] cutLine = line.split(" ");
            
            switch (cutLine[0]) {
                // Change the title
                case "title":
                    String title = line.replace("title ", "");
                    frame.setTitle(title);
                    break;

                // Make the button appear
                case "add":
                    panel.add(buttonTemp);
                    break;

                // Remove the button
                case "remove":
                    panel.remove(buttonTemp);
                    break;

                // Enable dark mode
                case "dark":
                    panel.setBackground(Color.BLACK);
                    break;
                
                // Disable dark mode
                case "light":
                    panel.setBackground(Color.WHITE);
                    break;
            
                // Stop the program
                case "stop":
                    in.close();
                    System.exit(0);
                    break;

                // Unknown command
                default:
                    System.out.println("Unknown command " + cutLine[0]);
                    break;
            }
            */
            /*
             * These lines update the UI
             * Repaint holds for all graphical changes (like color changes)
             * Revalidate holds for UI change (like button add/remove)
             */
            //frame.repaint();
            //frame.revalidate();
            
        }
    }
//}