package commands;

import db.DatabaseDiscussion;
import db.DatabaseUsers;
import reader.Reader;
import user.User;

@CommandInfo(name = "acceptfriend", optionnal = false)
public class AcceptFriendCommand implements Command{
    @Override
    public void execute(Reader reader, DatabaseUsers users_db, DatabaseDiscussion discussions_db, User currentuser) {
        if (currentuser.get_friendrequest().isEmpty()){
            System.out.println("No one sent you a friend request.");
        }
        else {
        System.out.println("Friends request : "+ currentuser.get_string_friendrequest(users_db));
        reader.readinput("Write the username you want to accept as your friend :");
        currentuser.accept_friend_request(reader.getinput(),users_db);
        }
    }
}