package sample;


import com.szforums.dao.UserDao;
import com.szforums.sessionValidity.sessionWriter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    @FXML
    private Button login_btn;
    @FXML
    private Button signup_btn;
    @FXML
    private TextField username_in;
    @FXML
    private TextField password_in;

    @FXML
    public void calldatabase() throws IOException {
        String username = username_in.getText();
        String password = password_in.getText();
        String [] ap = UserDao.getUserRecord(username,password);
        if(username.equals(ap[0]) && password.equals(ap[1])){
            System.out.println("Login Successfully");
            new sessionWriter("login.info",username);
            Parent forumhomeroot = FXMLLoader.load(getClass().getResource("forumhome.fxml"));
            Stage forumhomestage = (Stage) login_btn.getScene().getWindow();
            Scene forumhomescence = new Scene(forumhomeroot,800,600);
            forumhomestage.setScene(forumhomescence);
            forumhomestage.setTitle("Home");
        }
        else {
            System.out.println("Login Failed");
        }
    }

    @FXML
    private void loadSecond() throws IOException {
        Parent signuproot = FXMLLoader.load(getClass().getResource("signup.fxml"));
        Stage stage = (Stage) signup_btn.getScene().getWindow();
        Scene scene = new Scene(signuproot,800,600);
        stage.setScene(scene);
        stage.setTitle("Sign up");
        System.out.println("Login window closed");
    }
}
