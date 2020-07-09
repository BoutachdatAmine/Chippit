package be.ehb.project.chippit.db;


import be.ehb.project.chippit.db.PersonDAO;
import javafx.event.ActionEvent;
import org.mindrot.jbcrypt.BCrypt;

public class LoginDAO {
    public LoginDAO(){}

    //Check a user's password using BCrypt
    public boolean checkPassword(String email, String input){
        PersonDAO personDAO = new PersonDAO();
        String passwordFromDb = personDAO.getUserByEMail(email).getPassword();
        if (BCrypt.checkpw(input, passwordFromDb))
            return true;
        else
            return false;
    }
}
