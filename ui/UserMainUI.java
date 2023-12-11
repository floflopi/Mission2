package ui;
import javax.swing.*;

import actions.Actions;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Arrays;
import user.User;
import discussion.*;
import db.*;
import java.util.List;

public class UserMainUI extends Window {

    protected JSplitPane splitPane;

    private JPanel buttonPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    protected JPanel userPanel;

    protected JPanel members_discPanel;
    private JScrollPane discs_scrolls; // all discussion

    protected JFrame frame;
    protected String deactivated = "images/deactivated.png";
    private String[] features_img_name = new String[]{"images/micro_logo.png","images/camera_logo.png","images/safe_logo.png","images/adulte_logo.png"};
    protected JLabel[] features_img = new JLabel[4];

    private JButton friends_btn;
    protected JButton newdiscussion_btn;
    private JButton disconnect_btn;

    private DiscussionPanel discussionPanel;

    private User current_user;
    private DatabaseDiscussion disc_db;
    private DatabaseUsers users_db;
    protected Actions actions;
    protected int status =0;

    public User getcurrentUser(){
        return current_user;
    }
    public DatabaseDiscussion getDiscDb(){
        return disc_db;
    }
    public DatabaseUsers getUsersDb(){
        return users_db;
    }

    private FriendUI friendui;

    protected Boolean micro = true;
    protected Boolean camera = true;
    protected Boolean safemode = false;
    protected Boolean adulte_mode = true;

    public UserMainUI(String frameName,User current_user,DatabaseDiscussion disc_db,DatabaseUsers users_db,Actions actions) {
        super(frameName,true);
        this.actions = actions;
        this.frame = super.getFrame();
        this.current_user = current_user;
        this.disc_db = disc_db;
        this.users_db = users_db;
        
        initializeUI();
        
    }
    private void initializeUI() {
        createLeftPanel();
        update_discussions();
        String[] usernames = null;
        Discussion first_disc = null;
        if (!disc_db.find_all_disc(current_user,users_db).isEmpty()){
            usernames = disc_db.find_all_disc(current_user,users_db).get(0).split(",");
            first_disc = disc_db.get_discussion(users_db,usernames);
        }
        discussionPanel = new DiscussionPanel(this,first_disc,usernames,actions); // create right panel
        //split right and left part 
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, discussionPanel.getCurrentPanel());
        splitPane.setDividerLocation(350); // Set the initial divider location
        frame.add(splitPane);
        
