package sample;

import com.szforums.bean.User;
import com.szforums.dao.UserDao;
import com.szforums.sessionValidity.sessionWriter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class Signup {

    @FXML
    private VBox center_vbox;
    @FXML
    private HBox top_hbox;
    @FXML
    private TextField username_in;
    @FXML
    private TextField firstname_in;
    @FXML
    private TextField lastname_in;
    @FXML
    private TextField email_in;
    @FXML
    private TextField occupation_in;
    @FXML
    private PasswordField password_in;
    @FXML
    private PasswordField confirmpassword_in;
    @FXML
    private Button signup_btn;
    @FXML
    private Button login_btn;
    @FXML
    private TextField U_in;
    @FXML
    private PasswordField P_in;

    @FXML
    private void getdata(){
        String username = username_in.getText();
        String firstname = firstname_in.getText();
        String lastname = lastname_in.getText();
        String email = email_in.getText();
        String occupation = occupation_in.getText();
        if(password_in.getText().equals(confirmpassword_in.getText())){
            String password = confirmpassword_in.getText();
            User u = new User();
            u.setUsername(username);
            u.setName(firstname);
            u.setLastname(lastname);
            u.setEmail(email);
            u.setOccupation(occupation);
            u.setPassword(password);

            int status = UserDao.sendData(u);
            if(status > 0){
                System.out.println("Data Inserted Successfully");
                //center_vbox.setVisible(false);
                center_vbox.getChildren().clear();
                Text registered = new Text("You have been successfully registered to SZ forums Now hit login Button to Start communication with peoples");
                center_vbox.getChildren().add(registered);
                top_hbox.getChildren().remove(signup_btn);
                registered.autosize();
            }
            else
                System.out.println("Data Not Inserted");
        }
        else{
            System.out.println("Password not matched");
            Stage pp = new Stage();
            pp.initModality(Modality.APPLICATION_MODAL);
            pp.setTitle("Warning");
            VBox vb = new VBox();
            Label notmatched = new Label("password and confirm password are not matched");
            vb.getChildren().addAll(notmatched);
            vb.setStyle("-fx-padding: 4,4,4,4");
            Scene notmatchedscence = new Scene(vb,300,20);
            pp.setScene(notmatchedscence);
            pp.show();
        }
    }

    @FXML
    public void login() throws IOException {
        String username = U_in.getText();
        String password = P_in.getText();
        System.out.println(username);
        String [] ap = UserDao.getUserRecord(username,password);
        if(username.equals(ap[0]) && password.equals(ap[1])){
            System.out.println("Login Successfully");
            new sessionWriter("login.info",username);
            Parent forumhomeroot = FXMLLoader.load(getClass().getResource("forumhome.fxml"));
            Stage forumhomestage = (Stage) login_btn.getScene().getWindow();
            Scene forumhomescence = new Scene(forumhomeroot,600,400);
            forumhomestage.setScene(forumhomescence);
            forumhomestage.setTitle("Home");
        }
        else {
            System.out.println("Login Failed");
        }
    }

}
