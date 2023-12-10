package testui;

import java.util.Scanner;
import javax.swing.JButton;

import db.*;
import ui.NewDiscussionUI;
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
    static TestLoginInterface login;
    static TestUserMainUI usermainui;
    static TestFriendUI friendui;

    // Ouverture de l'app + login
    public static void scenario0() throws InterruptedException {
        init_database();
        // Open th login window
        login = new TestLoginInterface("Application",users_db,disc_db);
        // Connect the default usze (Flopi_Flo)
        login.setDefaultValues(defaultUsername, defaultPassword);
        Thread.sleep(2500);
        // Add Sarah as a new friend
        u.get_liste_contact().add(users_db.get_user("username", "Sarah").get_userid());
        // Open the MainUI window
        usermainui = new TestUserMainUI("Application",u,disc_db,users_db);
        // Close the login window
        login.closeWindow();
    }

    // Switch connected status to idle
    public static void scenario1() throws InterruptedException {
        Thread.sleep(2500);
        // Set the status to idle
        usermainui.setUserStatus(1);
        System.out.println("Scénario 1 exécuté");
    }

    public static void scenario2() throws InterruptedException {
        // Add Louis as a new friend
        u.get_liste_contact().add(users_db.get_user("username", "Louis").get_userid());
        Thread.sleep(2500);
        // Open the friends window
        friendui = new TestFriendUI(users_db, disc_db, u);
        Thread.sleep(2500);
        // Close the friend window
        friendui.closeWindow();
        
        //Thread.sleep(2500);
        //new NewDiscussionUI(usermainui, "New Discussion !", 0, 0, u, disc_db, users_db);
        //disc_db.create_discussion(usermainui, "New Discussion", 400, 300, u, users_db);
        //Thread.sleep(2500);

        // Create and open a new discussion
        String members = "Louis,Sarah";
        if (disc_db.verify_disc_creation("test",members,u, users_db)){
            usermainui.update_discussions(); 
            // Simulation of clicking on the button to open the discussion 
            JButton currentdisc_btn = usermainui.get_currentdisc_btn(members);
            if (currentdisc_btn != null) {
                currentdisc_btn.doClick();
            }
        }

        System.out.println("Scénario 2 exécuté");
    }

    public static void scenario3() throws InterruptedException {
        //friendui.closeWindow();
        System.out.println("Scénario 3 exécuté");
    }

    public static void scenario4() throws InterruptedException {
        System.out.println("Scénario 4 exécuté");
    }

    public static void scenario5() throws InterruptedException {
        System.out.println("Scénario 5 exécuté");
    }


    public static void scenario6() throws InterruptedException {
        // Remove Sarah from friends
        Integer Sarah = users_db.get_user("username", "Sarah").get_userid();
        u.get_liste_contact().remove(Sarah);
        System.out.println("Scénario 6 exécuté");
    }

    public static void scenario7() throws InterruptedException {
        System.out.println("Scénario 7 exécuté");
    }

    public static void scenario8() throws InterruptedException {
        System.out.println("Scénario 8 exécuté");
    }

    public static void scenario9() throws InterruptedException {
        System.out.println("Scénario 9 exécuté");
    }

    public static void scenario10() throws InterruptedException {
        System.out.println("Scénario 10 exécuté");
    }
    public static void main(String[] args) throws InterruptedException{
        Scanner scanner = new Scanner(System.in);
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
