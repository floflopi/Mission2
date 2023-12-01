//classe contenant toutes les actions (features) possibles dans l'app
import java.util.HashMap;
import java.util.Map;

import commands.AcceptFriendCommand;
import commands.AddFriendCommand;
import commands.BlockCommand;
import commands.Command;
import commands.ExcludeCommand;
import commands.FindByDateCommand;
import commands.FindByMessageCommand;
import commands.RemoveFriendCommand;
import commands.SendImageCommand;
import commands.SendMessageCommand;
import commands.SendVideoCommand;
import db.DatabaseDiscussion;
import db.DatabaseUsers;
import reader.Reader;
import user.User;

import java.util.ArrayList;
import java.util.Arrays;
public class Actions {
    // hashmap : action-isactif
    private HashMap<String,Boolean> optional_features;
    private ArrayList<String> mandatory_features;
    private Map<String, Command> commandMap;
    public Actions(){
        // Design Pattern Command
        commandMap = new HashMap<>();
        commandMap.put("addfriend", new AddFriendCommand());
        commandMap.put("acceptfriend", new AcceptFriendCommand());
        commandMap.put("removefriend", new RemoveFriendCommand());
        commandMap.put("sendmessage", new SendMessageCommand());
        commandMap.put("sendimage", new SendImageCommand());
        commandMap.put("sendvideo", new SendVideoCommand());
        commandMap.put("block", new BlockCommand());
        commandMap.put("findbymessage", new FindByMessageCommand());
        commandMap.put("findbyDate", new FindByDateCommand());
        commandMap.put("exclude", new ExcludeCommand());
        //

        String[] liste_features = new String[]{"block","sendimage","sendvideo","findbyDate","findbymessage","exclude"};
        mandatory_features = new ArrayList<>(Arrays.asList(new String[]{"addfriend","changeuser","sendmessage","acceptfriend","removefriend","quit"}));
        optional_features = new HashMap<String,Boolean>();
        for (String feature : liste_features){
            optional_features.put(feature, true);
        }
    }
    public void activate_features(boolean active,String features){
        for (String feature:features.split(",")){
            if (mandatory_features.contains(feature) && !active){
                System.out.println("you cannot deactivate a mandatory feature");
            }
            else if (!optional_features.containsKey(feature) && !mandatory_features.contains(feature)){
                System.out.println("Invalid feature");
            }
            else{
                optional_features.put(feature, active);
            }
        }
    }
    public void check_action(Reader reader,DatabaseUsers users_db,DatabaseDiscussion discussions_db,User currentuser){
        // Design Pattern Command
        String input = reader.getinput();

        if (commandMap.containsKey(input)) {
            commandMap.get(input).execute(reader, users_db, discussions_db, currentuser);
        } else {
            System.out.println("Invalid command");
        }

        // essaie de voir si une fonctionnalité est activée ou non 
        if (optional_features.containsKey(reader.getinput()) && !optional_features.get(reader.getinput())){
            System.out.println("Fonctionnalité désactivée !");
            return;
        }
        switch (reader.getinput()){
            case "help":
                System.out.println("Valid commands :\n"+
                                   "changeuser : change current user connected\n"+ 
                                   "addfriend : send a friend request to a user\n"+
                                   "acceptfriend : accept a friend request\n"+
                                   "removefriend : remove a friend from the user contact list\n"+
                                   "block : block a friend so that he can't message you or send friend request again"+
                                  "(you will no longer see the messages from this person) \n"+
                                   "exclude : exclude someone from a discussion if you are the admin of the discussion\n"+
                                   "sendmessage : send a message in a conversation\n"+
                                   "sendimage : send an image in a conversation\n"+
                                   "sendvideo : send a video in a conversation\n"+
                                   "findbyDate : display all messages on a specific date in a conversation\n"+
                                   "findbymessage: find a specific message in a conversation\n"+
                                   "activate feature1,feature2,... : activate some features\n"+
                                   "deactivate feature1,feature2,... : deactivate some features\n"+
                                   "quit : exit the program");
                break;         
            case "changeuser":
                Main.select_user(reader);
                break;           
            case "quit":
                break;
            // case deactivate/activate
            default:
                if (reader.getinput().length() < 10 || reader.getinput().length() == 10){
                    //System.out.println("Invalid command");
                }
                else if (reader.getinput().substring(0, 9).equals("activate ")){
                    activate_features(true,reader.getinput().substring(9));
                }
                else if (reader.getinput().substring(0, 11).equals("deactivate ")){
                    activate_features(false,reader.getinput().substring(11));
                }
                else{
                    //System.out.println("Invalid command");
                }
                break;
        }
    }
}