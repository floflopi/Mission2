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
import actions.Actions;
public class LoginInterface extends Window {

    protected JFrame frame;
    private String logo_name="images/logo.png";
    private String select_username="select username...";
    private String select_password="select password...";
    private JPanel panel;
    protected CustomInputField usernameField;
    protected CustomInputField passwordField;

    private DatabaseDiscussion disc_db;
    private Actions actions;

    public LoginInterface(String framename,DatabaseUsers users_db,DatabaseDiscussion disc_db,Actions actions) {
        super(framename,800,600,false);
        this.frame = super.getFrame();
        this.disc_db = disc_db;
        this.actions = actions;
        //set background to pink
        panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(221,149,221,255));
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        createAndShowGUI(users_db);
    }
    public void setDefaultValues(String defaultUsername, String defaultPassword) {
        usernameField.getinput().setText(defaultUsername);
        passwordField.getpassword().setText(defaultPassword);
    }
    
    public void try_connect(DatabaseUsers users_db){
        String username = usernameField.getinput().getText();
        String password = new String(passwordField.getpassword().getPassword());
        // check if we can connect 
        if (users_db.IsUserinDb(username,username + " is not in the UsersDatabase, please register if you don't have an account.") != null
        && users_db.good_password(username, password)){
            UserMainUI mainui = new UserMainUI("Main UI", users_db.get_user("username", username),disc_db,users_db,actions);
            frame.dispose(); // close current frame
        }
    }
    public void createAndShowGUI(DatabaseUsers users_db) {

        //image appli
        JLabel appImageLabel = new JLabel(new ImageIcon(logo_name));
        //barre d'entree pour username/password
        usernameField = new CustomInputField(select_username,200,30,20);
        passwordField = new CustomInputField(select_password, 200,30,20,true);
        
        // Ajouter un bouton "Connect"
        JButton connectButton = new JButton("Connect");

        Dimension currentSize = connectButton.getPreferredSize();
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
        panel.add(usernameField.getinput(), gbc);
        
        gbc.gridy++;
        panel.add(passwordField.getpassword(), gbc);
        
        gbc.gridy++;
        gbc.gridwidth = 2;  // Le bouton occupe deux colonnes
        gbc.anchor = GridBagConstraints.CENTER;  // Le bouton est centré horizontalement
        panel.add(connectButton, gbc);

        gbc.gridy++;
        panel.add(registerButton, gbc);
        updateUI();
        
    }
}
