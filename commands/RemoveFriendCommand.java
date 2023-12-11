package commands;

import java.util.Date;

import db.DatabaseDiscussion;
import db.DatabaseUsers;
import discussion.Discussion;
import message.Fichier;
import message.Message;
import reader.Reader;
import user.User;

@CommandInfo(name = "removefriend", optionnal = true)
public class RemoveFriendCommand implements Command{
    @Override
    public void execute(String user_deleted, DatabaseUsers users_db,Discussion current_discussion, DatabaseDiscussion discussions_db, User current_user) {
        current_user.remove_friend(user_deleted, users_db);
    }
}