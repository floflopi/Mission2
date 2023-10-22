import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
public class User {

    private String email;
    private String name;
    private int user_id; // id unique pour chaque user
    private ArrayList<Integer> liste_contact; // contient les user_id des autres
    private ArrayList<Integer> blacklist; // blocked users
    private ArrayList<Integer> friend_request; // request from user waiting to be accepted
    public User(String email,String name,int user_id){
        this.email = email;
        this.name = name;
        this.user_id = user_id;
        this.liste_contact = new ArrayList<>();
        this.friend_request = new ArrayList<>();
        this.blacklist = new ArrayList<>();
    }
    // called when an user send a friend request
    public void receive_friend_request(int from_user_id,DatabaseUsers users_db){
        friend_request.add(from_user_id);
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
    public ArrayList<Integer> get_friendrequest(){
        return friend_request;
    }
    public ArrayList<Integer> get_blacklist(){
        return blacklist;
    }
    public String get_string_friendrequest(DatabaseUsers users_db){
        String all_request = "";
        for (int friend_id : friend_request){
            all_request += users_db.get_user("id",String.valueOf(friend_id)).get_username();
            all_request+=",";
        }
        return all_request.substring(0, all_request.length() - 1); // delete the last ","
    }
    // il n est pas nécessaire qu un user soit un ami pour etre bloqué 
    public void block_user(String username_blocked,DatabaseUsers users_db){
        if (users_db.IsSameuser(username_blocked, get_username(),"you can't block yourself" )){
            return;
        }
        User user_blocked = users_db.IsUserinDb(username_blocked, username_blocked + " is not in the userdatabase");
        if (user_blocked == null){
            return;
        }
        int user_blocked_id = user_blocked.get_userid();
        //remove friend and add to the blacklist
        this.blacklist.add(user_blocked_id);
        if (this.liste_contact.contains(Integer.valueOf(user_blocked_id))){
            this.liste_contact.remove(Integer.valueOf(user_blocked_id));
        }
        System.out.println(user_blocked.get_username()+" is now blocked !");
    }

    public boolean send_friendrequest(String user,DatabaseUsers users_db){
        if (users_db.IsSameuser(user, get_username(),"you can't add yourself as a friend" )){
            return false;
        }
        User friend_user = users_db.IsUserinDb(user, user + " is not in the userdatabase");
        if (friend_user == null){
            return false;
        }
        int friend_id = friend_user.get_userid();
        if (liste_contact.contains(friend_id)){
            System.out.println(friend_user.get_username() + " is already in your contact list");
            return false;
        }
        if (friend_user.get_blacklist().contains(get_userid())){
            System.out.println(friend_user.get_username() + " has blocked you ! you cannot send a friendrequest.");
            return false;
        }
        liste_contact.add(friend_id);
        friend_user.receive_friend_request(get_userid(),users_db);
        System.out.println("friend request to "+friend_user.get_username() + " sent !");
        return true;
    }
    public void remove_friend(String user,DatabaseUsers users_db){
        if (users_db.IsSameuser(user, get_username(),"you can't remove yourself as a friend" )){
            return;
        }
        User friend_user = users_db.IsUserinDb(user, user + " is not in the userdatabase");
        if (friend_user == null){
            return;
        }
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
    public void accept_friend_request(String friend_user,DatabaseUsers users_db){
        liste_contact.add(users_db.get_user("username", friend_user).get_userid());
        System.out.println(friend_user + " is now your friend !");
    }

    public void send_message(String users,Message current_message,DatabaseUsers users_db,DatabaseDiscussion discussion_db){
        String[] users_array = users.split(",");
        ArrayList<Integer> users_id = new ArrayList<>();
        //check si on peut message ces utilisateurs
        for (String username : users_array){
            User friend = users_db.IsUserinDb(username, username + " is not in the userdatabase");
            if (friend == null || users_db.IsSameuser(friend.get_username(), get_username(), "you can't send message yourself")){
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
        // cherche si une discussion existe deja entre les utiliseurs
        Discussion current_discussion = discussion_db.get_discussion(users_id);        
        if(current_discussion == null && users_id.size() > 1){
            current_discussion = new DiscussionGroupe(users_id,get_userid());
            discussion_db.add_discussions(current_discussion);
        } 
        if(current_discussion == null && users_id.size() == 1){
            current_discussion = new Discussion(users_id);
            discussion_db.add_discussions(current_discussion);
        }
        current_discussion.add_message(current_message);
        current_message.send();
    }
}
