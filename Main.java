public class Main{
    private static DatabaseUsers user_db;
    private static DatabaseDiscussion discussions_db;
    private static String[] users_emails = {"flo@gmail.com","louis@gmail.com","sarah@gmail.com","nathan@gmail.com","julie@gmail.com"};
    private static User current_user;
    public static String[] get_users_emails(){
        return users_emails;
    }
    public static void main(String[] args) {
        // test users
        Main.init_database();
        Actions actions = new Actions();
        Reader reader = new Reader();
        select_user(reader);
        // user is connected
        while (!reader.getinput().equals("quit")){
            System.out.printf("indiquer la commande a faire (help pour plus de détails) : ");
            reader.readinput(null);
            actions.check_action(reader,user_db,discussions_db,current_user);
        }
        reader.getScanner().close();
    }
    public static void init_database(){
        user_db = DatabaseUsers.getInstance(Main.users_emails);
        discussions_db = DatabaseDiscussion.getInstance();
    }

    public static void print_available_email(){
        String print = "Available emails : ";
        for (String email:users_emails){
            print = print + email +", ";
        }
        System.out.println(print);
    }
    public static void select_user(Reader reader){
        print_available_email();
        System.out.printf("Quel est votre email ?");
        reader.readinput(null);
        //check if user in db
        current_user = user_db.get_user("email",reader.getinput());
        while (current_user == null){
            System.out.printf("user is not in the database, retry : ");
            reader.readinput(null);
            current_user = user_db.get_user("email",reader.getinput());
        }
    }
}
