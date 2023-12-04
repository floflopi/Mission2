package ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;  // Remove org.w3c.dom.events.MouseEvent import
import commands.*;
import java.awt.event.MouseEvent;


import db.*;
import user.*;

public class FriendUI extends Window {

    private String friend_name="write the username of the friend you want to add...";
    private JFrame frame;
    private JTextField friendNameField;
    private JButton sendRequestButton;
    private JLabel[] friends_action = new JLabel[3];

    private JPanel topPanel;
    private JPanel friendsrequestPanel;
    private JPanel friendsPanel;
    private User current_user;
    private DatabaseDiscussion disc_db;
    private DatabaseUsers users_db;

    private String[] images_icon = new String[]{"images/call_button.png","images/camera_button.png","images/message_button.png"};

    public FriendUI(DatabaseUsers users_db,DatabaseDiscussion disc_db,User current_user) {
        super("Friend UI"); // Vous pouvez ajuster la taille du cadre selon vos besoins
        this.users_db = users_db;
        this.disc_db = disc_db;
        this.current_user = current_user;
        this.frame = super.getFrame();
        initializeUI();
    }
    private void createTopPanel(){
        topPanel = new JPanel();
        topPanel.setBackground(getblackColor());
        topPanel.setPreferredSize(new Dimension(frame.getWidth(),100));
        
        friendNameField = new JTextField(friend_name);
        friendNameField.setPreferredSize(new Dimension(frame.getWidth() * 3/4,40));
        Font newFont = new Font("SansSerif", Font.PLAIN,27); // Ajuster la taille de la police
        friendNameField.setFont(newFont);
        friendNameField.setForeground(Color.GRAY);
        friendNameField.setCaretPosition(0); // position curseur
        friendNameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (friendNameField.getText().equals(friend_name)) {
                    friendNameField.setText("");
                    friendNameField.setForeground(Color.BLACK);
                }
            }
        });
        friendNameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // curseur au début si aucun mot de passe
                if (friendNameField.getText().equals(friend_name)){
                    friendNameField.setCaretPosition(0);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
            }
        });

        sendRequestButton = new JButton("Send Friend Request");
        sendRequestButton.setPreferredSize(new Dimension(frame.getWidth()/5,40));
        sendRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddFriendCommand().execute(friendNameField.getText(),users_db,disc_db,current_user);
            }
        });
        topPanel.add(friendNameField);
        topPanel.add(sendRequestButton);

        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
    }
  
    private void createFriendRequestPanel() {
        friendsrequestPanel = new JPanel();
        friendsrequestPanel.setBackground(getblackColor());
    
        // texte friend request
        JLabel friendRequestsLabel = new JLabel("Number of Friend Requests : "+ current_user.get_friendrequest().size());
        Font newFont = new Font("SansSerif", Font.PLAIN, 24);
        friendRequestsLabel.setFont(newFont);
        friendRequestsLabel.setForeground(Color.WHITE);
        friendRequestsLabel.setHorizontalAlignment(JLabel.CENTER);
        friendsrequestPanel.add(friendRequestsLabel);
    
        // Ajouter un JScrollPane en dessous pour contenir les demandes d'amis
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(getblackColor());
        JScrollPane scrollPane = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(frame.getWidth(), 175));  // Ajustez la taille selon vos besoins
        friendsrequestPanel.add(scrollPane);
        show_friend_request(contentPanel);
    }
    //display all friend request
    private void show_friend_request(JPanel contentPanel){
        for (int i=0;i< current_user.get_friendrequest().size();i++){
            //get username friend 
            String friend_user=users_db.get_user("id", String.valueOf(current_user.get_friendrequest().get(i))).get_username();
            JPanel demande = new JPanel(null); // Utiliser null pour aucun gestionnaire de disposition
            demande.setBackground(getblackColor());
            demande.setPreferredSize(new Dimension(frame.getWidth(), 175/3));
            
            JLabel user_txt = new JLabel(friend_user);
            user_txt.setForeground(Color.WHITE);
            user_txt.setFont(new Font("SansSerif", Font.PLAIN, 24));
            user_txt.setBounds(10,5, 200, 175/4);
            
            JButton acceptButton = new JButton("Accept");
            acceptButton.setBounds(650, 10, 100, 175/5); // Définir la position et la taille du JButton
            acceptButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    current_user.accept_friend_request(friend_user, users_db);
                    //display message
                    new WindowError("Request Succeed",friend_user + " was successfully added to your friends list !","images/noerror.png");
                    contentPanel.remove(demande);
                }
            });
            JButton declineButton = new JButton("Decline");
            declineButton.setBounds(780, 10, 100, 175/5); // Définir la position et la taille du JButton
            final int index = i;
            declineButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    current_user.get_friendrequest().remove(current_user.get_friendrequest().get(index));
                    contentPanel.remove(demande);
                }
            });
            demande.add(user_txt);
            demande.add(acceptButton);
            demande.add(declineButton);
            contentPanel.add(demande);
        }
    }
    //display current friends status 
    private void createCurrentFriendsPanel(){
        friendsPanel = new JPanel();
        friendsPanel.setBackground(getblackColor());
        friendsPanel.setPreferredSize(new Dimension(frame.getWidth(),350));
        // texte friend
        JLabel friendsLabel = new JLabel("Friends List");
        Font newFont = new Font("SansSerif", Font.PLAIN, 24);
        friendsLabel.setFont(newFont);
        friendsLabel.setForeground(Color.WHITE);
        friendsLabel.setHorizontalAlignment(JLabel.CENTER);
        friendsPanel.add(friendsLabel);
        //scroll panel
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(getblackColor());
        JScrollPane scrollPane = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(frame.getWidth(), 300));  // Ajustez la taille selon vos besoins
        friendsPanel.add(scrollPane);
        // cree x friends pour les mettre dans le panel 
        JPanel currentfriendPanel = new JPanel(null); // Utiliser null pour aucun gestionnaire de disposition
        currentfriendPanel.setBackground(getblackColor());
        currentfriendPanel.setPreferredSize(new Dimension(frame.getWidth(), 300/3));
        JLabel user_txt = new JLabel("Je suis un ami");
        user_txt.setForeground(Color.WHITE);
        newFont = new Font("SansSerif", Font.PLAIN, 24);
        user_txt.setFont(newFont);
        user_txt.setBounds(10,5, 200, 300/4); 
        currentfriendPanel.add(user_txt);
        currentfriendPanel.add(Box.createHorizontalStrut(8));
        // add images to current friend panel
        for (int i=0;i<3;i++){
            final int index = i; 
            Image img = new ImageIcon(images_icon[i]).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT);
            friends_action[i] = new JLabel(new ImageIcon(img));
            friends_action[i].setBounds(675 + 75 * i,20,50,50);
            friends_action[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    // Action à effectuer lorsqu'on clique sur l'image
                    System.out.println("Hello World" + index);
                }
            });
            currentfriendPanel.add(friends_action[i]);
            currentfriendPanel.add(Box.createHorizontalStrut(8));
        }
    
        contentPanel.add(currentfriendPanel);
    }
    private void initializeUI() {
        createTopPanel();
        createFriendRequestPanel();
        createCurrentFriendsPanel();        
        // Ajouter les composants au panneau principal
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(friendsrequestPanel, BorderLayout.CENTER);
        frame.add(friendsPanel, BorderLayout.SOUTH);

        // Mettre à jour l'interface utilisateur
        updateUI();
    }
}
