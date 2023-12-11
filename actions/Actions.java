//classe contenant toutes les actions (features) possibles dans l'app
package actions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;

import commands.*;
import db.DatabaseDiscussion;
import db.DatabaseUsers;
import discussion.*;
import reader.Reader;
import ui.DiscussionPanel;
import user.User;
import ui.*;
public class Actions {
    // hashmap : action-isactif
    private HashMap<String,Boolean> optional_features;
    private ArrayList<String> mandatory_features;
    ArrayList<String> liste_features;
    private Map<String, Command> commandMap;
    private Scanner scanner;
    private DatabaseDiscussion disc_db;
    private DatabaseUsers users_db;
    private Discussion current_discussion;
    private User current_user;

    private DiscussionPanel disc_panel;
    private MediaUI mediaUI;
    private FriendUI friendUI;

    private boolean scenarioMode = false;

    public void setScenarioMode(boolean scenarioMode) {
        this.scenarioMode = scenarioMode;
    }

    public Actions(DatabaseDiscussion disc_db,DatabaseUsers users_db,Discussion curr_disc,User current_user){
        this.disc_db = disc_db;
        this.users_db = users_db;
        this.current_discussion = curr_disc;
        this.current_user = current_user;
        scanner = new Scanner(System.in);
        commandMap = new HashMap<>();
        mandatory_features = new ArrayList<>(Arrays.asList(new String[]{"changeuser", "quit", "help"}));
        optional_features = new HashMap<String,Boolean>();
        command_map_initialization();
        for (String feature : liste_features){
            optional_features.put(feature, true);
        }
        startListening();
    }
    public void setDiscussionPanel(DiscussionPanel disc_panel){
        this.disc_panel = disc_panel;
    }
    public void setDiscussion(Discussion discussion){
        current_discussion = discussion;
    }
    public void setFriendUI(FriendUI friendUI){
        this.friendUI = friendUI;
    }
    public void setMediaUi(MediaUI mediaUI){
        this.mediaUI = mediaUI;
    }
    public HashMap<String,Boolean> get_optional_features(){
        return optional_features;
    }
    public void startListening() {
        new Thread(() -> {
            while (true) {
                // Faites quelque chose avec l'entrée utilisateur, par exemple imprimez "Hello"
                execute_action(users_db, disc_db, current_discussion,current_user );
            }
        }).start();
    }
    private void command_map_initialization() {
        String packageName = "commands";

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = ("./" + packageName);
        liste_features = new ArrayList<>(Arrays.asList(new String[]{}));
        
        try {
            Enumeration<URL> resources = classLoader.getResources(path);
        
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                if (resource.getProtocol().equals("file")) {
                    File file = new File(resource.toURI());
        
                    File[] files = file.listFiles();
                    if (files != null) {
                        for (File commandFile : files) {
                            if (commandFile.isFile() && commandFile.getName().endsWith(".class")) {
                                String className = packageName + '.' + commandFile.getName().replace(".class", "");
                                try {
                                    Class<?> commandClass = Class.forName(className);
        
                                    if (Command.class.isAssignableFrom(commandClass) && !commandClass.isInterface()) {
                                        CommandInfo commandInfo = commandClass.getAnnotation(CommandInfo.class);
        
                                        if (commandInfo != null) {
                                            if (commandInfo.optionnal()) {
                                                liste_features.add(commandInfo.name());
                                            } else {
                                                mandatory_features.add(commandInfo.name());
                                            }
                                            Constructor<?> constructor = commandClass.getConstructor();
                                            Command commandInstance = (Command) constructor.newInstance();
                                            commandMap.put(commandInfo.name(), commandInstance);
                                        }
                                    }
                                } catch (ClassNotFoundException | NoSuchMethodException
                                        | IllegalAccessException | InstantiationException
                                        | InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
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
        if (disc_panel != null){
            // update the discussion panel UI
            disc_panel.updateFeatures();
        }
        if (mediaUI != null){
            mediaUI.updateFeatures();
        }
        if (friendUI != null){
            friendUI.updateFeatures();
        }
    }
    // try to execute action 
    public void execute_action(DatabaseUsers users_db,DatabaseDiscussion discussions_db,Discussion current_discussion,User currentuser){
        if (scenarioMode) {
            return;
        }

        String input = scanner.nextLine();//reader.getinput().toLowerCase();

        if (commandMap.containsKey(input)) {
            // essaie de voir si une fonctionnalité est activée ou non 
            if (optional_features.containsKey(input) && !optional_features.get(input)){
                System.out.println("Fonctionnalité désactivée !");
                return;
            }
            else {
                commandMap.get(input).execute(input, users_db,current_discussion, discussions_db, currentuser);
            }
        } else {
            if (!input.contains("deactivate") && !input.contains("activate")) {
                System.out.println("Invalid command");
            }
        }



        switch (input){
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
                //Main.select_user(reader);
                break;      
            case "quit":
                break;
            // case deactivate/activate
            default:
                if (input.length() < 10 || input.length() == 10){
                    System.out.println("Invalid command");
                }
                else if (input.substring(0, 9).equals("activate ")){
                    activate_features(true,input.substring(9));
                }
                else if (input.substring(0, 11).equals("deactivate ")){
                    activate_features(false,input.substring(11));
                }
                else{
                    System.out.println("Invalid command");
                }
                break;
        }
    }
}