package discussion;
import java.util.ArrayList;

import message.Message;
/*
 * une discussion contient une liste de tout les messages envoyés 
 * ainsi qu'une liste des id de membres participant à la discussion
 */
public class Discussion {
    public Discussion(ArrayList<Integer> members_id,boolean isprivate){
        messages = new ArrayList<>();
        this.members_id = members_id;
        this.isprivate = isprivate;
    }
    private boolean isprivate;
    private ArrayList<Message> messages;
    private ArrayList<Integer> members_id;

    public ArrayList<Integer> getmembers_id(){
        return members_id;
    }
    public ArrayList<Message> getmessages(){
        return messages;
    }
    public void add_message(Message message){
        messages.add(message);
    }
    public void remove_member(int id){
        this.members_id.remove(Integer.valueOf(id));
    }
    public boolean isprivate(){
        return isprivate;
    }
}
