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
    public void check_action(DatabaseUsers users_db,DatabaseDiscussion discussions_db,User currentuser){
        switch (input){
            case "help":
                System.out.println("Valid commands :\n"+
                                   "addfriend : add a friend to the user contact list\n"+
                                   "removefriend : remove a friend to the user contact list\n"+
                                   "sendmessage : envoie un message à une ou plusieurs personnes\n"+
                                   "checkmessage : permet de retrouver un message et d'afficher les détails du message");
                break;            
            case "addfriend":
                readinput("Write the username you want to add to your contact list :");
                currentuser.add_friend(input, users_db);
                break;
            case "removefriend":
                readinput("Write the username you want to remove from your contact list :");
                currentuser.remove_friend(input, users_db);
                break;
            case "sendmessage":
                readinput("Write the message you want to send : ");
                String message = input;
                readinput("Write the username of the people you want to send this message (each seperated by a ',') : ");
                currentuser.send_message(input,message,users_db,discussions_db);
            case "checkmessage":
                readinput("Write the message you want to find : ");
                System.out.println(discussions_db.find_message(input,users_db));   
            default:
                break;
        }
    }
}
