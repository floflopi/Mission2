//classe contenant toutes les actions possibles dans l app
import java.util.HashMap;
public class Actions {
    //hashmap : action-isactif
    private HashMap<String,Boolean> actions;
    public Actions(){
        actions = new HashMap<>();
        actions.put("help", true); // help should not be deactivated
        actions.put("addfriend", true);
        actions.put("removefriend", true);
        actions.put("sendmessage", true);
        actions.put("checkmessage", true);
        actions.put("checkdiscussion", true);
    }
    public void activate_features(boolean active,String features){
        for (String feature:features.split(",")){
            if (!actions.containsKey(feature)){
                System.out.println("Invalid feature");
            }
            else{
                actions.put(feature, active);
            }
        }
    }
    public void check_action(Reader reader,DatabaseUsers users_db,DatabaseDiscussion discussions_db,User currentuser){
        // essaie de voir si une fonctionnalité est activée ou non 
        if (actions.containsKey(reader.getinput()) && !actions.get(reader.getinput())){
            System.out.println("Fonctionnalité désactivée !");
            return;
        }
        switch (reader.getinput()){
            case "help":
                System.out.println("\nValid commands :\n"+
                                   "addfriend : add a friend to the user contact list\n"+
                                   "removefriend : remove a friend to the user contact list\n"+
                                   "sendmessage : envoie un message à une ou plusieurs personnes\n"+
                                   "checkmessage : permet de retrouver un message et d'afficher les détails du message\n"+
                                   "activate feature1,feature2,... : active certaines features\n"+
                                   "deactivate feature1,feature2,... : désactive certaines features\n"+
                                   "quit : quitte le programme");
                break;            
            case "addfriend":
                reader.readinput("Write the username you want to add to your contact list :");
                currentuser.add_friend(reader.getinput(), users_db);
                break;
            case "removefriend":
                reader.readinput("Write the username you want to remove from your contact list :");
                currentuser.remove_friend(reader.getinput(), users_db);
                break;
            case "sendmessage":
                reader.readinput("Write the message you want to send : ");
                String message = reader.getinput();
                reader.readinput("Write the username of the people you want to send this message (each seperated by a ',') : ");
                currentuser.send_message(reader.getinput(),message,users_db,discussions_db);
                break;
            case "checkmessage":
                reader.readinput("Write the message you want to find : ");
                System.out.println(discussions_db.find_message(reader.getinput(),users_db));
                break;
            case "quit":
                break;
            // case deactivate/activate
            default:
                if (reader.getinput().substring(0, 9).equals("activate ")){
                    activate_features(true,reader.getinput().substring(9));
                }
                else if (reader.getinput().substring(0, 11).equals("deactivate ")){
                    activate_features(false,reader.getinput().substring(11));
                }
                else{
                    System.out.println("Commande invalide");
                }
                break;
        }
    }
}
