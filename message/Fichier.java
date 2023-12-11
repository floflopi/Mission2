package message;
import java.util.Date;

import db.DatabaseUsers;
public class Fichier extends Message{
    private String type_fichier;
    public Fichier(String type_fichier,Date date,int from_user_id){
        super(type_fichier,date,from_user_id);
        
    }
    public String get_type_fichier(){
        return type_fichier;
    }
    
    @Override
    public String send(String type_fichier) {
        System.out.println(type_fichier);
        System.out.println("type");
        switch (type_fichier) {
            case "image":
                System.out.println("image will be send");
                return "image sent !";
            case "video":
                return "video sent !";
            case "gif":
                return "gif sent !";
            case "vocaux":
                return "vocal sent !";
            case "autresfichiers":
                return "other files sent !";
            default:
                
                return null;
        }
    }

    @Override
    public String see_details(DatabaseUsers db){
        String details = "This is a "+ get_type_fichier()+  "\nDate : " + get_date() + "\nFrom " 
        + db.get_all_users().get(Integer.valueOf(get_from_user_id())).get_username();
        return details;
    }

}
