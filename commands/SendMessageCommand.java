package commands;

import java.util.Date;

import db.DatabaseDiscussion;
import db.DatabaseUsers;
import message.Fichier;
import message.Message;
import reader.Reader;
import user.User;
import discussion.*;
@CommandInfo(name = "sendmessage", optionnal = false)
public class SendMessageCommand implements Command{
    @Override
    public void execute(String message, DatabaseUsers users_db,Discussion current_discussion, DatabaseDiscussion discussions_db, User current_user) {
        Message current_message = new Message(message, new Date(), current_user.get_userid());
        current_discussion.add_message(current_message);
    }
}