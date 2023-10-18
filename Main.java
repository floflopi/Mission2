import java.util.Scanner;
import java.util.Date;
public class Main{
    private static DatabaseUsers user_db;
    private static DatabaseDiscussion discussions_db;
    public static void main(String[] args) {
        Reader reader = new Reader();
        System.out.printf("Quel est votre email ?");
        reader.readinput();
        Main.init_database();
        Date current_date = new Date();
        //check if user in db
        while (!user_db.user_in_db(true,reader.getinput())){
            System.out.println("user is not in the database, retry");
            reader.readinput();
        }
        User current_user = user_db.get_user(true,reader.getinput());
        // user is connected
        while (!reader.getinput().equals("stop")){
            System.out.println("indiquer la commande a faire (help pour plus de détails) : ");
            reader.readinput();
            reader.check_action(user_db,discussions_db,current_user);
        }
        reader.getScanner().close();
    }
    public static void init_database(){
        user_db = new DatabaseUsers();
        discussions_db = new DatabaseDiscussion();
    }
}
