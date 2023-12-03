package commands;

import db.DatabaseDiscussion;
import db.DatabaseUsers;
import reader.Reader;
import user.User;

@CommandInfo(name = "removefriend", optionnal = false)
public class RemoveFriendCommand implements Command{
    @Override
    public void execute(Reader reader, DatabaseUsers users_db, DatabaseDiscussion discussions_db, User currentuser) {
        reader.readinput("Write the username you want to remove from your contact list :");
        currentuser.remove_friend(reader.getinput(), users_db);
    }
}