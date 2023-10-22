//classe contenant toutes les actions possibles dans l app
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
public class Actions {
    // hashmap : action-isactif
    private HashMap<String,Boolean> actions;
    public Actions(){
        String[] liste_actions = new String[]{"help","addfriend","acceptfriend",
        "removefriend","block","sendmessage","sendimage","sendvideo","findbyDate","findbymessage"};
        actions = new HashMap<>();
        for (String action : liste_actions){
            actions.put(action, true);
        }
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
        Fichier file;
        String discussion_membres;
        // essaie de voir si une fonctionnalité est activée ou non 
        if (actions.containsKey(reader.getinput()) && !actions.get(reader.getinput())){
            System.out.println("Fonctionnalité désactivée !");
            return;
        }
        switch (reader.getinput()){
            case "help":
                System.out.println("\nValid commands :\n"+
                                   "changeuser : change current user connected\n"+ 
                                   "addfriend : send a friend request to a user\n"+
                                   "acceptfriend : accept a friend request\n"+
                                   "removefriend : remove a friend to the user contact list\n"+
                                   "block : block a friend so that he can't message you or send friend request again"+
                                   "(you will no longer see the messages from this person) \n"+
                                   "exclude : exclude someone from a discussion if you are the admin of the discussion\n"+
                                   "sendmessage : envoie un message à une ou plusieurs personnes\n"+
                                   "sendimage : envoie une image dans une conversation\n"+
                                   "sendvideo : envoie une vidéo dans une conversation\n"+
                                   "findbyDate : permet d'afficher tous les messages à une certaine date dans une conversation\n"+
                                   "findbymessage: permet de retrouver un message textuel spécifique dans une conversation\n"+
                                   "activate feature1,feature2,... : active certaines features\n"+
                                   "deactivate feature1,feature2,... : désactive certaines features\n"+
                                   "quit : quitte le programme");
                break;            
            case "addfriend":
                reader.readinput("Write the username you want to add to your contact list :");
                currentuser.send_friendrequest(reader.getinput(), users_db);
                break;
            case "acceptfriend":
                if (currentuser.get_friendrequest().isEmpty()){
                    System.out.println("no one sent you a friend request.");
                    break;
                }
                System.out.println("Friends request : "+ currentuser.get_string_friendrequest(users_db));
                reader.readinput("Write the username you want to accept as your friend :");
                currentuser.accept_friend_request(reader.getinput(),users_db);
                break;
            case "removefriend":
                reader.readinput("Write the username you want to remove from your contact list :");
                currentuser.remove_friend(reader.getinput(), users_db);
                break;
            case "sendmessage":
                reader.readinput("Write the message you want to send : ");
                String message = reader.getinput();
                reader.readinput("Write the username of the people you want to send this message (each seperated by a ',') : ");
                currentuser.send_message(reader.getinput(),new Message(message,new Date(),currentuser.get_userid()),users_db,discussions_db);
                break;
            case "changeuser":
                Main.select_user(reader);
                break;
            // bad smells : duplicate code with sendimage/sendvideo but cannot change it 
            case "sendimage": 
                file = new Fichier("image",new Date(),currentuser.get_userid());
                reader.readinput("Write the username of the people you want to send this message (each seperated by a ',') : ");
                currentuser.send_message(reader.getinput(),file,users_db,discussions_db);
                break;
            case "sendvideo": 
                file = new Fichier("video",new Date(),currentuser.get_userid());
                reader.readinput("Write the username of the people you want to send this message (each seperated by a ',') : ");
                currentuser.send_message(reader.getinput(),file,users_db,discussions_db);
                break;
            case "block":
                reader.readinput("Write the username you want to block");
                currentuser.block_user(reader.getinput(),users_db);
                break;
            case "findbymessage":
                reader.readinput("Write the username of the people you share a discussion with (each seperated by a ',') : ");
                discussion_membres = reader.getinput()+","+currentuser.get_username();
                reader.readinput("Write the message you want to check");
                System.out.println(discussions_db.find_message(reader.getinput(), users_db,discussion_membres.split(",")));
                break;
            case "findbyDate":
                reader.readinput("Write the username of the people you share a discussion with (each seperated by a ',') : ");
                discussion_membres = reader.getinput()+","+currentuser.get_username();
                reader.readinput("Write the date you want to check (with format dd/MM/yyyy)");
                System.out.println(discussions_db.find_messages_date(reader.getinput(), users_db,discussion_membres.split(",")));
                break;
            case "exclude":
                reader.readinput("Write the username of the people you share a discussion with (each seperated by a ',') : ");
                discussion_membres = reader.getinput()+","+currentuser.get_username();
                reader.readinput("Write the username you want to exclude from the conversation :");
                discussions_db.exclude_member(reader.getinput(), new ArrayList<String>(Arrays.asList(discussion_membres.split(","))),
                    users_db,currentuser);
            case "quit":
                break;
            // case deactivate/activate
            default:
                if (reader.getinput().length() < 10){
                    System.out.println("Commande invalide");
                }
                else if (reader.getinput().substring(0, 9).equals("activate ")){
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
