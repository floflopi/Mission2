import java.util.Date;

public class Message {
    private String contenu;
    private Date date;
    private int from_user_id;
    public String get_contenu(){
        return contenu;
    }
    public Date get_date(){
        return date;
    }
    public String see_details(DatabaseUsers db){
        String details = "contenu : " + get_contenu() + "\nDate : " + get_date() + "\nFrom " + db.get_all_users().get(from_user_id).get_username();
        return details;
    }
}
