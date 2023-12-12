package testui;

import java.util.Scanner;
import javax.swing.JButton;

import actions.Actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import db.*;
import discussion.Discussion;
import ui.*;
import user.User;

public class MainUITest {
    private static DatabaseUsers users_db;
    private static DatabaseDiscussion disc_db;
    private static String[] users_emails = {"a@gmail.com","Flopi_Flo@gmail.com","Louis@gmail.com","Sarah@gmail.com","Julien@gmail.com","Dupont@gmail.com"};

    private static int currentFunctionIndex = 1;

    static String defaultEmail = "Flopi_Flo@gmail.com";
    static String defaultUsername = "Flopi_Flo";
    static String defaultPassword = "Flopi_Flo";
    static User u = new User(defaultEmail,defaultUsername,defaultPassword,1);
    static LoginInterface login;
    static TestUserMainUI usermainui;
    static FriendUI friendui;
    private static Actions actions;
    private static MediaUI mediaui;

    static Boolean micro = true;
    static Boolean camera = true;
    static Boolean safemode = true;

    static Integer cooldown = 3000;

    // Ouverture de l'app + login
    public static void scenario0() throws InterruptedException {
        init_database();
        // Open th login window
        login = new LoginInterface("Application",users_db,disc_db, actions);
        // Connect the default usze (Flopi_Flo)
        login.setDefaultValues(defaultUsername, defaultPassword);
        Thread.sleep(cooldown);
        // Add Sarah as a new friend
        u.get_liste_contact().add(users_db.get_user("username", "Sarah").get_userid());
        // Open the MainUI window
        usermainui = new TestUserMainUI("Application",u,disc_db,users_db, actions);
        // Close the login window
        login.closeWindow();
    }

    // Switch connected status to idle
    public static void scenario1() throws InterruptedException {
        Thread.sleep(cooldown);
        // Set the status to idle
        usermainui.setUserStatus(1);
        Thread.sleep(cooldown);
        // Add Louis as a new friend
        u.get_liste_contact().add(users_db.get_user("username", "Louis").get_userid());
        // Open the friends window
        friendui = new FriendUI(users_db, disc_db, u, actions);
        Thread.sleep(cooldown);
        // Close the friend window
        friendui.closeWindow();

        Thread.sleep(cooldown);
        // Create and open a new discussion
        String members = "Louis,Sarah";
        if (disc_db.verify_disc_creation("test",members, u, users_db)){
            usermainui.update_discussions(); 
            // Simulation of clicking on the button to open the discussion 
            JButton currentdisc_btn = usermainui.get_currentdisc_btn(members);
            if (currentdisc_btn != null) {
                currentdisc_btn.doClick();
            }
        }
        Thread.sleep(cooldown);
        DiscussionPanel discussionPanel = usermainui.getDiscussionPanel();
        discussionPanel.getFileButton().getButton().doClick();
        mediaui = discussionPanel.getMediaUI();
        Thread.sleep(cooldown);      
        mediaui.getButtons()[0].doClick(); // random button 
        System.out.println("Scénario 1 exécuté");
    }

    public static void scenario2() throws InterruptedException {
        if (mediaui != null) mediaui.closeWindow();
        Thread.sleep(cooldown);

        // Open the friends window
        friendui = new FriendUI(users_db, disc_db, u, actions);
        Thread.sleep(cooldown);
        // Close the friend window
        friendui.closeWindow();

        System.out.println("Scénario 2 exécuté");
    }

    public static void scenario3() throws InterruptedException {
        Thread.sleep(cooldown);
        // Active le safemode
        usermainui.click_simulation(2);
        Thread.sleep(cooldown);
        // Désactive le mode adulte
        usermainui.click_simulation(3);
        Thread.sleep(cooldown);
        friendui = new FriendUI(users_db, disc_db, u, actions);
        Thread.sleep(cooldown);
        friendui.closeWindow();

        /*
        Thread.sleep(cooldown);
        String[] membersList = {"Louis,Sarah"};
        ArrayList<Integer> members_id = new ArrayList<Integer>(Arrays.asList(users_db.get_user("username", "Louis").get_userid(), users_db.get_user("username", "Sarah").get_userid()));
        Discussion current_discussion = new Discussion(members_id, false);
        DiscussionPanel testdiscussionpanel =  new DiscussionPanel(usermainui, current_discussion, membersList, actions);
        mediaui =  new TestMediaUI(users_db, disc_db, u, current_discussion, testdiscussionpanel, actions);
        */
        
        System.out.println("Scénario 3 exécuté");
    }

    public static void scenario4() throws InterruptedException {
        if (mediaui != null) mediaui.closeWindow();
        Thread.sleep(cooldown);
        // Set le status à ne pas déranger
        usermainui.setUserStatus(2);
        Thread.sleep(cooldown);

        // Désactive le safemode
        usermainui.click_simulation(2);
        Thread.sleep(cooldown);
        friendui = new FriendUI(users_db, disc_db, u, actions);

        System.out.println("Scénario 4 exécuté");
    }

