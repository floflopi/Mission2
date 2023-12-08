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
    private static String[] users_emails = {"a@gmail.com","b@gmail.com","c@gmail.com","Sarah@gmail.com","Julien@gmail.com","Dupont@gmail.com"};
    public static void main(String[] args){
        init_database();
        LaunchGif app = new LaunchGif("Application",users_db,disc_db);
        //User u = new User("a@gmail.com","a","a",1);
    //u.get_liste_contact().add(users_db.get_user("username", "a").get_userid());
        //new UserMainUI("app",u,disc_db,users_db);
    }

    public static void init_database(){
        users_db = DatabaseUsers.getInstance(users_emails);
        disc_db = DatabaseDiscussion.getInstance();
    }
}