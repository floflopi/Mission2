package testui;

import actions.Actions;
import db.DatabaseDiscussion;
import db.DatabaseUsers;
import ui.LoginInterface;

public class TestLoginInterface extends LoginInterface {

    public TestLoginInterface(String framename, DatabaseUsers users_db, DatabaseDiscussion disc_db, Actions actions) {
        super(framename, users_db, disc_db, actions);
    }

    public void setDefaultValues(String defaultUsername, String defaultPassword) {
        usernameField.getinput().setText(defaultUsername);
        passwordField.getpassword().setText(defaultPassword);
    }

    public void closeWindow() {
        this.frame = super.getFrame();
        frame.dispose();
    }
}