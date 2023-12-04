package ui;
import java.util.Scanner;

import javax.swing.*;

import ui.LoginInterface;
import ui.WindowError;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.awt.event.KeyAdapter;

public class CustomInputField{


    private JTextField inputfield;
    private JPasswordField passwordField;

    public JPasswordField getpassword(){
        return passwordField;
    }    
    public JTextField getinput(){
        return inputfield;
    }


    public CustomInputField(String texte_defaut,int size_x, int size_y,boolean ispassword){
        passwordField = new JPasswordField(texte_defaut);
        passwordField.setPreferredSize(new Dimension(size_x,size_y));
        define_behavior_password(texte_defaut);
    }
    public CustomInputField(String texte_defaut,int size_x, int size_y){
        inputfield =new JTextField(texte_defaut);
        inputfield.setPreferredSize(new Dimension(size_x,size_y));
        define_behavior_input(texte_defaut);

    }
    public void define_behavior_input(String texte_defaut){
        inputfield.setForeground(Color.GRAY);
        inputfield.setCaretPosition(0); // position curseur
        inputfield.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (inputfield.getText().equals(texte_defaut)) {
                    inputfield.setText("");
                    inputfield.setForeground(Color.BLACK);
                }
            }
        });
        inputfield.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // curseur au début si aucun mot de passe
                if (inputfield.getText().equals(texte_defaut)){
                    inputfield.setCaretPosition(0);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
            }
        });
    }
    public void define_behavior_password(String texte_defaut){
        passwordField.setCaretPosition(0);
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char)0);
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (Arrays.equals(passwordField.getPassword(), texte_defaut.toCharArray())) {
                    passwordField.setText("");
                    passwordField.setEchoChar('*');
                    passwordField.setForeground(Color.BLACK);
                }
            }
        });
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // curseur au début si aucun mot de passe
                if (Arrays.equals(passwordField.getPassword(), texte_defaut.toCharArray())){
                    passwordField.setCaretPosition(0);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
            }
        });
    }
}