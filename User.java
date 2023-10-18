import java.util.ArrayList;
public class User {
    private String email;
    private String name;
    private int user_id; // id unique pour chaque user
    private ArrayList<Integer> liste_contact; // contient les user_id des autres
    public User(String email,String name,int user_id){
        this.email = email;
        this.name = name;
        this.user_id = user_id;
        this.liste_contact = new ArrayList<>();
    }
    public String get_email(){
        return email;
    }
    public String get_username(){
        return name;
    }
    public int get_userid(){
        return user_id;
    }
    // add friend if user is in database else print error
    public boolean add_friend(String user,DatabaseUsers db){
        if (!db.user_in_db(false,user)){
            System.out.println(user + " is not in the userdatabase");
            return false;
        }
        User friend_user = db.get_user(false, user);
        int friend_id = friend_user.get_userid();
        if (liste_contact.contains(friend_id)){
            System.out.println(friend_user.get_username() + " is already in your contact list");
            return false;
        }
        else{
            liste_contact.add(friend_id);
            System.out.println(friend_user.get_username() + " was added to your contact list !");
        }
        return true;
    }
    public void remove_friend(String user,DatabaseUsers db){
        if (!db.user_in_db(false,user)){
            System.out.println(user + " is not in the Userdatabase");
            return;
        }
        User friend_user = db.get_user(false, user);
        int friend_id = friend_user.get_userid();
        if (liste_contact.contains(friend_id)){
            liste_contact.remove(Integer.valueOf(friend_id));
            System.out.println(friend_user.get_username() + " was deleted from your contact list !");
        }
        else{
            System.out.println(friend_user.get_username() + " is not in contact list");
        }
        return;

    }
}
