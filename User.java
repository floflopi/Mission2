import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

    public void send_message(String users,String message,DatabaseUsers users_db,DatabaseDiscussion discussion_db){
        String[] users_array = users.split(",");
        ArrayList<Integer> users_id = new ArrayList<>();
        //check si on peut message ces utilisateurs
        for (String username : users_array){
            User friend = users_db.get_user(false,username);
            if (friend == null){
                System.out.println(username + " is not present in the users database");
                return;
            }
            if (friend.get_username().equals(get_username())){
                System.out.println("you can't send message to yourself");
                return;
            }
            if (!liste_contact.contains(friend.get_userid())){
                System.out.println(friend.get_username() + " is not your friend so you can't message him");
                return;
            }
            users_id.add(friend.get_userid());
        }
        users_id.add(get_userid());
        Collections.sort(users_id); // we sort the array so that we can easily find discussion in discussions_id;
        Message current_message = new Message(message,new Date(),get_userid());
        // cherche si une discussion existe deja entre les utiliseurs
        Discussion current_discussion = discussion_db.get_discussion(users_id);        
        if(current_discussion == null){
            current_discussion = new Discussion(users_id);
            discussion_db.add_discussions(current_discussion);
        } 
        current_discussion.add_message(current_message);
        System.out.println("message sent !");
    }
    // add friend if user is in database else print error
    public boolean add_friend(String user,DatabaseUsers db){
        if (user == get_username()){
            System.out.println("you can't add yourself as a friend");
            return false;
        }
        if (db.get_user(false,user) == null){
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
        if (user == get_username()){
            System.out.println("you can't remove yourself as a friend");
            return;
        }
        if (db.get_user(false,user) == null){
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
