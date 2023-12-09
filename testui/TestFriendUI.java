package testui;

import javax.swing.JFrame;

import db.DatabaseDiscussion;
import db.DatabaseUsers;
import ui.FriendUI;
import ui.LoginInterface;
import user.User;

public class TestFriendUI extends FriendUI {

    private JFrame frame;

    public TestFriendUI(DatabaseUsers users_db,DatabaseDiscussion disc_db,User current_user) {
        super(users_db, disc_db, current_user);
    }

    public void closeWindow() {
        this.frame = super.getFrame();
        frame.dispose();
    }

}