import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseDiscussion {
    private ArrayList<Discussion> users_discussions;

    private static DatabaseDiscussion singleton=null; // singleton pattern
    private DatabaseDiscussion(){
        users_discussions = new ArrayList<Discussion>();
    }
    public void add_discussions(Discussion discussion){
        users_discussions.add(discussion);
    }
    public ArrayList<Discussion> getusers_discussions(){
        return users_discussions;
    }
    public String find_message(String message,DatabaseUsers users_db,String[] members_username){
        ArrayList<Integer> members_id = new ArrayList<Integer>();
        for (String member:members_username){
            User member_user = users_db.get_user("username", member);
            if (member_user == null){
                return member+ "is not in the database user";
            }
            members_id.add(member_user.get_userid());
        }
        Collections.sort(members_id);
        for (Discussion discussion:users_discussions){
            if (discussion.getmembers_id().equals(members_id)){ // check if all members are in the discussion
                for (Message current_message:discussion.getmessages()){
                    if (current_message.get_contenu().equals(message)){
                        return current_message.see_details(users_db);
                    }
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
            System.out.println(exclude_member + "is not part of the group");
            return;
        }
        if (members_username.size() == 2){
            System.out.println("you can't exclude someone from a private conversation but you can block this person");
            return;
        }
        ArrayList<Integer> members_id = new ArrayList<Integer>();
        for (String member:members_username){
            User member_user = users_db.get_user("username", member);
            if (member_user == null){
                System.out.println(member_user + "is not in the database user");
            }
            members_id.add(member_user.get_userid());
        }
        Collections.sort(members_id);
        for (Discussion discussion:users_discussions){
            if (discussion.getmembers_id().equals(members_id) && discussion instanceof DiscussionGroupe){ 
                DiscussionGroupe discussionGroupe = (DiscussionGroupe) discussion;
                if (discussionGroupe.get_admin_id() == current_user_id){
                    discussion.remove_member(users_db.get_user("username", exclude_member).get_userid());
                    System.out.println(exclude_member + "was successfully excluded");
                }
                else{
                    System.out.println("you are not the admin of the discussion and thus cannot exclude someone");
                }
                return;
            }
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
    public String find_messages_date(String dateString,DatabaseUsers users_db,String[] members_username){

        ArrayList<Integer> members_id = new ArrayList<Integer>();
        for (String member:members_username){
            User member_user = users_db.get_user("username", member);
            if (member_user == null){
                return member+ "is not in the database user";
            }
            members_id.add(member_user.get_userid());
        }
        Collections.sort(members_id);
        String all_messages="";

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false); // Pour rendre la validation stricte
        Date date=null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Date invalide");
        }
        for (Discussion discussion:users_discussions){
            if (discussion.getmembers_id().equals(members_id)){ // check if all members are in the discussion
                for (Message current_message:discussion.getmessages()){
                    if (date_equal(date, current_message.get_date())){
                        all_messages += current_message.see_details(users_db);
                        return current_message.see_details(users_db);
                    }
                }
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
    //create singleton DatabaseDiscussion
    public static synchronized DatabaseDiscussion getInstance() 
    { 
        if (singleton == null) 
            singleton = new DatabaseDiscussion();
    
        return singleton;
    } 
}
