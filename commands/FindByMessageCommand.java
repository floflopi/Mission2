package commands;

import db.DatabaseDiscussion;
import db.DatabaseUsers;
import discussion.Discussion;
import user.User;

@CommandInfo(name = "findbymessage", optionnal = true)
public class FindByMessageCommand implements Command{
    /* 
    @Override
    public void execute(Reader reader, DatabaseUsers users_db, DatabaseDiscussion discussions_db, User currentuser) {
        reader.readinput("Write the username of the people you share a discussion with (each seperated by a ',') :");
        String discussion_membres = reader.getinput()+","+currentuser.get_username();
        reader.readinput("Write the message you want to check:");
        System.out.println(discussions_db.find_message(reader.getinput(), users_db,discussion_membres.split(",")));
    }
    */

    @Override
    public void execute(String message, DatabaseUsers users_db, Discussion current_discussion,
            DatabaseDiscussion discussions_db, User currentuser) {
        // TODO Auto-generated method stub
        
    }
}
