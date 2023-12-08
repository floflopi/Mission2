package commands;

import java.util.Date;

import db.DatabaseDiscussion;
import db.DatabaseUsers;
import message.Message;
import reader.Reader;
import user.User;
import discussion.*;
@CommandInfo(name = "sendmessage", optionnal = false)
public class SendMessageCommand implements Command{
    @Override
    public void execute(String message, DatabaseUsers users_db,Discussion current_discussion, DatabaseDiscussion discussions_db, User currentuser) {

        currentuser.send_message(message,users_db,current_discussion,discussions_db);
    }
    @Override
    public void execute(String input, DatabaseUsers users_db, DatabaseDiscussion discussions_db, User currentuser) {
    }
}