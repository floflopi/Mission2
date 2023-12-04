package commands;

import db.DatabaseDiscussion;
import db.DatabaseUsers;
import reader.Reader;
import user.User;

// Design Pattern Command
public interface Command {
    void execute(Reader reader, DatabaseUsers users_db, DatabaseDiscussion discussions_db, User currentuser);
    void execute(String input, DatabaseUsers users_db, DatabaseDiscussion discussions_db, User currentuser);
}
