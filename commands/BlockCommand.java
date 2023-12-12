package commands;

import db.DatabaseDiscussion;
import db.DatabaseUsers;
import discussion.Discussion;
import user.User;

@CommandInfo(name = "block", optionnal = true)
public class BlockCommand implements Command{
    @Override
    public void execute(String message, DatabaseUsers users_db, Discussion current_discussion,
            DatabaseDiscussion discussions_db, User currentuser) {
        // TODO Auto-generated method stub
        
    }
}
