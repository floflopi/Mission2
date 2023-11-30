package message;
import java.util.Date;

import db.DatabaseUsers;
public class Fichier extends Message{
    private String type_fichier;
    public Fichier(String type_fichier,Date date,int from_user_id){
        super(null,date,from_user_id);
        this.type_fichier = type_fichier;
    }
    public String get_type_fichier(){
        return type_fichier;
    }
    
    @Override
    public void send(){
        if (type_fichier.equals("image")){
            System.out.println("image sent !");
        }
        else if (type_fichier.equals("video")){
            System.out.println("video sent !");
        }
    }

    @Override
    public String see_details(DatabaseUsers db){
        String details = "This is a "+ get_type_fichier()+  "\nDate : " + get_date() + "\nFrom " 
        + db.get_all_users().get(Integer.valueOf(get_from_user_id())).get_username();
        return details;
    }

}
