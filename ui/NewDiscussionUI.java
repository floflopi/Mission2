package ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import user.User;
import ui.UserMainUI;
import db.*;
public class NewDiscussionUI extends Window {
    private JFrame frame;

    private User current_user;
    private DatabaseDiscussion disc_db;
    private DatabaseUsers users_db;

    private JButton confirm_btn;
    private CustomInputField inputfield;
    private String select_username = "Select all the usernames you want to add to the discussion (separated by a ',')";

    private UserMainUI UserMainUI;
    public NewDiscussionUI(UserMainUI UserMainUI,String frameName, int sizex, int sizey, User current_user, DatabaseDiscussion disc_db, DatabaseUsers users_db) {
        super(frameName, sizex, sizey,false);
        this.frame = super.getFrame();
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.current_user = current_user;
        this.disc_db = disc_db;
        this.users_db = users_db;
        this.UserMainUI = UserMainUI; 
        initializeUI();
    }

    private void initializeUI() {
        frame.getContentPane().setBackground(getblackColor());
        inputfield = new CustomInputField(select_username, 600, 50);
        inputfield.getinput().setFont(new Font("SansSerif", Font.PLAIN,15));
        confirm_btn = new JButton("Confirm");
        confirm_btn.setPreferredSize(new Dimension(150, 40));
        confirm_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (disc_db.verify_disc_creation(select_username,inputfield.getinput().getText(),current_user, users_db)){
                    UserMainUI.update_discussions();
                    frame.dispose();
                   
                }
            }
        });
        // Utiliser GridBagLayout pour plus de contrôle sur la disposition
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 20, 0); // Marge en bas de inputfield
        frame.add(inputfield.getinput(), gbc);

        gbc.gridy = 1; // Ligne suivante
        frame.add(confirm_btn, gbc);
    }
}