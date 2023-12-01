package commands;

import java.util.Date;

import db.DatabaseDiscussion;
import db.DatabaseUsers;
import message.Fichier;
import reader.Reader;
import user.User;

public class SendImageCommand implements Command{
    @Override
    public void execute(Reader reader, DatabaseUsers users_db, DatabaseDiscussion discussions_db, User currentuser) {
        Fichier file = new Fichier("image",new Date(),currentuser.get_userid());
        reader.readinput("Write the username of the people you want to send this message (each seperated by a ',') :");
        currentuser.send_message(reader.getinput(),file,users_db,discussions_db);
    }
}
