package commands;

import db.DatabaseDiscussion;
import db.DatabaseUsers;
import reader.Reader;
import user.User;

@CommandInfo(name = "addfriend", optionnal = true)
public class AddFriendCommand implements Command{
    @Override
    public void execute(Reader reader, DatabaseUsers users_db, DatabaseDiscussion discussions_db, User currentuser) {
        return;
        //reader.readinput("Write the username you want to add to your contact list :");
        //currentuser.send_friendrequest(reader.getinput(), users_db);
    }
    @Override
    public void execute(String inputString, DatabaseUsers users_db, DatabaseDiscussion discussions_db, User currentuser) {
        currentuser.send_friendrequest(inputString, users_db);
    }
}
