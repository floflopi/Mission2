package testui;

import javax.swing.JSplitPane;

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

    public JSplitPane getSplitPane() {
        return splitPane;
    }

}