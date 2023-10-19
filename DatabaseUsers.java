import java.util.ArrayList;
public class DatabaseUsers{
    private int id_user; 
    private ArrayList<User> users;
    // simulate users
    public DatabaseUsers(String[] users_emails){
        users = new ArrayList<User>();
        id_user = 0;
        for (String email:users_emails){
            users.add(new User(email,email.split("@")[0],id_user));
            id_user++;
        }
    }
    // if email -> check email
    // if not email-> check username
    // get_user == null signifie que user n est pas dans la db
    public User get_user(boolean isemail,String email){
        for (User user:users){
            if (user.get_email().equals(email) && isemail){
                return user;
            }
            if (user.get_username().equals(email) && !isemail){
                return user;
            }
        }
        return null;
    }
    public ArrayList<User> get_all_users(){
        return users;
    }
}