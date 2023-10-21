import java.util.ArrayList;
public class DatabaseUsers{
    private int id_user; 
    private ArrayList<User> users;
    private static DatabaseUsers singleton=null; // singleton pattern
    // simulate users (constructer)
    private DatabaseUsers(String[] users_emails){
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
    public User get_user(String search,String email){
        for (User user:users){
            if (user.get_email().equals(email) && search.equals("email")){
                return user;
            }
            if (user.get_username().equals(email) && search.equals("username")){
                return user;
            }
            if (String.valueOf(user.get_userid()).equals(email) && search.equals("id")){
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
        if (singleton == null) 
            singleton = new DatabaseUsers(users_emails); 
  
        return singleton;
    } 
}