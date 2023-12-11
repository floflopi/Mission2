package user;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import db.DatabaseUsers;
import ui.WindowError;
import message.*;
import discussion.*;
import db.*;
public class User {

    private String email;
    private String name;
    private String password;
    private int user_id; // id unique pour chaque user
    private ArrayList<Integer> liste_contact; // contient les user_id des autres
    private ArrayList<Integer> blacklist; // blocked users
    private ArrayList<Integer> friend_request; // request from user waiting to be accepted
    private String noerror = "images/noerror.png";
    public User(String email,String name,String password,int user_id){
        this.email = email;
        this.name = name;
        this.password = password;
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
    public String get_password(){
        return password;
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
        User user_blocked = users_db.IsUserinDb(username_blocked,null);
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
        User friend_user = users_db.IsUserinDb(user,null);
        if (friend_user == null){
            return false;
        }
        int friend_id = friend_user.get_userid();
        if (liste_contact.contains(friend_id)){
            new WindowError("Error",friend_user.get_username() + " is already in your contact list",null);
            return false;
        }
        if (friend_user.get_friendrequest().contains(Integer.valueOf(get_userid()))){
            new WindowError("Error","you have already send a friend request to " + friend_user.get_username() + " !",null);
            return false;
        }
        // remove user from blacklist if user want to be friend again
        if (blacklist.contains(Integer.valueOf(friend_id))){
            blacklist.remove(Integer.valueOf(friend_id));
        }
        if (friend_user.get_blacklist().contains(get_userid())){
            new WindowError("Error",friend_user.get_username() + " has blocked you ! you cannot send a friendrequest.",null);
            return false;
        }
        friend_user.receive_friend_request(get_userid(),users_db);
        // create window error sauf qu on remplace la croix par un ok ! 
        new WindowError("Request Succeed","friend request to "+friend_user.get_username() + " sent !" , noerror);
        return true;
    }
    public void remove_friend(String user,DatabaseUsers users_db){
        if (users_db.IsSameuser(user, get_username(),"you can't remove yourself as a friend")){
            return;
        }
        User friend_user = users_db.IsUserinDb(user,null);
        if (friend_user == null){
            return;
        }
        int friend_id = friend_user.get_userid();
        if (liste_contact.contains(friend_id)){
            liste_contact.remove(Integer.valueOf(friend_id));
            friend_user.get_liste_contact().remove(Integer.valueOf(user_id));
            new WindowError("Request succeed",friend_user.get_username() + " was deleted from your contact list !", noerror);
        }
        else{
            new WindowError("Error",friend_user.get_username() + " is not in contact list", null);
        }
        return;
    }
    public void accept_friend_request(String friend_user,DatabaseUsers users_db){
        User user =  users_db.IsUserinDb(friend_user,null);
        if (user == null){
            return;
        }
        liste_contact.add(user.get_userid());
        user.get_liste_contact().add(Integer.valueOf(user_id));
        user.get_friendrequest().remove(Integer.valueOf(user_id));
    }
}
