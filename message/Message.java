package message;
import java.util.Date;

import db.DatabaseUsers;

public class Message {
    private String contenu;
    private Date date;
    private int from_user_id;
    public Message(String contenu,Date date,int from_user_id){
        this.contenu = contenu;
        this.date = date;
        this.from_user_id = from_user_id;
    }
    public String get_contenu(){
        return contenu;
    }
    public Date get_date(){
        return date;
    }
    public int get_from_user_id(){
        return from_user_id;
    }
    public String see_details(DatabaseUsers db){
        String details = "contenu : " + get_contenu() + "\nDate : " + get_date() + "\nFrom " 
        + db.get_all_users().get(Integer.valueOf(get_from_user_id())).get_username();
        return details;
    }
    public void send(){
        System.out.println("message sent !");
    }
}
