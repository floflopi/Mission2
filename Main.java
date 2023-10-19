public class Main{
    private static DatabaseUsers user_db;
    private static DatabaseDiscussion discussions_db;
    public static void main(String[] args) {
        // test users
        String[] users_email = {"flo@gmail.com","louis@gmail.com","sarah@gmail.com","nathan@gmail.com","julie@gmail.com"};
        print_available_email(users_email);
        Main.init_database(users_email);
        Actions actions = new Actions();
        Reader reader = new Reader();
        System.out.printf("Quel est votre email ?");
        reader.readinput(null);
        //check if user in db
        while (user_db.get_user(true,reader.getinput()) == null){
            System.out.println("user is not in the database, retry");
            reader.readinput(null);
        }
        User current_user = user_db.get_user(true,reader.getinput());
        // user is connected
        while (!reader.getinput().equals("quit")){
            System.out.printf("indiquer la commande a faire (help pour plus de détails) : ");
            reader.readinput(null);
            actions.check_action(reader,user_db,discussions_db,current_user);
        }
        reader.getScanner().close();
    }
    public static void init_database(String[] users_email){
        user_db = new DatabaseUsers(users_email);
        discussions_db = new DatabaseDiscussion();
    }
    public static void print_available_email(String[] emails){
        String print = "Available emails : ";
        for (String email:emails){
            print = print + email +", ";
        }
        System.out.println(print);
    }
}
