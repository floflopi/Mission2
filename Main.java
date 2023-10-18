import java.util.Scanner;
import java.util.Date;
public class Main{
    public static void main(String[] args) {
        Reader reader = new Reader();
        System.out.printf("Quel est votre email ?");
        reader.readinput();
        DatabaseUsers database = new DatabaseUsers();
        Date current_date = new Date();
        //check if user in db
        while (!database.user_in_db(true,reader.getinput())){
            System.out.println("user is not in the database, retry");
            reader.readinput();
        }
        User current_user = database.get_user(true,reader.getinput());
        // user is connected
        while (!reader.getinput().equals("stop")){
            System.out.println("indiquer la commande a faire (help pour plus de détails) : ");
            reader.readinput();
            reader.check_action(database,current_user);
        }
        reader.getScanner().close();
    }
}
