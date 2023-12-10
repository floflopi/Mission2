package testui;

import db.DatabaseDiscussion;
import db.DatabaseUsers;
import ui.FriendUI;
import user.User;
import java.util.List;

public class TestFriendUI extends FriendUI {

    public TestFriendUI(DatabaseUsers users_db,DatabaseDiscussion disc_db,User current_user, List<Boolean> contexts) {
        super(users_db, disc_db, current_user, contexts);
    }

    public void closeWindow() {
        this.frame = super.getFrame();
        frame.dispose();
    }

}