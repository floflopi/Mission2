import java.util.ArrayList;

/*
 * une discussion peut avoir lieu entre deux membres mais aussi des discussions de groupe
 *
 */
public class Discussion {
    public Discussion(ArrayList<Integer> members_id){
        messages = new ArrayList<>();
        this.members_id = members_id;
    }
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
}
