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
    public ArrayList<Integer> get_liste_contact(){
        return liste_contact;
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
        if (blacklist.contains(Integer.valueOf(friend_id))){
            blacklist.remove(Integer.valueOf(friend_id));
        }
        if (friend_user.get_blacklist().contains(get_userid())){
            System.out.println(friend_user.get_username() + " has blocked you ! you cannot send a friendrequest.");
            return false;
        }
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
            friend_user.get_liste_contact().remove(Integer.valueOf(user_id));
            System.out.println(friend_user.get_username() + " was deleted from your contact list !");
        }
        else{
            System.out.println(friend_user.get_username() + " is not in contact list");
        }
        return;
    }
    public void accept_friend_request(String friend_user,DatabaseUsers users_db){
        User user =  users_db.IsUserinDb(friend_user,friend_user +" is not in the userdatabase");
        if (user == null){
            return;
        }
        liste_contact.add(user.get_userid());
        user.get_liste_contact().add(Integer.valueOf(user_id));
        user.get_friendrequest().remove(Integer.valueOf(user_id));
        System.out.println(friend_user + " is now your friend !");
    }

    public void send_message(String users,Message current_message,DatabaseUsers users_db,DatabaseDiscussion discussion_db){
        users= users + "," + get_username();
        String[] users_array = users.split(",");
        ArrayList<Integer> users_id = discussion_db.get_members_id(users_db, users_array);
        if (users_id == null)
            return;
        // si il y a 2 ou get_userid() dans users_id c que user a essayé de se message lui meme
        if (users_id.stream().filter(x->x.equals(get_userid())).count() > 1){
            System.out.println("you can't send message to yourself");
            return;
        }
        // try to find if a current_discussion exist
        Discussion current_discussion = discussion_db.get_discussion(users_id);
        if (current_discussion == null){
            for (int user_id:users_id){
                if (user_id == get_userid()){
                    continue;
                }
                if (!liste_contact.contains(user_id)){
                    System.out.println(users_db.get_user("id", String.valueOf(user_id)).get_username() 
                    + " is not your friend so you can't message him");
                    return;
                }
            }
            if (users_id.size() > 2){
                current_discussion = new DiscussionGroupe(users_id,get_userid());
            }
            else{
                current_discussion = new Discussion(users_id,true);
            }
            discussion_db.add_discussions(current_discussion);
        }
        // si discussion est privé alors 
        if (current_discussion.isprivate() && !liste_contact.contains(users_db.get_user("username",users_array[0]).get_userid())){
            System.out.println(users_array[0] + " is not your friend so you can't message him");
            return;
        }
        current_discussion.add_message(current_message);
        current_message.send();
    }
}
