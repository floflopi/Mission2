package ui;
import javax.swing.*;
import java.awt.*;

public class FriendUI extends Window {

    private JFrame frame;
    private JTextField friendNameField;
    private JButton sendRequestButton;
    private JLabel friendsLabel;
    private JPanel friendsPanel;

    private JPanel topPanel;
    public FriendUI() {
        super("Friend UI"); // Vous pouvez ajuster la taille du cadre selon vos besoins
        this.frame = super.getFrame();
        initializeUI();
    }
    private void createTopPanel(){
        topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(frame.getWidth(),100));
        
        friendNameField = new JTextField();
        friendNameField.setPreferredSize(new Dimension(frame.getWidth() * 2/3,40));
        Font newFont = new Font("SansSerif", Font.PLAIN,30); // Ajuster la taille de la police
        friendNameField.setFont(newFont);

        sendRequestButton = new JButton("Send Friend Request");
        sendRequestButton.setPreferredSize(new Dimension(frame.getWidth()/5,40));
        topPanel.add(friendNameField);
        topPanel.add(sendRequestButton);

        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
    }
    private void initializeUI() {
        createTopPanel();
        // Créer les composants
        friendsLabel = new JLabel("Amis : ");
        friendsPanel = new JPanel();

        // Ajouter un gestionnaire de disposition au panneau principal
        //frame.setLayout(new BorderLayout());



        // Ajouter les composants au panneau principal
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(friendsLabel, BorderLayout.CENTER);
        frame.add(friendsPanel, BorderLayout.SOUTH);

        // Mettre à jour l'interface utilisateur
        updateUI();
    }
}
