package UI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class LoginInterface extends Window {

    private JFrame frame;

    public LoginInterface(){
        this("Login Interface", 1100, 550);
    }
    public LoginInterface(String framename) {
        this(framename, 1100, 550);
    }
    // Constructeur avec des dimensions spécifiées
    public LoginInterface(String framename, int frameX, int frameY) {
        super(framename,frameX,frameY);
        this.frame = super.getFrame();
        createAndShowGUI2();
    }

    public void createAndShowGUI2() {

        JPanel panel = new JPanel(new GridBagLayout());
        //image appli
        JLabel appImageLabel = new JLabel(new ImageIcon("discord_logo.png"));

        //barre d'entree pour username/password
        JTextField usernameField = new JTextField("select username...");
        //usernameField.setForeground(Color.GRAY);
        JPasswordField passwordField = new JPasswordField("select password...");

        usernameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // Lorsque le champ de texte gagne le focus, effacer le texte s'il est égal à "select username..."
                if (usernameField.getText().equals("select username...") && !e.isTemporary()) {
                    usernameField.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        // Ajouter un bouton "Connect"
        JButton connectButton = new JButton("Connect");
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action à effectuer lors de la connexion
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                System.out.println("User connected: " + username);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(appImageLabel, gbc);

        gbc.gridy++;
        panel.add(usernameField, gbc);

        gbc.gridy++;
        panel.add(passwordField, gbc);

        gbc.gridy++;
        gbc.gridwidth = 2;  // Le bouton occupe deux colonnes
        gbc.anchor = GridBagConstraints.CENTER;  // Le bouton est centré horizontalement
        panel.add(connectButton, gbc);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
    }
}
