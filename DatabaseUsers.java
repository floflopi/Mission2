import java.util.ArrayList;
public class DatabaseUsers{
    public int id_user; 
    private ArrayList<User> users;
    // simulate users
    public DatabaseUsers(){
        users = new ArrayList<User>();
        id_user = 0;
        users.add(new User("flo@gmail.com","flo",id_user));
        id_user++;
        users.add(new User("louis@gmail.com","louis",id_user));
        id_user++;
    }
    // if email -> check email
    // if not email-> check username
    public boolean user_in_db(boolean isemail,String email){
        for (User user: users){
            if (user.get_email().equals(email) && isemail){
                return true;
            }
            if (user.get_username().equals(email) && !isemail){
                return true;
            }
        }
        return false;
    }
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
    public void add_user_db(){

    }
}