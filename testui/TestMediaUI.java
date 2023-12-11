package testui;

import actions.Actions;
import db.DatabaseDiscussion;
import db.DatabaseUsers;
import discussion.Discussion;
import ui.DiscussionPanel;
import ui.MediaUI;
import ui.UserMainUI;
import user.User;

public class TestMediaUI extends MediaUI {
    
    public TestMediaUI(DatabaseUsers users_db,DatabaseDiscussion disc_db,User current_user,Discussion current_disc,DiscussionPanel disc_panel,Actions actions) {
        super(users_db, disc_db, current_user, current_disc, disc_panel, actions);
    }

    public void closeWindow() {
        this.frame = super.getFrame();
        frame.dispose();
    }

}