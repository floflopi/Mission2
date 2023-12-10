package testui;

import javax.swing.JButton;

import java.awt.Component;
import java.awt.Container;

import db.DatabaseDiscussion;
import db.DatabaseUsers;
import ui.UserMainUI;
import user.User;

public class TestUserMainUI extends UserMainUI {
    
    public TestUserMainUI(String frameName, User current_user, DatabaseDiscussion disc_db, DatabaseUsers users_db) {
        super(frameName, current_user, disc_db, users_db);
        
    }

    public void setUserStatus(Integer status) {
         this.status =  status;
         userPanel.repaint(); 
    }

    public JButton findButtonByText(Container container, String buttonText) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().equals(buttonText)) {
                    return button;
                }
            }
        }

        return null;
    }

    public JButton get_currentdisc_btn(String buttonText) {
        JButton currentdisc_btn = findButtonByText(members_discPanel, buttonText);
        return currentdisc_btn;
    }

    public JButton get_newdiscussion_btn() {
        return newdiscussion_btn;
    }

    public void set_features_img(Integer index) {
        if (features_img[index].isEnabled()) {
            features_img[index].setEnabled(false);
        }
        else {
            features_img[index].setEnabled(true);
        }
    }

    public void closeWindow() {
        this.frame = super.getFrame();
        frame.dispose();
    }

}