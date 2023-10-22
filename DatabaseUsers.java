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
    public boolean IsSameuser(String user1,String user2,String messageError){
        boolean issame = user1 == user2;
        if (issame){
            System.out.println(messageError);
        }
        return issame;
    }
    public User IsUserinDb(String username,String messageError){
        User user  = get_user("username",username);
        if (user == null){
            System.out.println(messageError);
            return null;
        }
        return user;
    }
    // search_type = "email" or "username" or "id"
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
        if (singleton == null) 
            singleton = new DatabaseUsers(users_emails); 
  
        return singleton;
    } 
}