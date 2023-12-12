package ui;
import java.util.Scanner;

import javax.swing.*;

import discussion.Discussion;
import ui.LoginInterface;
import ui.WindowError;
import user.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.awt.event.KeyAdapter;

import db.*;
import user.*;
import commands.*;
import actions.*;
public class MediaUI extends Window{

    private Discussion current_discussion;
    private DatabaseUsers users_db;
    private DatabaseDiscussion disc_db;
    private DiscussionPanel disc_panel;

    private User current_user;
    protected JFrame frame;
    private JPanel MainPanel;
    private String[] file_btns_img = new String[]{"image","vocaux","gif","video","autresfichiers"};
    private JButton[] file_btns = new JButton[file_btns_img.length];
    private Actions actions;
    Command[] commands = {
        new SendImageCommand(),
        new SendVocauxCommand(),
        new SendGifCommand(),
        new SendVideoCommand(),
        new SendAutresFichiersCommand()
    };
    public JButton[] getButtons(){
        return file_btns;
    }
    public MediaUI(DatabaseUsers users_db,DatabaseDiscussion disc_db,User current_user,Discussion current_disc,DiscussionPanel disc_panel,Actions actions){
        super("Media UI",1000,800,false); // Vous pouvez ajuster la taille du cadre selon vos besoins
        this.actions = actions;
        this.actions.setMediaUi(this);
        this.users_db = users_db;
        this.disc_db = disc_db;
        this.current_user = current_user;
        this.current_discussion = current_disc;
        this.disc_panel = disc_panel;
        this.frame = super.getFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initializeUI();
        updateFeatures();
        frame.add(MainPanel);
    }
    public void updateFeatures(){
        for (int i=0;i< file_btns_img.length;i++){
            file_btns[i].setVisible(actions.get_optional_features().get("send" + file_btns_img[i]));
        }
    }
    private void initializeUI() {
        MainPanel = new JPanel();
        MainPanel.setLayout(new BoxLayout(MainPanel, BoxLayout.Y_AXIS));
        MainPanel.setBackground(getblackColor());
        
        MainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        //txt 
        CustomLabel texte = new CustomLabel("Select the type of file you want to send", 25, Color.white, FlowLayout.CENTER, getblackColor(), 1000, 40);
        MainPanel.add(texte.getPanel());
        MainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        for (int i = 0; i < file_btns_img.length; i++) {
            int finalI=i;
            file_btns[i] = new JButton(file_btns_img[i]);
            file_btns[i].setMaximumSize(new Dimension(150, 75));
            file_btns[i].setAlignmentX(Component.CENTER_ALIGNMENT);  // Centrer le bouton horizontalement
            file_btns[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // do something
                    commands[finalI].execute(file_btns_img[finalI], users_db, current_discussion, disc_db, current_user);
                    disc_panel.updateMessagesPanel(); // reupload messages
                    frame.dispose();
                }
            });
            MainPanel.add(file_btns[i]);
            MainPanel.add(Box.createRigidArea(new Dimension(0, 40)));  // Espacement entre les boutons
        }
    
        updateUI();
    }
    
}