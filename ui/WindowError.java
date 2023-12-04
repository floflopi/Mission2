package ui;
import java.util.Scanner;

import javax.swing.*;

import ui.LoginInterface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowError extends Window{

    private JFrame frame;
    private String MessageError;
    private String error_img="images/error_cross.png";

    public WindowError(String framename,String MessageError,String newimage) {
        super(framename,700,400);
        if (newimage != null){
            this.error_img=newimage;
        }
        this.frame = super.getFrame();
        this.MessageError = MessageError;
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // only close the error not the app
        showErrorMessage();

    }

    private void showErrorMessage() {
        JPanel errorPanel = new JPanel(new GridBagLayout());
        // Image de l'erreur (croix rouge)
        ImageIcon errorIcon = new ImageIcon(error_img);
        Image image = errorIcon.getImage().getScaledInstance(errorIcon.getIconWidth() / 5, -1, Image.SCALE_DEFAULT);
        ImageIcon scaledErrorIcon = new ImageIcon(image);
        JLabel errorLabel = new JLabel(scaledErrorIcon);
        // Message d'erreur avec une police plus grande
        JTextArea messageArea = new JTextArea(MessageError);
        messageArea.setBackground(errorPanel.getBackground());
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setEditable(false);

        Font currentFont = messageArea.getFont();
        Font newFont = new Font("SansSerif", Font.PLAIN, currentFont.getSize() + 14); // Ajuster la taille de la police
        messageArea.setFont(newFont);

        // Ajouter un espace à gauche du texte
        int leftPadding = 10; // Ajustez la valeur selon vos besoins
        messageArea.setBorder(BorderFactory.createEmptyBorder(0, leftPadding, 0, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST; // Aligner l'image à gauche
        errorPanel.add(errorLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0; // Faire en sorte que le texte occupe tout l'espace horizontal disponible
        gbc.fill = GridBagConstraints.HORIZONTAL;
        errorPanel.add(messageArea, gbc);
        // Ajouter le bouton "OK" en bas à droite
        JButton okButton = new JButton("OK");
        Dimension buttonSize = new Dimension(180, 60); // Ajustez la largeur et la hauteur selon vos besoins
        okButton.setPreferredSize(buttonSize);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Fermer la fenêtre
            }
        });
        // Positionner le bouton "OK" en bas à droite
        gbc.gridx = 1;
        gbc.gridy = 1; // Placez le bouton dans la deuxième ligne
        gbc.anchor = GridBagConstraints.SOUTHEAST; // Alignez en bas à droite
        gbc.insets = new Insets(0, 0, 0, 20); // Réinitialisez les marges
        gbc.fill = GridBagConstraints.NONE; // Ne pas étirer le bouton
        errorPanel.add(okButton, gbc);

        frame.getContentPane().add(errorPanel, BorderLayout.CENTER);
        updateUI();
    }
}