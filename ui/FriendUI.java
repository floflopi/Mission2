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

// en fullscreen ne marche pas !
public class FriendUI extends Window {

    private String friend_name="write the username of the friend you want to add...";
    protected JFrame frame;
    private CustomInputField friendNameField;
    private JButton sendRequestButton;
    private JLabel[] friends_action = new JLabel[4];

    private JPanel topPanel;
    private JPanel friendsrequestPanel;
    private JPanel friendsrequestContentPanel; // content of scroll panel
    private JPanel friendsPanel;
    private JPanel friendsContentPanel;
    private JPanel MainPanel;
    private User current_user;
    private DatabaseDiscussion disc_db;
    private DatabaseUsers users_db;

    private String[] images_icon = new String[]{"images/call_button.png","images/camera_button.png","images/message_button.png","images/block_user.png"};

    public FriendUI(DatabaseUsers users_db,DatabaseDiscussion disc_db,User current_user) {
        super("Friend UI",true); // Vous pouvez ajuster la taille du cadre selon vos besoins
        this.users_db = users_db;
        this.disc_db = disc_db;
        this.current_user = current_user;
        this.frame = super.getFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initializeUI();
    }
    private void createTopPanel(){
        FlowLayout topLayout = new FlowLayout(FlowLayout.CENTER);
        topLayout.setHgap(20);

        topPanel = new JPanel(topLayout);
        topPanel.setBackground(getblackColor());
        topPanel.setMaximumSize(new Dimension(frame.getWidth(),100));
        
        friendNameField = new CustomInputField(friend_name, frame.getWidth() * 6/10, 40, 27);

        sendRequestButton = new JButton("Send Friend Request");
        sendRequestButton.setPreferredSize(new Dimension(frame.getWidth()/6,40));
        sendRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddFriendCommand().execute(friendNameField.getinput().getText(),users_db,disc_db,current_user);
            }
        });
        topPanel.add(friendNameField.getinput());
        topPanel.add(sendRequestButton);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        MainPanel.add(topPanel);
    }
    private void createFriendRequestPanel() {
        MainPanel.add(Window.getEmptyPanel(1920, 20));
        // texte friend request
        CustomLabel friendRequestsLabel = new CustomLabel("Number of Friend Requests : "+ current_user.get_friendrequest().size(), 24,Color.white,FlowLayout.CENTER,
        getblackColor(),1920,50);
        MainPanel.add(friendRequestsLabel.getPanel());  
        MainPanel.add(Window.getEmptyPanel(1920, 20));  
        // Ajouter un JScrollPane en dessous pour contenir les demandes d'amis
        friendsrequestContentPanel = new JPanel();
        friendsrequestContentPanel.setBackground(getblackColor());
        friendsrequestContentPanel.setMaximumSize(new Dimension(700,175));
        friendsrequestContentPanel.setLayout(new BoxLayout(friendsrequestContentPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(friendsrequestContentPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //friendsrequestPanel.add(scrollPane);
        show_friend_request();
        MainPanel.add(scrollPane);

    }
    //display all friend request
    private void show_friend_request(){
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
                    friendsrequestContentPanel.remove(demande);
                    show_all_friends(); //update friend list
                    updateUI();
                }
            });
            JButton declineButton = new JButton("Decline");
            declineButton.setBounds(780, 10, 100, 175/5); // Définir la position et la taille du JButton
            final int index = i;
            declineButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    current_user.get_friendrequest().remove(current_user.get_friendrequest().get(index));
                    friendsrequestContentPanel.remove(demande);
                    updateUI();
                }
            });
            demande.add(user_txt);
            demande.add(acceptButton);
            demande.add(declineButton);
            friendsrequestContentPanel.add(demande);
        }
    }
    private void show_all_friends(){
        for (int i=0;i < current_user.get_liste_contact().size();i++){
            User friend_user=users_db.get_user("id", String.valueOf(current_user.get_liste_contact().get(i)));
            JPanel currentfriendPanel = new JPanel(null); // Utiliser null pour aucun gestionnaire de disposition
            currentfriendPanel.setBackground(getblackColor());
            currentfriendPanel.setPreferredSize(new Dimension(frame.getWidth(), 300/3));
            JLabel user_txt = new JLabel(friend_user.get_username());
            user_txt.setForeground(Color.WHITE);
            user_txt.setFont(new Font("SansSerif", Font.PLAIN, 24));
            user_txt.setBounds(10,5, 200, 300/4); 
            currentfriendPanel.add(user_txt);
            currentfriendPanel.add(Box.createHorizontalStrut(8));
            // add images to current friend panel
            for (int j=0;j<images_icon.length;j++){
                final int index = j; 
                Image img = new ImageIcon(images_icon[j]).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT);
                friends_action[j] = new JLabel(new ImageIcon(img));
                friends_action[j].setBounds(600 + 75 * j,20,50,50);
                friends_action[j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        // Action à effectuer lorsqu'on clique sur l'image
                        System.out.println("Hello World" + index);
                    }
                });
                currentfriendPanel.add(friends_action[j]);
                currentfriendPanel.add(Box.createHorizontalStrut(8));
            }
            friendsContentPanel.add(currentfriendPanel);
        }

            
        
    }
    //display current friends status 
    private void createCurrentFriendsPanel(){
        friendsPanel = new JPanel();
        friendsPanel.setBackground(getblackColor());
        friendsPanel.setPreferredSize(new Dimension(frame.getWidth(),350));
        // texte friend
        MainPanel.add(Window.getEmptyPanel(1920, 20));
        CustomLabel friends_label = new CustomLabel("Friends List", 24,Color.white,FlowLayout.CENTER,getblackColor(),1920,50);
        MainPanel.add(friends_label.getPanel());
        MainPanel.add(Window.getEmptyPanel(1920, 20));

        //scroll panel
        friendsContentPanel = new JPanel();
        friendsContentPanel.setBackground(getblackColor());
        friendsContentPanel.setMaximumSize(new Dimension(800,250));
        JScrollPane scrollPane = new JScrollPane(friendsContentPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // cree x friends pour les mettre dans le panel 
        show_all_friends();
        MainPanel.add(scrollPane);
        MainPanel.add(Window.getEmptyPanel(1920, 80));
    }
    private void initializeUI() {
        MainPanel = new JPanel(new BorderLayout());
        MainPanel.setLayout(new BoxLayout(MainPanel, BoxLayout.Y_AXIS));
        MainPanel.setBackground(getblackColor());
        frame.add(MainPanel);
        createTopPanel();
        createFriendRequestPanel();
        createCurrentFriendsPanel();        
        updateUI();
    }
}
