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
import java.util.ArrayList;
import java.util.List;

import db.*;
import user.*;
import actions.Actions;
// en fullscreen ne marche pas !
public class FriendUI extends Window {

    private String friend_name="write the username of the friend you want to add...";
    protected JFrame frame;
    private CustomInputField friendNameField;
    private JButton sendRequestButton;
    protected JLabel[] friends_action = new JLabel[4];
    private JScrollPane scrollrequestPane;
    private JPanel topPanel;
    private JPanel friendsrequestContentPanel; // content of scroll panel
    private JPanel friendsPanel;
    private JPanel friendsContentPanel;
    private JPanel MainPanel;

    
    private User current_user;
    private DatabaseDiscussion disc_db;
    private DatabaseUsers users_db;
    private Actions actions;


    private String[] btn_names =  new String[]{"removefriend","block","report"};
    private ArrayList<ArrayList<JButton>> buttons = new ArrayList<>(3);

    public FriendUI(DatabaseUsers users_db,DatabaseDiscussion disc_db,User current_user,Actions actions) {
        super("Friend UI",true); // Vous pouvez ajuster la taille du cadre selon vos besoins
        for (int i=0;i<3;i++){
            buttons.add(new ArrayList<JButton>());
        }
        
        this.actions = actions;
        this.actions.setFriendUI(this);
        this.users_db = users_db;
        this.disc_db = disc_db;
        this.current_user = current_user;
        this.frame = super.getFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initializeUI();
    }
    public void updateFeatures(){
        for (int i=0;i< btn_names.length;i++){
            for (int j=0;j< buttons.get(i).size();j++){
                buttons.get(i).get(j).setVisible(actions.get_optional_features().get(btn_names[i]));
            }
        }
        if (friendsrequestContentPanel != null){
            scrollrequestPane.setVisible(actions.get_optional_features().get("addfriend"));
            friendsrequestContentPanel.setVisible(actions.get_optional_features().get("addfriend"));
        }
        if (topPanel != null)
            topPanel.setVisible(actions.get_optional_features().get("addfriend"));
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
                new AddFriendCommand().execute(friendNameField.getinput().getText(),users_db,null,disc_db,current_user);
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
        Dimension scrollPanelDimension = new Dimension(1000,175);
        friendsrequestContentPanel = new JPanel();
        friendsrequestContentPanel.setBackground(getblackColor());
        
        friendsrequestContentPanel.setLayout(new BoxLayout(friendsrequestContentPanel, BoxLayout.Y_AXIS));
        scrollrequestPane = new JScrollPane(friendsrequestContentPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        friendsrequestContentPanel.setPreferredSize(scrollPanelDimension);
        scrollrequestPane.setMaximumSize(scrollPanelDimension);
        show_friend_request();
        MainPanel.add(scrollrequestPane);

    }
    //display all friend request
    private void show_friend_request(){
        Color[] color = new Color[]{Color.RED,Color.BLACK};
        for (int i=0;i< current_user.get_friendrequest().size();i++){
            //get username friend 
            String friend_user=users_db.get_user("id", String.valueOf(current_user.get_friendrequest().get(i))).get_username();

            FlowLayout demandeLayout = new FlowLayout(FlowLayout.CENTER);
            demandeLayout.setHgap(20);
            JPanel demande = new JPanel(demandeLayout); // Utiliser null pour aucun gestionnaire de disposition
            demande.setBackground(Window.getblackColor());
            demande.setMaximumSize(new Dimension(1000, 60));
            
            CustomLabel userLabel = new CustomLabel(friend_user, 24, Color.WHITE, FlowLayout.CENTER, getblackColor(), 100, 35);

            JButton acceptButton = new JButton("Accept");
            acceptButton.setPreferredSize(new Dimension(100,35));
            acceptButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new AcceptFriendCommand().execute(friend_user, users_db, null, disc_db, current_user);
                    //display message
                    new WindowError("Request Succeed",friend_user + " was successfully added to your friends list !","images/noerror.png");
                    friendsrequestContentPanel.remove(demande);
                    show_all_friends(); //update friend list
                    updateUI();
                }
            });
            JButton declineButton = new JButton("Decline");
            declineButton.setPreferredSize(new Dimension(100,35));
            final int index = i;
            declineButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    current_user.get_friendrequest().remove(current_user.get_friendrequest().get(index));
                    friendsrequestContentPanel.remove(demande);
                    updateUI();
                }
            });

            demande.add(userLabel.getPanel());
            demande.add(acceptButton);
            demande.add(declineButton);

            friendsrequestContentPanel.add(demande);
        }
    }

    private void show_all_friends(){
        friendsContentPanel.removeAll();
        friendsContentPanel.add(Window.getEmptyPanel(1000, 10));
        for (ArrayList<JButton> array:buttons){
            array.clear();
        }
        for (int i=0;i < current_user.get_liste_contact().size();i++){
            User friend_user=users_db.get_user("id", String.valueOf(current_user.get_liste_contact().get(i)));
            FlowLayout friendLayout = new FlowLayout(FlowLayout.CENTER);
            friendLayout.setHgap(25);
            JPanel currentfriendPanel = new JPanel(friendLayout); // Utiliser null pour aucun gestionnaire de disposition
            currentfriendPanel.setBackground(getblackColor());
            currentfriendPanel.setPreferredSize(new Dimension(1000, 60));

            CustomLabel userLabel = new CustomLabel(friend_user.get_username(), 24, Color.WHITE, FlowLayout.CENTER, getblackColor(), 100, 60);

            currentfriendPanel.add(userLabel.getPanel());
            Runnable[] fonction = new Runnable[]{
                () -> new RemoveFriendCommand().execute(null,users_db,null,disc_db,current_user),
                () -> new BlockCommand().execute(null,users_db,null,disc_db,current_user),
                () -> new ReportCommand().execute(null,users_db,null,disc_db,current_user)};

            for (int j=0;j < btn_names.length;j++){
                JButton current_btn = new JButton(btn_names[j]);
                current_btn.setPreferredSize(new Dimension(150,40));
                // remove block report
                final int index=j;
                current_btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        fonction[index].run();
                    }
                });
                currentfriendPanel.add(current_btn);
                buttons.get(j).add(current_btn);
            }
            friendsContentPanel.add(currentfriendPanel);
        }

        updateFeatures();

            
        
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

        JScrollPane scrollPane = new JScrollPane(friendsContentPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        Dimension scrollPanelDimension = new Dimension(1000,600);
        friendsContentPanel.setPreferredSize(scrollPanelDimension);
        scrollPane.setMaximumSize(scrollPanelDimension);
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
        updateFeatures();     
        updateUI();
    }
}
