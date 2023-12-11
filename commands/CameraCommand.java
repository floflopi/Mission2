package commands;

import db.DatabaseDiscussion;
import db.DatabaseUsers;
import discussion.Discussion;
import reader.Reader;
import ui.WindowError;
import user.User;

@CommandInfo(name = "camera", optionnal = true)
public class CameraCommand implements Command{

    @Override
    public void execute(String frienduser, DatabaseUsers users_db, Discussion current_discussion,
            DatabaseDiscussion discussions_db, User currentuser) {
                //not implemented at the moment 
    }
}