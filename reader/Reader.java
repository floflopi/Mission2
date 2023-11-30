package reader;
import java.util.Scanner;
// classe indiquant les différentes actions disponible pour un utilisateur
public class Reader {
    private Scanner scanner;
    private String input;
    public Reader(){
        scanner = new Scanner(System.in);
    }
    public void readinput(String message){
        if (message != null){
            System.out.printf(message);
        }
        input = scanner.nextLine();
    }
    public String getinput(){
        return input;
    }
    public Scanner getScanner(){
        return scanner;
    }
}
