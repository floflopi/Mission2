package commands;

import db.DatabaseDiscussion;
import db.DatabaseUsers;
import reader.Reader;
import user.User;

@CommandInfo(name = "findbydate", optionnal = true)
public class FindByDateCommand implements Command{
    @Override
    public void execute(Reader reader, DatabaseUsers users_db, DatabaseDiscussion discussions_db, User currentuser) {
        reader.readinput("Write the username of the people you share a discussion with (each seperated by a ',') :");
        String discussion_membres = reader.getinput()+","+currentuser.get_username();
        reader.readinput("Write the date you want to check (with format dd/MM/yyyy)");
        System.out.println(discussions_db.find_messages_date(reader.getinput(), users_db,discussion_membres.split(",")));
    }
}