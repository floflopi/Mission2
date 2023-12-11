package commands;

import db.DatabaseDiscussion;
import db.DatabaseUsers;
import discussion.Discussion;
import reader.Reader;
import ui.WindowError;
import user.User;

@CommandInfo(name = "acceptfriend", optionnal = false)
public class AcceptFriendCommand implements Command{

    @Override
    public void execute(String frienduser, DatabaseUsers users_db, Discussion current_discussion,
            DatabaseDiscussion discussions_db, User currentuser) {
        if (currentuser.get_friendrequest().isEmpty()){
            new WindowError("Error","Nobody sent you a friend request.",null);
        }
        else {
        currentuser.accept_friend_request(frienduser,users_db);
        }

    }
}