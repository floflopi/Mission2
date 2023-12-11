package testui;

import javax.swing.JButton;

import actions.Actions;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import db.DatabaseDiscussion;
import db.DatabaseUsers;
import ui.UserMainUI;
import user.User;

public class TestUserMainUI extends UserMainUI {
    
    public TestUserMainUI(String frameName, User current_user, DatabaseDiscussion disc_db, DatabaseUsers users_db, Actions actions) {
        super(frameName, current_user, disc_db, users_db, actions);
        
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
    
    public void click_simulation(Integer index) {
        if (index == 0) {
            micro = !micro;
            //imgLabel2.setVisible(!micro);
            actions.activate_features(micro, "micro");
        } else if (index == 1) {
            camera = !camera;
            //imgLabel2.setVisible(!camera);
            actions.activate_features(camera, "camera");
        } else if (index == 2) {
            safemode = !safemode;
            //imgLabel2.setVisible(!safemode);
            for (String feature : new String[]{"block", "exclude", "report"}) {
                actions.activate_features(!safemode, feature);
            }
        } else {
            adulte_mode = !adulte_mode;
            //imgLabel2.setVisible(!adulte_mode);
            for (String feature : new String[]{"sendimage", "sendvocaux", "sendgif", "sendautresfichiers", "addfriend", "acceptfriend", "sendvideo"}) {
                actions.activate_features(adulte_mode, feature);
            }
        }
    }


    public void closeWindow() {
        this.frame = super.getFrame();
        frame.dispose();
    }

}