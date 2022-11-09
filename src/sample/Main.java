package sample;

import com.szforums.dao.UserDao;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        if(UserDao.getConTester() == null){
            Stage stageSeverOff = new Stage();
            VBox label_container = new VBox(new Label("Server is Offline or currently Under maintaining stage So please try to login again in few time !"));
            label_container.setStyle("-fx-padding: 4,4,4,4");
            Scene sceneServerOff = new Scene(label_container);
            stageSeverOff.setScene(sceneServerOff);
            stageSeverOff.setTitle("SZ forums unavailable");
            stageSeverOff.show();
        }
        else {
            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            primaryStage.setTitle("Login");
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.show();

        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
