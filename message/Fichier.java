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
        System.out.println("type fichier");
        System.out.println(type_fichier);
        switch (type_fichier) {
            case "Image":
                return "image sent !";
            case "Video":
                return "video sent !";
            case "Gif":
                return "gif sent !";
            case "Vocaux":
                return "vocal sent !";
            case "Autres fichiers":
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
