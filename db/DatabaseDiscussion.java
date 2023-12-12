package db;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import discussion.Discussion;
import discussion.DiscussionGroupe;
import message.Message;
import ui.WindowError;
import user.User;
import ui.NewDiscussionUI;
import ui.UserMainUI;
public class DatabaseDiscussion {
    private ArrayList<Discussion> users_discussions;

    private static DatabaseDiscussion db_disc_singleton=null; // singleton pattern

    private DatabaseDiscussion(){
        users_discussions = new ArrayList<Discussion>();
    }
    //create singleton DatabaseDiscussion
    public static synchronized DatabaseDiscussion getInstance() 
    { 
        if (db_disc_singleton == null) 
            db_disc_singleton = new DatabaseDiscussion();
    
        return db_disc_singleton;
    } 
    public void create_discussion(UserMainUI userMainUI,String framename,int sizex,int sizey,User current_user,DatabaseUsers users_db){
        if (current_user.get_liste_contact().isEmpty()){
            new WindowError("Error", "you need friends if you want to create a discussion",null);
            return;
        }
        NewDiscussionUI disc_ui = new NewDiscussionUI(userMainUI,"New Discussion",650,350,current_user,this,users_db);
        
    }
    //return list of all username with current user inside
    public ArrayList<String> find_all_disc(User current_user,DatabaseUsers users_db){
        ArrayList<String> discs= new ArrayList<>();
        for (Discussion discussion:users_discussions){
            if (discussion.getmembers_id().contains(current_user.get_userid())){
                String members="";
                for (Integer id: discussion.getmembers_id()){
                    if (id != current_user.get_userid()){
                        members = members + users_db.get_user("id",String.valueOf(id)).get_username() + ",";
                    }
                }
                discs.add(members.substring(0, members.length() - 1)); // remove last ","

            }
        }
        return discs;
    }
    //returns true if a discussion can be create
    public boolean verify_disc_creation(String messageinitial,String list_users,User current_user,DatabaseUsers users_db){
        if (list_users.equals(messageinitial)){
            new WindowError("Error","Please add at least one person to the discussion",null);
            return false;
        }
        String[] users_array = (list_users + ',' + current_user.get_username()).split(",");
        ArrayList<Integer> users_id = get_members_id(users_db, users_array);
        if (users_id == null){
            return false;
        }
        // si il y a 2 ou get_userid() dans users_id c que user a essayé de se message lui meme
        if (users_id.stream().filter(x->x.equals(current_user.get_userid())).count() > 1){
            new WindowError("Error","you can't send message to yourself", null);
            return false;
        }
        // try to find if a current_discussion exist
        if (get_discussion(users_id)!= null){
            new WindowError("Error","The discussion already exist", null);
            return false;
        }
        Discussion current_discussion;
        for (int user_id:users_id){
            if (user_id == current_user.get_userid()){
                continue;
            }
            if (!current_user.get_liste_contact().contains(user_id)){
                new WindowError("Error", users_db.get_user("id", String.valueOf(user_id)).get_username() 
                + " is not your friend so you can't message him",null);
                return false;
            }
        }
        if (users_id.size() > 2){
            current_discussion = new DiscussionGroupe(users_id,current_user.get_userid());
        }
        else{
            current_discussion = new Discussion(users_id,true);
        }
        users_discussions.add(current_discussion);
        return true;
    }
    public String find_message(String message,DatabaseUsers users_db,String[] members_username){
        ArrayList<Integer> members_id = get_members_id(users_db, members_username);
        Discussion discussion = get_discussion(members_id);
        if (discussion != null){
            for (Message current_message:discussion.getmessages()){
                if (current_message.get_contenu().equals(message)){
                    return current_message.see_details(users_db);
                }
            }
        }
        return "No message found !";
    }
    public void exclude_member(String exclude_member,ArrayList<String> members_username,DatabaseUsers users_db,User current_user){

        if (exclude_member.equals(current_user.get_username())){
            System.out.println("you can't exclude yourself from a conversation.");
        }
        int current_user_id = current_user.get_userid();
        if (!members_username.contains(exclude_member)){
            System.out.println(exclude_member + " is not part of the group");
            return;
        }
        if (members_username.size() == 2){
            System.out.println("you can't exclude someone from a private conversation but you can block this person");
            return;
        }
        ArrayList<Integer> members_id = new ArrayList<Integer>();
        for (String member:members_username){
            User member_user = users_db.IsUserinDb(member,null);
            members_id.add(member_user.get_userid());
        }
        Collections.sort(members_id);
        Discussion discussion = get_discussion(members_id);
        if (discussion instanceof DiscussionGroupe){ 
            DiscussionGroupe discussionGroupe = (DiscussionGroupe) discussion;
            if (discussionGroupe.get_admin_id() == current_user_id){
                discussion.remove_member(users_db.get_user("username", exclude_member).get_userid());
                System.out.println(exclude_member + " was successfully excluded");
            }
            else{
                System.out.println("you are not the admin of the discussion and thus cannot exclude someone");
            }
            if (discussion.getmembers_id().size() == 2){
                users_discussions.remove(discussionGroupe);
            }
            return;
        }
        System.out.println("there is no discussion available with all the members you describe");
    }
    public boolean date_equal(Date date1,Date date2){
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        // Attribution des dates aux instances de Calendar
        cal1.setTime(date1);
        cal2.setTime(date2);

        // Comparaison des années, des mois et des jours
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                           cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                           cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    // conversion username->id
    public ArrayList<Integer> get_members_id(DatabaseUsers users_db,String[] members_username){
        ArrayList<Integer> members_id = new ArrayList<Integer>();
        for (String member:members_username){
            User member_user = users_db.IsUserinDb(member,null);
            if (member_user == null){
                return null;//error throwed
            }
            members_id.add(member_user.get_userid());
        }
        Collections.sort(members_id); // sort the array so that we can compare with the db_discussions
        return members_id;
    }
    public String find_messages_date(String dateString,DatabaseUsers users_db,String[] members_username){
        ArrayList<Integer> members_id = get_members_id(users_db, members_username);
        String all_messages="";

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false); // Pour rendre la validation stricte
        Date date=null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Invalid date");
        }
        Discussion discussion = get_discussion(members_id);
        for (Message current_message:discussion.getmessages()){
            if (date_equal(date, current_message.get_date())){
                all_messages += current_message.see_details(users_db);
                all_messages += "\n";
            }
        }
        if (all_messages == ""){
            return "No message found !";
        }
        return all_messages;
    }
    // retourne la discussion qui concerne tout les users_id
    // discussion == null signifie qu aucune discussion existe pour ces utilisateurs
    public Discussion get_discussion(ArrayList<Integer> users_id){
        for (Discussion discussion:users_discussions){
            if (discussion.getmembers_id().equals(users_id)){
                return discussion;
            }
        }
        return null;
    }
    public Discussion get_discussion(DatabaseUsers users_db,String[] usernames){
        ArrayList<Integer> users_id = get_members_id(users_db,usernames);
        for (Discussion discussion:users_discussions){
            if (discussion.getmembers_id().equals(users_id)){
                return discussion;
            }
        }
        return null;
    }

    public void resetDatabase() {
        users_discussions.clear();
    }

}
