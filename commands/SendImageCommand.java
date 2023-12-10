package commands;

import java.util.Date;

import db.DatabaseDiscussion;
import db.DatabaseUsers;
import discussion.Discussion;
import message.Fichier;
import message.Message;
import reader.Reader;
import user.User;

@CommandInfo(name = "sendimage", optionnal = true)
public class SendImageCommand implements Command{
    @Override
    public void execute(String message, DatabaseUsers users_db,Discussion current_discussion, DatabaseDiscussion discussions_db, User current_user) {
        Message current_message = new Fichier(message, new Date(), current_user.get_userid());
        current_discussion.add_message(current_message);
    }
    @Override
    public void execute(String input, DatabaseUsers users_db, DatabaseDiscussion discussions_db, User currentuser) {
    }
    @Override
    public void execute(Reader reader, DatabaseUsers users_db, DatabaseDiscussion discussions_db, User currentuser) {
        // TODO Auto-generated method stub
        
    }
}
