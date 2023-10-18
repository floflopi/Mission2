import java.util.ArrayList;

public class DatabaseDiscussion {
    private ArrayList<Discussion> users_discussions;
    public DatabaseDiscussion(){
        users_discussions = new ArrayList<Discussion>();
    }
    public void add_discussions(Discussion discussion){
        users_discussions.add(discussion);
    }
    public ArrayList<Discussion> getusers_discussions(){
        return users_discussions;
    }
    // retourne true si pour les users_id une discussion existe 
    public boolean discussion_exist(ArrayList<Integer> users_id){
        for (Discussion discussion:users_discussions){
            if (discussion.getmembers_id().equals(users_id)){
                return true;
            }
        }
        return false;
    }
    //atm retourne le premier message qu on trouve. apres peut etre extend a find all ? 
    public String find_message(String message,DatabaseUsers users_db){
        for (Discussion discussion:users_discussions){
            for (Message current_message:discussion.getmessages()){
                if (current_message.get_contenu().equals(message)){
                    return current_message.see_details(users_db);
                }
            }
        }
        return "No message found !";
    }
    // retourne la discussion qui concerne tout les users_id
    public Discussion get_discussion(ArrayList<Integer> users_id){
        for (Discussion discussion:users_discussions){
            if (discussion.getmembers_id().equals(users_id)){
                return discussion;
            }
        }
        return null;
    }
}