        updateUI();
    }
    
    private void createLeftPanel() {
        leftPanel = new JPanel(new BorderLayout());
        createButtons();
    
        // scrollable discussion
        members_discPanel = new JPanel();
        members_discPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        members_discPanel.setBackground(getblackColor());
        members_discPanel.setPreferredSize(new Dimension(350,100));
        discs_scrolls = new JScrollPane(members_discPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        discs_scrolls.getViewport().setBackground(getblackColor());
        createUserPanel();
    
        // Add components to the left panel
        leftPanel.add(buttonPanel, BorderLayout.NORTH); 
        leftPanel.add(discs_scrolls, BorderLayout.CENTER);
        leftPanel.add(userPanel, BorderLayout.SOUTH);
    }
    

    private void createButtons(){
        buttonPanel = new JPanel(); // JPanel pour contenir les boutons
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        disconnect_btn = new JButton("Disconnect");
        disconnect_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new LoginInterface("Login Interface", users_db, disc_db,actions);
            }
        });       
        friends_btn = new JButton("Friends");
        friends_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                friendui = new FriendUI(users_db,disc_db,current_user, actions);
            }
        });
        newdiscussion_btn = new JButton("New Discussion");
        newdiscussion_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disc_db.create_discussion(UserMainUI.this,"New Discussion",400,300,current_user,users_db);
            }
        });
        Dimension buttonSize = new Dimension(350,40);
        disconnect_btn.setMaximumSize(buttonSize);
        friends_btn.setMaximumSize(buttonSize);
        newdiscussion_btn.setMaximumSize(buttonSize);

        buttonPanel.add(disconnect_btn);
        buttonPanel.add(friends_btn);
        buttonPanel.add(newdiscussion_btn);
        buttonPanel.setPreferredSize(new Dimension(350, 120));
    }
    private void createUserPanel() {
        userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.X_AXIS));
    
        // Ajouter une boîte de collage horizontal à gauche pour que le user_label reste aligné à gauche
        userPanel.add(Box.createHorizontalGlue());
    
        JLabel user_label = new JLabel(current_user.get_username());
        Font newFont = new Font("SansSerif", Font.PLAIN, 24);
        user_label.setFont(newFont);
        user_label.setForeground(Color.WHITE);
    
        // Ajouter le label aligné à gauche
        userPanel.add(user_label,BorderLayout.WEST);
        JPanel circlePanel = new JPanel() {
            // en ligne,déconnecté,ne pas déranger

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int circleSize = 20;
                if (status == 0) {
                    g2d.setColor(Color.GREEN);
                } else if (status == 1){
                    g2d.setColor(Color.ORANGE);
                }
                else{
                    g2d.setColor(Color.RED);
                }

                g2d.fill(new Ellipse2D.Double(0, getHeight() / 2 - circleSize / 2, circleSize, circleSize));
            }
        };
        circlePanel.setPreferredSize(new Dimension(20, 20)); 
        circlePanel.setBackground(getblackColor()); 
        //change color when clicked
        circlePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                status = (status + 1) % 3;
                ((JPanel) e.getSource()).repaint(); 
            }
        });
        userPanel.add(Box.createHorizontalStrut(8));
        userPanel.add(circlePanel);

        // Ajouter un espace entre le label et l'image
        userPanel.add(Box.createHorizontalStrut(10)); 
        // add 4 images to user panel
        for (int i=0;i<4;i++){
            final int index = i; 
            Image img = new ImageIcon(features_img_name[i]).getImage().getScaledInstance(42,42, Image.SCALE_DEFAULT);
            features_img[i] = new JLabel(new ImageIcon(img));

            Image img_deactivated = new ImageIcon(deactivated).getImage().getScaledInstance(42, 42, Image.SCALE_DEFAULT);
            JLabel imgLabel2 = new JLabel(new ImageIcon(img_deactivated));
            if (i!=2)
                imgLabel2.setVisible(false); // features are by default activated except safe mode
            JPanel imagePanel = new JPanel();
            imagePanel.setLayout(new OverlayLayout(imagePanel));
            imagePanel.setBackground(getblackColor());
            imagePanel.add(imgLabel2);
            imagePanel.add(features_img[i]);

            features_img[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    // micro camera safe adulte 
                    if (index == 0){
                        micro = !micro;
                        imgLabel2.setVisible(!micro); // contraire de la feature
                        actions.activate_features(micro,"micro");
                    }
                    else if (index == 1){
                        camera = !camera;
                        imgLabel2.setVisible(!camera);
                        actions.activate_features(camera,"camera");
                    }
                    else if (index == 2){
                        safemode = !safemode;
                        imgLabel2.setVisible(!safemode);
                        for (String feature:new String[]{"block","exclude","report"}){
                            actions.activate_features(!safemode,feature);
                        }
                    }
                    else{
                        adulte_mode = !adulte_mode;
                        imgLabel2.setVisible(!adulte_mode);
                        for (String feature:new String[]{"sendimage","sendvocaux","sendgif","sendautresfichiers","addfriend","acceptfriend","sendvideo"}){
                            actions.activate_features(adulte_mode,feature);
                        }
                    }
                }
            });


            userPanel.add(imagePanel);
            userPanel.add(Box.createHorizontalStrut(8));
        }
        userPanel.setPreferredSize(new Dimension(350, 80));
        userPanel.setBackground(Window.getblackColor());
    }
    //seek all discussions available
    public void update_discussions(){
        ArrayList<String> discs = disc_db.find_all_disc(current_user,users_db);
        System.out.println(discs);
        if (!discs.isEmpty()){
            for (String discussion:discs){
                JButton currentdisc_btn= new JButton(discussion);
                currentdisc_btn.setPreferredSize(new Dimension(350,50));
                currentdisc_btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (discussionPanel.getCurrentIntPanel() == 0){
                            String[] usernames = (disc_db.find_all_disc(current_user,users_db).get(0) + "," + current_user.get_username()).split(",");
                            Discussion first_disc = disc_db.get_discussion(users_db,usernames);
                            discussionPanel.initDiscussionPanel(first_disc,usernames);
                            splitPane.setRightComponent(discussionPanel.getCurrentPanel());
                        }
                        
                    }
                });
                members_discPanel.add(currentdisc_btn);

            }
        }
        updateUI();
        return;
    }
}
