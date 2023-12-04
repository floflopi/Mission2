package db;
import user.User;
import java.util.ArrayList;
import ui.*;
public class DatabaseUsers{
    private int id_user; 
    private ArrayList<User> users;
    private static DatabaseUsers users_db_singleton=null; // singleton pattern
    // simulate users (constructer)
    private DatabaseUsers(String[] users_emails){
        users = new ArrayList<User>();
        id_user = 0;
        for (String email:users_emails){
            users.add(new User(email,email.split("@")[0],email.split("@")[0],id_user));
            id_user++;
        }
    }
    //écrit un message d'erreur si les deux utilisateurs ont le même username
    public boolean IsSameuser(String user1,String user2,String messageError){
        boolean issame = user1.equals(user2);
        if (issame){
            new WindowError("Error",messageError,null);
        }
        return issame;
    }
    public boolean good_password(String username,String password) {
        User user  = get_user("username",username);
        if (!user.get_password().equals(password)){
            new WindowError("Error","Wrong password ! please retry.",null);
            return false;
        }
        return true;
    }
    // retourne un object User si son username est dans la DatabaseUsers, sinon retourne null
    public User IsUserinDb(String username,String messageError){
        User user  = get_user("username",username);
        if (user == null){
            if (messageError == null){
                new WindowError("Error",username + " is not in the UsersDatabase.",null);
            }
            else{
                new WindowError("Error",messageError,null);
            }
            
            return null;
        }
        return user;
    }
    // searchType = "email" or "username" or "id"
    public User get_user(String searchType, String query) {
        for (User user : users) {
            if (searchType.equals("email") && user.get_email().equals(query)) {
                return user;
            }
            if (searchType.equals("username") && user.get_username().equals(query)) {
                return user;
            }
            if (searchType.equals("id") && String.valueOf(user.get_userid()).equals(query)) {
                return user;
            }
        }
        return null;
    }
    public ArrayList<User> get_all_users(){
        return users;
    }
    //create singleton DatabaseUsers
    public static synchronized DatabaseUsers getInstance(String[] users_emails) 
    { 
        if (users_db_singleton == null) 
            users_db_singleton = new DatabaseUsers(users_emails); 
  
        return users_db_singleton;
    } 
}