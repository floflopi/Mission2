package commands;

import java.util.Date;

import db.DatabaseDiscussion;
import db.DatabaseUsers;
import message.Message;
import reader.Reader;
import user.User;

@CommandInfo(name = "sendmessage", optionnal = false)
public class SendMessageCommand implements Command{
    @Override
    public void execute(Reader reader, DatabaseUsers users_db, DatabaseDiscussion discussions_db, User currentuser) {
        reader.readinput("Write the message you want to send :");
        String message = reader.getinput();
        reader.readinput("Write the username of the people you want to send this message (each seperated by a ',') :");
        currentuser.send_message(reader.getinput(),new Message(message,new Date(),currentuser.get_userid()),users_db,discussions_db);
    }
}