package ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.awt.event.KeyAdapter;

import db.DatabaseDiscussion;
import db.DatabaseUsers;
public class LoginInterface extends Window {

    private JFrame frame;
    private String logo_name="images/logo.png";
    private String select_username="select username...";
    private String select_password="select password...";
    private JPanel panel;
    private JTextField usernameField;
    private JPasswordField passwordField;

    private DatabaseDiscussion disc_db;

    public LoginInterface(String framename,DatabaseUsers users_db,DatabaseDiscussion disc_db) {
        super(framename);
        this.frame = super.getFrame();
        this.disc_db = disc_db;
        //set background to pink
        panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(221,149,221,255));
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        createAndShowGUI(users_db);
    }
    public void try_connect(DatabaseUsers users_db){
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        // check if we can connect 
        if (users_db.IsUserinDb(username,username + " is not in the UsersDatabase, please register if you don't have an account.") != null
        && users_db.good_password(username, password)){
            UserMainUI mainui = new UserMainUI("Main UI", users_db.get_user("username", username),disc_db,users_db);
            frame.dispose(); // close current frame
        }
    }
    public void createAndShowGUI(DatabaseUsers users_db) {

        //image appli
        JLabel appImageLabel = new JLabel(new ImageIcon(logo_name));
        //barre d'entree pour username/password
        usernameField = new JTextField(select_username);
        Dimension currentSize = new Dimension((int) (usernameField.getPreferredSize().width * 1.5), (int) (usernameField.getPreferredSize().height * 1.5));
        usernameField.setPreferredSize(currentSize);
        usernameField.setForeground(Color.GRAY);
        usernameField.setCaretPosition(0); // position curseur
        usernameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (usernameField.getText().equals(select_username)) {
                    usernameField.setText("");
                    usernameField.setForeground(Color.BLACK);
                }
            }
        });
        usernameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // curseur au début si aucun mot de passe
                if (usernameField.getText().equals(select_username)){
                    usernameField.setCaretPosition(0);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        passwordField = new JPasswordField(select_password);
        passwordField.setPreferredSize(currentSize);
        passwordField.setCaretPosition(0);
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char)0);
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (Arrays.equals(passwordField.getPassword(), select_password.toCharArray())) {
                    passwordField.setText("");
                    passwordField.setEchoChar('*');
                    passwordField.setForeground(Color.BLACK);
                }
            }
        });
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // curseur au début si aucun mot de passe
                if (Arrays.equals(passwordField.getPassword(), select_password.toCharArray())){
                    passwordField.setCaretPosition(0);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        // Ajouter un bouton "Connect"
        JButton connectButton = new JButton("Connect");
        //connectButton.setBackground(Color.BLUE);
        currentSize = connectButton.getPreferredSize();
        connectButton.setPreferredSize(new Dimension((int) (currentSize.width * 1.2), (int) (currentSize.height * 1.2)));
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try_connect(users_db);
            }
        });
        // Ajouter un bouton "Register"
        JButton registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension((int) (currentSize.width * 1.2), (int) (currentSize.height * 1.2)));
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //try_connect(users_db);
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

        gbc.gridy++;
        panel.add(registerButton, gbc);
        updateUI();
        
    }
}
