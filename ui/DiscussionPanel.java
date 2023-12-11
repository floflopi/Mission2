package ui;
import javax.swing.*;

import commands.SendMessageCommand;

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
import message.*;
import actions.*;
//right panel of main ui
public class DiscussionPanel{
    private UserMainUI UserMainUI; // get functions of usermainui
    private JPanel NoFriendPanel; // case of no discussion yet
    
    private JPanel discussionPanel;
    private JPanel sendMessagePanel;
    private JPanel[] searchPanels;
    private JPanel actiondiscPanel;
    private JPanel messagesPanel;

    private CustomInputField sendMessagefield;

    private int current_panel = 0;

    private String file_img="images/file_button.png";
    private String no_friend_img="images/bocchi_sad.png";
    private String[] members;
    private Discussion current_discussion;
    private Actions actions;
    public DiscussionPanel(UserMainUI UserMainUI,Discussion current_disc,String[] members,Actions actions){
        this.actions = actions;
        this.UserMainUI = UserMainUI;
        if (current_disc == null){
            initNoFriendPanel();
        }
        else{
            initDiscussionPanel(current_disc,members);
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
    public void updateFeatures(){

    }
    public void find_current_discussion(String members_username){
        String[] all_members = (members_username+ "," + UserMainUI.getcurrentUser().get_username()).split(",");
        current_discussion = UserMainUI.getDiscDb().get_discussion(UserMainUI.getDiscDb().get_members_id(UserMainUI.getUsersDb(), all_members));
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
        ContentPanel.setBackground(Window.getblackColor());
        // Ajouter le panneau au panneau de droite
        NoFriendPanel.add(ContentPanel, BorderLayout.CENTER);
        NoFriendPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 620)); 
    }
    // add searchfromdate and user panel
    public void createsearchPanel(){
        searchPanels = new JPanel[2];
        JButton[] search_btn = new JButton[2];
        CustomInputField[] searchfields = new CustomInputField[2];
        String[] search_txt = new String[]{"Write a valid date (JJ/MM/YYYY)","Write the username you want to find messages"};
        Dimension dim_btn = new Dimension(100,40);
        for (int i=0;i<2;i++){
            FlowLayout searchLayout = new FlowLayout(FlowLayout.CENTER);
            searchPanels[i] = new JPanel(searchLayout);
            searchPanels[i].setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
            searchPanels[i].setBackground(Window.getblackColor());
            searchLayout.setHgap(20);

            searchfields[i] = new CustomInputField(search_txt[i], 550, 40,20);
            search_btn[i] = new JButton("Search");
            search_btn[i].setPreferredSize(dim_btn);

            searchPanels[i].add(searchfields[i].getinput());
            searchPanels[i].add(search_btn[i]);

            discussionPanel.add(searchPanels[i]);
        }
    }
    public void createActionDiscussionPanel(){
        FlowLayout actionLayout = new FlowLayout(FlowLayout.CENTER);
        actionLayout.setHgap(20);
        actiondiscPanel = new JPanel(actionLayout);
        actiondiscPanel.setBackground(Window.getblackColor());
        actiondiscPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
      
        JLabel Membres_txt = new JLabel("Members : "  + String.join(",",members));
        Membres_txt.setFont(new Font("SansSerif", Font.PLAIN,27));
        Membres_txt.setForeground(Color.WHITE);
        actiondiscPanel.add(Membres_txt);

        String[] nom_images = new String[]{"images/call_button.png","images/camera_button.png"};
        //appel micro, appel camera
        Runnable[] fonction = new Runnable[]{
            () -> new MediaUI(UserMainUI.getUsersDb(), UserMainUI.getDiscDb(),
            UserMainUI.getcurrentUser(), current_discussion, DiscussionPanel.this,actions),
            () -> new MediaUI(UserMainUI.getUsersDb(), UserMainUI.getDiscDb(),
            UserMainUI.getcurrentUser(), current_discussion, DiscussionPanel.this,actions)
        };
        for (int i=0;i<2;i++){
            // remplacer par les actions disponible 
            ImageButton action_btn = new ImageButton(nom_images[i], 40, 40,fonction[i]);
            actiondiscPanel.add(action_btn.getButton());
        }
        String[] buttons_name = new String[]{"Add","Exclude","Quit"};
        for (int i=0;i<3;i++){
            JButton current_button = new JButton(buttons_name[i]);
            current_button.setPreferredSize(new Dimension(90,40));
            actiondiscPanel.add(current_button);
        }
        discussionPanel.add(actiondiscPanel);

    }
    // affiche les discussions tel que : 
    // Username : message + date + bouton reaction (simple coeur image qu on peut appuyer dessus) + répondre (permet d envoyer une notif a l user)
    public void createMessagesPanel(){
        FlowLayout sendLayout = new FlowLayout(FlowLayout.CENTER);
        messagesPanel = new JPanel();
        messagesPanel.setBackground(Window.getblackColor());
        messagesPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,620));
        messagesPanel.setLayout(new BoxLayout(messagesPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(messagesPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //scrollPane.setPreferredSize(new Dimension(Integer.MAX_VALUE, 620));  // Ajustez la taille selon vos besoins
        discussionPanel.add(scrollPane);
        updateMessagesPanel();
    } 
    public void createSendMessagePanel(){
        FlowLayout sendLayout = new FlowLayout(FlowLayout.CENTER);
        sendLayout.setVgap(25);
        sendLayout.setHgap(15);
        sendMessagePanel = new JPanel(sendLayout);
        sendMessagePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,100));
        sendMessagePanel.setBackground(Window.getblackColor());
        sendMessagefield = new CustomInputField("Write the message you want to send ",600,50,30);


        ImageButton file_btn = new ImageButton(file_img, 50, 50,() -> new MediaUI(UserMainUI.getUsersDb(), UserMainUI.getDiscDb(),
        UserMainUI.getcurrentUser(), current_discussion, DiscussionPanel.this,actions));
        JButton send_btn =  new JButton("Send");
        send_btn.setPreferredSize(new Dimension(100,50));
        send_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SendMessageCommand().execute(sendMessagefield.getinput().getText(),UserMainUI.getUsersDb(),current_discussion,UserMainUI.getDiscDb(),UserMainUI.getcurrentUser());
                updateMessagesPanel(); // reupload messages
            }
        });

        sendMessagePanel.add(sendMessagefield.getinput());
        sendMessagePanel.add(file_btn.getButton());
        sendMessagePanel.add(send_btn);
        discussionPanel.add(sendMessagePanel);
    }
    public void updateMessagesPanel(){
        messagesPanel.removeAll(); // reupload messages
        ArrayList<Message> messages = current_discussion.getmessages();
        for (int i=0;i < messages.size();i++){
            Message current_message = messages.get(i);

            FlowLayout messageLayout = new FlowLayout(FlowLayout.LEFT);
            messageLayout.setVgap(15);
            messageLayout.setHgap(25);
            JPanel currentPanel = new JPanel(messageLayout);
            currentPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,80));
            currentPanel.setBackground(Window.getblackColor());

            JLabel username = new JLabel(UserMainUI.getUsersDb().get_user("id",String.valueOf(current_message.get_from_user_id())).get_username() + " : ");
            username.setFont(new Font("SansSerif", Font.PLAIN,28));
            username.setForeground(Color.WHITE);
            JLabel message_txt = new JLabel(current_message.get_contenu());
            message_txt.setFont(new Font("SansSerif", Font.PLAIN,28));
            message_txt.setForeground(Color.WHITE);
            System.out.println(current_message.get_contenu());
            currentPanel.add(username);
            currentPanel.add(message_txt);
            messagesPanel.add(currentPanel);
        }
        UserMainUI.updateUI();

    }

    public void addEmptyPanel(int sizey){
        JPanel emptyPanel = new JPanel();
        emptyPanel.setBackground(Window.getblackColor());
        emptyPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,sizey));
        discussionPanel.add(emptyPanel);
    }

    public void initDiscussionPanel(Discussion current_disc,String[] members_username) {
        this.current_discussion = current_disc;
        this.members = members_username;
        current_panel = 1;
        discussionPanel = new JPanel(new BorderLayout());
        discussionPanel.setLayout(new BoxLayout(discussionPanel, BoxLayout.Y_AXIS));

        addEmptyPanel(10);
        createsearchPanel();
        addEmptyPanel(10);
        createActionDiscussionPanel();
        addEmptyPanel(10);
        createMessagesPanel();
        createSendMessagePanel();

    }
}
