package commands;

import db.DatabaseDiscussion;
import db.DatabaseUsers;
import discussion.Discussion;
import reader.Reader;
import user.User;

// Design Pattern Command
public interface Command {
    void execute(Reader reader, DatabaseUsers users_db, DatabaseDiscussion discussions_db, User currentuser); 
    void execute(String input, DatabaseUsers users_db, DatabaseDiscussion discussions_db, User currentuser);
    void execute(String message, DatabaseUsers users_db,Discussion current_discussion, DatabaseDiscussion discussions_db, User currentuser);
}
