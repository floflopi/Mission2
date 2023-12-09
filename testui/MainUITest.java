package testui;

import java.util.Scanner;

import db.*;
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
    static TestLoginInterface login;
    static TestUserMainUI usermainui;
    static TestFriendUI friendui;

    // Ouverture de l'app + login
    public static void scenario0() throws InterruptedException {
        init_database();
        login = new TestLoginInterface("Application",users_db,disc_db);
        login.setDefaultValues(defaultUsername, defaultPassword);
        Thread.sleep(2500);
        //login.try_connect(users_db);
        u.get_liste_contact().add(users_db.get_user("username", "Sarah").get_userid());
        //u.get_liste_contact().add(users_db.get_user("username", "Louis").get_userid());
        //u = users_db.get_user("email", "Louis@gmail.com");
        //u.send_friendrequest("Sarah", users_db);
        usermainui = new TestUserMainUI("Application",u,disc_db,users_db);
        login.closeWindow();
    }

    // Passage du statut connecté à absent
    public static void scenario1() throws InterruptedException {
        Thread.sleep(2500);
        usermainui.setUserStatus(1);
        System.out.println("Scénario 1 exécuté");
    }

    public static void scenario2() throws InterruptedException {
        u.get_liste_contact().add(users_db.get_user("username", "Louis").get_userid());
        Thread.sleep(2500);
        friendui = new TestFriendUI(users_db, disc_db, u);
        System.out.println("Scénario 2 exécuté");
    }

    public static void scenario3() throws InterruptedException {
        friendui.closeWindow();
        System.out.println("Scénario 3 exécuté");
    }

    public static void scenario4() throws InterruptedException {
        System.out.println("Scénario 4 exécuté");
    }

    public static void scenario5() throws InterruptedException {
        System.out.println("Scénario 5 exécuté");
    }


    public static void scenario6() throws InterruptedException {
        Integer Sarah = users_db.get_user("username", "Sarah").get_userid();
        u.get_liste_contact().remove(Sarah);
        //u.get_liste_contact().remove(users_db.get_user("username", "Sarah").get_userid());
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
