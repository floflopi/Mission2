package testui;

import db.DatabaseDiscussion;
import db.DatabaseUsers;
import ui.FriendUI;
import user.User;

import actions.Actions;

public class TestFriendUI extends FriendUI {

    public TestFriendUI(DatabaseUsers users_db,DatabaseDiscussion disc_db,User current_user, Actions actions) {
        super(users_db, disc_db, current_user, actions);
    }

    public void closeWindow() {
        this.frame = super.getFrame();
        frame.dispose();
    }

}