package commands;

import db.DatabaseDiscussion;
import db.DatabaseUsers;
import discussion.Discussion;
import user.User;

@CommandInfo(name = "addfriend", optionnal = true)
public class AddFriendCommand implements Command{
    @Override
    public void execute(String message, DatabaseUsers users_db, Discussion current_discussion,
            DatabaseDiscussion discussions_db, User currentuser) {
        currentuser.send_friendrequest(message, users_db);
    }
}
