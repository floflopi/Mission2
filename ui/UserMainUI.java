package ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import user.User;
import db.*;
public class UserMainUI extends Window {

    private JSplitPane splitPane;

    private JPanel buttonPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel userPanel;

    private JFrame frame;
    private String no_friend_img = "images/bocchi_sad.png";
    private String deactivated = "images/deactivated.png";
    private String[] features_img_name = new String[]{"images/micro_logo.png","images/camera_logo.png","images/safe_logo.png","images/adulte_logo.png"};
    private JLabel[] features_img = new JLabel[4];

    private JButton friends_btn;
    private JButton newdiscussion_btn;

    private User current_user;
    private DatabaseDiscussion disc_db;
    public UserMainUI(String frameName,User current_user,DatabaseDiscussion disc_db) {
        super(frameName);
        this.frame = super.getFrame();
        this.current_user = current_user;
        this.disc_db = disc_db;
        initializeUI();
        
    }

    private void initializeUI() {
        createLeftPanel();
        createRightPanel();
        //split right and left part 
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(350); // Set the initial divider location
        frame.add(splitPane);
        updateUI();
    }
    
    private void createLeftPanel() {
        leftPanel = new JPanel(new BorderLayout());
        createButtons();
        // scrollable discussion
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().setBackground(new Color(64, 68, 75));
        createUserPanel();
        // Add components to the left panel
        leftPanel.add(buttonPanel, BorderLayout.NORTH); 
        leftPanel.add(scrollPane, BorderLayout.CENTER);
        leftPanel.add(userPanel, BorderLayout.SOUTH);
    }

    private void createButtons(){
        buttonPanel = new JPanel(); // JPanel pour contenir les boutons
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        friends_btn = new JButton("Friends");
        friends_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FriendUI();
            }
        });
        newdiscussion_btn = new JButton("New Discussion");
        newdiscussion_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disc_db.create_discussion(current_user);
            }
        });

        Dimension buttonSize = new Dimension(350,50);
        friends_btn.setMaximumSize(buttonSize);
        newdiscussion_btn.setMaximumSize(buttonSize);

        buttonPanel.add(friends_btn);
        buttonPanel.add(newdiscussion_btn);
        buttonPanel.setPreferredSize(new Dimension(350, 100));
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
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int circleSize = 20; // Ajustez la taille du cercle selon vos besoins
                g2d.setColor(Color.GREEN); // Couleur du cercle
                g2d.fill(new Ellipse2D.Double(0, getHeight()/2 - circleSize/2, circleSize, circleSize));
            }
        };
        circlePanel.setPreferredSize(new Dimension(20, 20)); // Ajustez la taille selon vos besoins
        circlePanel.setBackground(new Color(64, 68, 75)); // Même couleur que le fond de userPanel
        userPanel.add(Box.createHorizontalStrut(8));
        userPanel.add(circlePanel);

        // Ajouter un espace entre le label et l'image
        userPanel.add(Box.createHorizontalStrut(10)); 
        // add 4 images to user panel
        for (int i=0;i<4;i++){
            final int index = i; 
            Image img = new ImageIcon(features_img_name[i]).getImage().getScaledInstance(42,42, Image.SCALE_DEFAULT);
            features_img[i] = new JLabel(new ImageIcon(img));
            features_img[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    // Action à effectuer lorsqu'on clique sur l'image
                    System.out.println("Hello World" + index);
                }
            });
            userPanel.add(features_img[i]);
            userPanel.add(Box.createHorizontalStrut(8));
        }
        userPanel.setPreferredSize(new Dimension(350, 80));
        userPanel.setBackground(new Color(64, 68, 75));
    }

    private void createRightPanel() {
        rightPanel = new JPanel(new BorderLayout());
        JLabel noDiscussionLabel = new JLabel("No Discussion : ( try to make friends !");
        Font newFont = new Font("SansSerif", Font.PLAIN,28); // Ajuster la taille de la police
        noDiscussionLabel.setFont(newFont);
        noDiscussionLabel.setForeground(Color.WHITE);
        noDiscussionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        noDiscussionLabel.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0)); // pose le texte plus bas 

        Image resizedImage = new ImageIcon(no_friend_img).getImage().getScaledInstance((int) (1920/2.7), (int) (1080/2.7), Image.SCALE_DEFAULT);
        JLabel imageLabel = new JLabel(new ImageIcon(resizedImage));

        // Créer un panneau pour contenir le texte et l'image
        JPanel rightContentPanel = new JPanel(new BorderLayout());
        rightContentPanel.add(noDiscussionLabel, BorderLayout.NORTH);
        rightContentPanel.add(imageLabel, BorderLayout.CENTER);

        rightContentPanel.setBackground(new Color(64,68,75));
        // Ajouter le panneau au panneau de droite
        rightPanel.add(rightContentPanel, BorderLayout.CENTER);

    }
}
