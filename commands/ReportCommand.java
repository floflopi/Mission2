package commands;

import db.DatabaseDiscussion;
import db.DatabaseUsers;
import discussion.Discussion;
import user.User;

@CommandInfo(name = "report", optionnal = true)
public class ReportCommand implements Command{
    @Override
    public void execute(String message, DatabaseUsers users_db, Discussion current_discussion,
            DatabaseDiscussion discussions_db, User currentuser) {
        // TODO Auto-generated method stub
        
    }
}
