package testui;

import db.DatabaseDiscussion;
import db.DatabaseUsers;
import ui.LoginInterface;

public class TestLoginInterface extends LoginInterface {

    public TestLoginInterface(String framename, DatabaseUsers users_db, DatabaseDiscussion disc_db) {
        super(framename, users_db, disc_db);
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