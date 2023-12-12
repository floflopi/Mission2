package commands;

import db.DatabaseDiscussion;
import db.DatabaseUsers;
import discussion.Discussion;
import user.User;

// Design Pattern Command
public interface Command {
    void execute(String input, DatabaseUsers users_db,Discussion current_discussion, DatabaseDiscussion discussions_db, User currentuser);
}
