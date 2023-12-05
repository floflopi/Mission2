package ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import user.User;
import discussion.*;
import db.*;
//right panel of main ui
public class DiscussionPanel{
    private UserMainUI UserMainUI; // get functions of usermainui
    private JPanel NoFriendPanel; // case of no discussion yet
    private JPanel discussionPanel;

    private JPanel searchPanel;
    private JPanel actiondiscPanel;

    private CustomInputField searchFromUserfield;
    private CustomInputField searchFromDatefield;


    private int current_panel = 0;
    private String no_friend_img="images/bocchi_sad.png";
    public DiscussionPanel(UserMainUI UserMainUI,boolean nofriend){
        this.UserMainUI = UserMainUI;
        if (nofriend){
            initNoFriendPanel();
        }
        else{
            initDiscussionPanel(null);
        }
    }
    public int getCurrentIntPanel(){
        return current_panel;
    }
    public JPanel getCurrentPanel(){
        if (current_panel == 0){
            return NoFriendPanel;
        }
        return discussionPanel;
    }

    public void initNoFriendPanel(){
        NoFriendPanel = new JPanel(new BorderLayout());
        JLabel noDiscussionLabel = new JLabel("No Discussion : ( try to make friends !");
        noDiscussionLabel.setFont(new Font("SansSerif", Font.PLAIN,28));
        noDiscussionLabel.setForeground(Color.WHITE);
        noDiscussionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        noDiscussionLabel.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0)); // pose le texte plus bas 

        Image resizedImage = new ImageIcon(no_friend_img).getImage().getScaledInstance((int) (1920/1.2), (int) (1080/1.2), Image.SCALE_DEFAULT);
        JLabel imageLabel = new JLabel(new ImageIcon(resizedImage));

        // Créer un panneau pour contenir le texte et l'image
        JPanel ContentPanel = new JPanel(new BorderLayout());
        ContentPanel.add(noDiscussionLabel, BorderLayout.NORTH);
        ContentPanel.add(imageLabel, BorderLayout.CENTER);
        ContentPanel.setBackground(UserMainUI.getblackColor());
        // Ajouter le panneau au panneau de droite
        NoFriendPanel.add(ContentPanel, BorderLayout.CENTER);
        NoFriendPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 620)); 
    }

    public void createSearchPanel(){
         
        searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Alignez les composants à gauche
        searchPanel.setBackground(Window.getblackColor());
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        searchFromDatefield = new CustomInputField("Write a valid date (JJ/MM/YYYY)", 350, 40);
        searchFromUserfield = new CustomInputField("Write the username you want to find messages", 350, 40);

        JButton searchFromDate_btn = new JButton("Search");
        JButton searchFromUser_btn = new JButton("Search");

        Dimension dim_btn = new Dimension(100,40);
        searchFromDate_btn.setPreferredSize(dim_btn);
        searchFromUser_btn.setPreferredSize(dim_btn);

        ((FlowLayout) searchPanel.getLayout()).setHgap(20);
        //searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        searchPanel.add(searchFromDatefield.getinput());
        searchPanel.add(searchFromDate_btn);

        //searchPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        ((FlowLayout) searchPanel.getLayout()).setVgap(20);
        searchPanel.add(searchFromUserfield.getinput());
        searchPanel.add(searchFromUser_btn);

    }
    public void createActionDiscussionPanel(String members_username){
        actiondiscPanel = new JPanel();
        actiondiscPanel.setBackground(Window.getblackColor());
        actiondiscPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        actiondiscPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10)); // Alignez les composants à gauche

        
        JLabel Membres_txt = new JLabel("Member(s) : " + UserMainUI.getcurrentUser().get_username() + "," + members_username);
        Membres_txt.setFont(new Font("SansSerif", Font.PLAIN,27));
        Membres_txt.setForeground(Color.WHITE);
        actiondiscPanel.add(Membres_txt);

        String[] nom_images = new String[]{"images/call_button.png","images/camera_button.png"};
        for (int i=0;i<2;i++){
            Image resizedImage = new ImageIcon(nom_images[i]).getImage().getScaledInstance(40,40, Image.SCALE_DEFAULT);
            JLabel imageLabel = new JLabel(new ImageIcon(resizedImage));
            actiondiscPanel.add(imageLabel);
        }
        String[] buttons_name = new String[]{"Add","Exclude","Quit"};
        for (int i=0;i<3;i++){
            JButton current_button = new JButton(buttons_name[i]);
            current_button.setPreferredSize(new Dimension(90,40));
            actiondiscPanel.add(current_button);
        }

    }
    // affiche les discussions tel que : 
    // Username : message + date + bouton reaction (simple coeur image qu on peut appuyer dessus) + répondre (permet d envoyer une notif a l user)
    public void createDiscussionPanel(){

    }
    //inputfield + send bouton + image épingle pour fichier qui va ouvrir une window avec les choix différent (pour l instant appuyer sur image envoie juste un message Image.png)
    // 
    public void createSendMessagePanel(){

    }
    public void initDiscussionPanel(String members_username) {
        current_panel = 1;
        discussionPanel = new JPanel(new BorderLayout());
        discussionPanel.setLayout(new BoxLayout(discussionPanel, BoxLayout.Y_AXIS));

        createSearchPanel();
        createActionDiscussionPanel(members_username);
        // Panel 3
        JPanel discPanel = new JPanel();
        discPanel.setBackground(Color.BLUE);
        discPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 820));

        // Panel 4
        JPanel sendmessagePanel = new JPanel();
        sendmessagePanel.setBackground(Color.YELLOW);
        sendmessagePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        // Ajouter les panneaux dans l'ordre que vous voulez
        discussionPanel.add(searchPanel);
        discussionPanel.add(actiondiscPanel);
        discussionPanel.add(discPanel); // no yet made
        discussionPanel.add(sendmessagePanel);
        // Set the preferred size after adding to discussionPanel
    }
}
