package ui;
import java.util.Scanner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import db.*;
import user.User;
import ui.LoginInterface;
public class MainUI{
    private static DatabaseUsers users_db;
    private static DatabaseDiscussion disc_db;
    private static String[] users_emails = {"Flopi_Flo@gmail.com","Louis@gmail.com","Sarah@gmail.com","Julien@gmail.com","Dupont@gmail.com"};
    public static void main(String[] args){
        init_database();
        //LaunchGif app = new LaunchGif("Application",users_db,disc_db);
        new UserMainUI("app", new User("Flopi_Flo@gmail.com","Flopi_Flo","Flopi_Flo",0),disc_db,users_db);
    }

    public static void init_database(){
        users_db = DatabaseUsers.getInstance(users_emails);
        disc_db = DatabaseDiscussion.getInstance();
    }
}