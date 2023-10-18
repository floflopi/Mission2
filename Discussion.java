import java.util.ArrayList;

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
    public static void find_message(String message){
        return;
    }
    public static void send_message(Message message,int from_user_id){
        return;
    }
    public static void receive(){
        return;
    }
}
