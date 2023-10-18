import java.util.Scanner;
// classe indiquant les différentes actions disponible pour un utilisateur
public class Reader {
    private Scanner scanner;
    private String input;
    public Reader(){
        scanner = new Scanner(System.in);
    }
    public void readinput(){
        input = scanner.nextLine();
    }
    public void readinput(String message){
        System.out.printf(message);
        input = scanner.nextLine();
    }
    public String getinput(){
        return input;
    }
    public Scanner getScanner(){
        return scanner;
    }
    // should I move this function in another class ? 
    public void check_action(DatabaseUsers db,User currentuser){
        switch (input){
            case "help":
                System.out.println("Valid commands :\n"+
                                   "addfriend : add a friend to the user contact list\n"+
                                   "removefriend : remove a friend to the user contact list");
                break;            
            case "addfriend":
                readinput("Write the username you want to add to your contact list :");
                currentuser.add_friend(input, db);
                break;
            case "removefriend":
                readinput("Write the username you want to remove from your contact list :");
                currentuser.remove_friend(input, db);
                break;
            default:
                break;
        }
    }
}
