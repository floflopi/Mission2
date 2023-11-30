package commands;

import db.DatabaseDiscussion;
import db.DatabaseUsers;
import reader.Reader;
import user.User;

public interface Command {
    void execute(Reader reader, DatabaseUsers users_db, DatabaseDiscussion discussions_db, User currentuser);
}
