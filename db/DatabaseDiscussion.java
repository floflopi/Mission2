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
    public void create_discussion(User current_user){
        if (current_user.get_liste_contact().isEmpty()){
            new WindowError("Error", "you need friends if you want to create a discussion");
            return;
        }
        // pop new AddDiscussion Window
        
    }
    public void add_discussions(Discussion discussion){
        users_discussions.add(discussion);
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
            User member_user = users_db.IsUserinDb(member);
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

    public ArrayList<Integer> get_members_id(DatabaseUsers users_db,String[] members_username){
        ArrayList<Integer> members_id = new ArrayList<Integer>();
        for (String member:members_username){
            User member_user = users_db.IsUserinDb(member);
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

}
