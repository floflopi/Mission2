package commands;

import java.util.ArrayList;
import java.util.Arrays;

import db.DatabaseDiscussion;
import db.DatabaseUsers;
import reader.Reader;
import user.User;

@CommandInfo(name = "exclude", optionnal = true)
public class ExcludeCommand implements Command{
    @Override
    public void execute(Reader reader, DatabaseUsers users_db, DatabaseDiscussion discussions_db, User currentuser) {
        reader.readinput("Write the username of the people you share a discussion with (each seperated by a ',') :");
        String discussion_membres = reader.getinput()+","+currentuser.get_username();
        reader.readinput("Write the username you want to exclude from the conversation :");
        discussions_db.exclude_member(reader.getinput(), new ArrayList<String>(Arrays.asList(discussion_membres.split(","))),
            users_db,currentuser);
    }
}