import db.DatabaseDiscussion;
import db.DatabaseUsers;
import reader.Reader;
import user.User;

public class Main{
    private static DatabaseUsers user_db;
    private static DatabaseDiscussion discussions_db;
    private static String[] users_emails = {"a@gmail.com","b@gmail.com","c@gmail.com","d@gmail.com","e@gmail.com"};
    private static User current_user;
    public static String[] get_users_emails(){
        return users_emails;
    }
    public static void main(String[] args) {
        // test users
        init_database();
        Actions actions = new Actions();
        Reader reader = new Reader();
        select_user(reader);
        // user is connected
        while (!reader.getinput().equals("quit")){
            System.out.printf("Specify the command to execute (use 'help' for more details):");
            reader.readinput(null);
            actions.check_action(reader,user_db,discussions_db,current_user);
        }
        reader.getScanner().close();
    }
    public static void init_database(){
        user_db = DatabaseUsers.getInstance(users_emails);
        discussions_db = DatabaseDiscussion.getInstance();
    }

    public static void print_available_email(){
        String print = "Available emails :";
        for (int i = 0; i < users_emails.length; i++) {
            print = print + users_emails[i];
            if (i != users_emails.length - 1) {
                print = print + ",";
            }
        }
        System.out.println(print);
    }
    public static void select_user(Reader reader){
        print_available_email();
        System.out.printf("Select an email from the list above:");
        reader.readinput(null);
        //check if user in db
        current_user = user_db.get_user("email",reader.getinput());
        while (current_user == null){
            System.out.printf("this user is not in the database, retry : ");
            reader.readinput(null);
            current_user = user_db.get_user("email",reader.getinput());
        }
    }
}