    public static void scenario5() throws InterruptedException {
        friendui.closeWindow();
        // Active le safemode
        Thread.sleep(cooldown);
        usermainui.click_simulation(2);
        Thread.sleep(cooldown);

        friendui = new FriendUI(users_db, disc_db, u, actions);


        System.out.println("Scénario 5 exécuté");
    }


    public static void scenario6() throws InterruptedException {
        friendui.closeWindow();
        //usermainui.closeWindow();

        Thread.sleep(cooldown);
        // Active le mode adulte
        usermainui.click_simulation(3);

        Integer sarah = users_db.get_user("username", "Sarah").get_userid();
        u.get_liste_contact().remove(sarah);

        friendui = new FriendUI(users_db, disc_db, u, actions);
        Thread.sleep(cooldown);
        friendui.closeWindow();
        
        /*
        Thread.sleep(cooldown);
        String[] membersList = {"Louis,Sarah"};
        ArrayList<Integer> members_id = new ArrayList<Integer>(Arrays.asList(users_db.get_user("username", "Louis").get_userid(), users_db.get_user("username", "Sarah").get_userid()));
        Discussion current_discussion = new Discussion(members_id, false);
        DiscussionPanel testdiscussionpanel =  new DiscussionPanel(usermainui, current_discussion, membersList, actions);
        mediaui =  new TestMediaUI(users_db, disc_db, u, current_discussion, testdiscussionpanel, actions);
        */

        System.out.println("Scénario 6 exécuté");
    }

    public static void scenario7() throws InterruptedException {
        if (mediaui != null) mediaui.closeWindow();
        Thread.sleep(cooldown);
        // Set le status à en ligne
        usermainui.setUserStatus(0);
        Thread.sleep(cooldown);

        // Désactive le mode adulte
        usermainui.click_simulation(3);
        Thread.sleep(cooldown);

        friendui = new FriendUI(users_db, disc_db, u, actions);
        Thread.sleep(cooldown);
        friendui.closeWindow();
        
        /*
        Thread.sleep(cooldown);
        String[] membersList = {"Louis,Sarah"};
        ArrayList<Integer> members_id = new ArrayList<Integer>(Arrays.asList(users_db.get_user("username", "Louis").get_userid(), users_db.get_user("username", "Sarah").get_userid()));
        Discussion current_discussion = new Discussion(members_id, false);
        DiscussionPanel testdiscussionpanel =  new DiscussionPanel(usermainui, current_discussion, membersList, actions);
        mediaui =  new TestMediaUI(users_db, disc_db, u, current_discussion, testdiscussionpanel, actions);
        */

        System.out.println("Scénario 7 exécuté");
    }

    public static void scenario8() throws InterruptedException {
        if (mediaui != null) mediaui.closeWindow();
        Thread.sleep(cooldown);
        // Désactive le micro
        usermainui.click_simulation(0);
        Thread.sleep(cooldown);

        System.out.println("Scénario 8 exécuté");
    }

    public static void scenario9() throws InterruptedException {
        friendui.closeWindow();
        Thread.sleep(cooldown);
        // Active le micro
        usermainui.click_simulation(0);
        Thread.sleep(cooldown);

        // Active le mode adulte
        usermainui.click_simulation(3);
        Thread.sleep(cooldown);
        friendui = new FriendUI(users_db, disc_db, u, actions);

        System.out.println("Scénario 9 exécuté");
    }

    public static void scenario10() throws InterruptedException {
        friendui.closeWindow();
        Thread.sleep(cooldown);

        // Désactive le mode adulte
        usermainui.click_simulation(3);
        Thread.sleep(cooldown);

        // Désactive le safemode
        usermainui.click_simulation(2);
        Thread.sleep(cooldown);
        friendui = new FriendUI(users_db, disc_db, u, actions);
        
        System.out.println("Scénario 10 exécuté");
    }
    public static void main(String[] args) throws InterruptedException{
        Scanner scanner = new Scanner(System.in);
        actions = new Actions(disc_db, users_db, null, u);
        actions.setScenarioMode(true); 
        scenario0();
        while (true) {
            System.out.print("Tapez 'next' pour exécuter le scénario suivant ou 'exit' pour quitter: ");
            String userInput = scanner.nextLine().toLowerCase();
            switch (userInput) {
                case "next":
                    switch (currentFunctionIndex) {
                        case 1:
                            scenario1();
                            break;
                        case 2:
                            scenario2();
                            break;
                        case 3:
                            scenario3();
                            break;
                        case 4:
                            scenario4();
                            break;
                        case 5:
                            scenario5();
                            break;
                        case 6:
                            scenario6();
                            break;
                        case 7:
                            scenario7();
                            break;
                        case 8:
                            scenario8();
                            break;
                        case 9:
                            scenario9();
                            break;
                        case 10:
                            scenario10();
                            break;
                        case 11:
                            scanner.close();
                            System.exit(0);
                            break;
                    }
                    currentFunctionIndex = (currentFunctionIndex % 11) + 1;
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Commande non reconnue. Tapez 'next' pour exécuter le scénario suivant ou 'exit' pour quitter.");
            }
        }
    }

    public static void init_database(){
        users_db = DatabaseUsers.getInstance(users_emails);
        disc_db = DatabaseDiscussion.getInstance();
    }
}
