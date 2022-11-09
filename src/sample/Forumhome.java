package sample;

import com.szforums.bean.Answer;
import com.szforums.bean.Query;
import com.szforums.dao.UserDao;
import com.szforums.sessionValidity.sessionReader;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

public class Forumhome {

    @FXML
    private VBox threads_container_vbox;
    private VBox queries_container;
    private boolean pressed = false;
    /*@FXML
    private ScrollPane scroll;*/

    public void gtThreads(){
        threads_container_vbox.getChildren().clear();
        try{
            List<Query> list = UserDao.getAllQueriesRecords();
            if(list.isEmpty()){
                System.out.println("No any queries are posted");
            }
            else {
                for(Query q:list) {
                    String title;
                    String description;
                    Label ltitle = new Label();
                    Label lt = new Label();
                    HBox title_container = new HBox();
                    title = q.getQuery_name();
                    description = q.getQuery_description();
                    lt.setText("Title:");
                    ltitle.setText(title);
                    title_container.getChildren().addAll(lt, ltitle);
                    title_container.setStyle("-fx-spacing: 4");
                    ltitle.setStyle("-fx-background-color: #ff9571");
                    lt.setStyle("-fx-background-color: #ff9571");
                    Text tdescription = new Text(description);
                    VBox ans_Container = new VBox();
                    VBox Query_Description_Container = new VBox();
                    Query_Description_Container.getChildren().add(tdescription);
                    Button getAnswers = new Button("Show Answers");
                    Label Answers = new Label("Answers of " + title);
                    Answers.setStyle("-fx-text-fill: #0011ff;" + "-fx-background-color: #2aff0b");
                    ans_Container.setStyle("-fx-spacing: 4");
                    ans_Container.getChildren().addAll(Answers,getAnswers);
                    getAnswers.setOnAction(actionEvent -> {
                        pressed = true;
                        ans_Container.getChildren().clear();
                        System.out.println("Pressed in " + q.getQuery_name());
                        List<Answer> answers_list = UserDao.getAnswers(q.getQuery_name());
                        if(answers_list.isEmpty()){
                            Label no_ans_found = new Label("No answers are posted for this " + q.getQuery_name());
                            ans_Container.getChildren().addAll(Answers,getAnswers,no_ans_found);
                        }
                        else {
                            int ans_no = 1;
                            for(Answer a: answers_list){
                                Label answer = new Label("Answer " + ans_no + " :");
                                Text answer_description = new Text(a.getAns_content());
                                VBox answer_description_container = new VBox();
                                //answer_description.setEditable(false);
                                answer.setStyle("-fx-background-color: #ff9454;" + "-fx-text-fill: maroon;");
                                answer_description_container.getChildren().addAll(answer,answer_description);
                                answer_description_container.setStyle("-fx-padding: 4;" + "-fx-border-color: gray;" + "-fx-spacing: 4");
                                ans_Container.setStyle("-fx-border-color: yellow;" + "-fx-padding: 4;" + "-fx-spacing: 2");
                                ans_Container.getChildren().addAll(answer_description_container);
                                ans_no ++;
                            }
                        }
                    });
                    TextArea Answer_in = new TextArea();
                    VBox.setVgrow(Answer_in,Priority.ALWAYS);
                    Answer_in.setPromptText("Type your Answer here");
                    Button ans_sender = new Button("Reply");
                    ans_sender.setOnAction(actionEvent -> {
                        Answer a = new Answer();
                        if(Answer_in.getText().isEmpty()){
                            Stage empty_stage = new Stage();
                            empty_stage.initModality(Modality.APPLICATION_MODAL);
                            VBox vb = new VBox();
                            Label empty = new Label("Your answer field is empty");
                            vb.getChildren().add(empty);
                            vb.setStyle("-fx-padding: 4");
                            Scene empty_scene = new Scene(vb);
                            empty_stage.setScene(empty_scene);
                            empty_stage.show();
                        }
                        else {
                            a.setAns_content(Answer_in.getText());
                            a.setAns_query_name(q.getQuery_name());

                            int status = UserDao.sendAnsData(a);
                            if (status > 0) {
                                System.out.println("Answer posted successfully");
                                Answer_in.clear();
                                Answer_in.setPromptText("Type your Answer here");
                                int ans_no = 1;
                                if(pressed == false){
                                    List<Answer> answers_list = UserDao.getAnswers(q.getQuery_name());
                                    for(Answer a1: answers_list) {
                                        ans_Container.getChildren().clear();
                                        Label answer = new Label("Answer " + ans_no + " :");
                                        Text answer_description = new Text(a1.getAns_content());
                                        VBox answer_description_container = new VBox();
                                        //answer_description.setEditable(false);
                                        answer.setStyle("-fx-background-color: #ff9454;" + "-fx-text-fill: maroon;");
                                        answer_description_container.getChildren().addAll(answer,answer_description);
                                        answer_description_container.setStyle("-fx-padding: 4;" + "-fx-border-color: gray;" + "-fx-spacing: 4");
                                        ans_Container.setStyle("-fx-border-color: yellow;" + "-fx-padding: 4;" + "-fx-spacing: 2");
                                        ans_Container.getChildren().addAll(answer_description_container);
                                        ans_no ++;
                                    }
                                }
                                else {
                                    List<Answer> answers_list = UserDao.getAnswers(q.getQuery_name());
                                    for(Answer a2: answers_list) {
                                        ans_Container.getChildren().clear();
                                        Label answer = new Label("Answer " + ans_no + " :");
                                        Text answer_description = new Text(a2.getAns_content());
                                        VBox answer_description_container = new VBox();
                                        answer.setStyle("-fx-background-color: #ff9454;" + "-fx-text-fill: maroon;");
                                        answer_description_container.getChildren().addAll(answer,answer_description);
                                        answer_description_container.setStyle("-fx-padding: 4;" + "-fx-border-color: gray;" + "-fx-spacing: 4");
                                        ans_Container.setStyle("-fx-border-color: yellow;" + "-fx-padding: 4;" + "-fx-spacing: 2");
                                        ans_Container.getChildren().addAll(answer_description_container);
                                        ans_no ++;
                                    }
                                }

                            } else
                                System.out.println("Answer not posted");
                        }
                    });
                    queries_container = new VBox();
                    queries_container.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
                    queries_container.getChildren().addAll(title_container,Query_Description_Container,ans_Container,Answer_in,ans_sender);
                    queries_container.setStyle("-fx-border-color: #1cc7ff;" + "-fx-spacing: 6;" + "-fx-padding: 4;" + "-fx-effect: dropshadow(gaussian,#f5e7e6,1,1.0,0.0,0.0);");
                    threads_container_vbox.getChildren().add(queries_container);
                    Query_Description_Container.setStyle("-fx-padding: 4;");
                    threads_container_vbox.setStyle("-fx-spacing: 10;" + "-fx-background-color: #ffffe0");

                }
            }
        }
        catch (Exception e){
            Stage stageServerOff = new Stage();
            stageServerOff.initModality(Modality.APPLICATION_MODAL);
            VBox label_container = new VBox();
            Label server_status = new Label("Server Offline or Under Maintaining stage Please try to launch app in some time");
            label_container.getChildren().add(server_status);
            label_container.setStyle("-fx-padding: 4,4,4,4");
            Scene sceneServerOff = new Scene(label_container);
            stageServerOff.setScene(sceneServerOff);
        }
    }

    @FXML
    public void setQueries() throws IOException, ParseException {
        threads_container_vbox.getChildren().clear();
        sessionReader read_obj = new sessionReader("login.info");
        TextField titlein = new TextField();
        titlein.setStyle("-fx-background-color: #eeaeff");
        titlein.setPromptText("Title");
        TextArea tdescriptionin = new TextArea();
        tdescriptionin.setStyle("-fx-background-color: #d0ff9e");
        tdescriptionin.setPromptText("Write your query here");
        Button post = new Button("Post");
        Region space = new Region();
        post.setStyle("-fx-background-color: #5aff78");
        HBox space_HBox = new HBox();
        space_HBox.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        HBox.setHgrow(space, Priority.ALWAYS);
        space_HBox.getChildren().addAll(space,post);
        threads_container_vbox.setStyle("-fx-padding: 10,10,10,10;" + "-fx-spacing: 8");
        threads_container_vbox.getChildren().addAll(titlein,tdescriptionin,space_HBox);

        post.setOnAction(actionEvent -> {
            if(titlein.getText().isEmpty() && tdescriptionin.getText().isEmpty()){
                Stage bothEmptyStage = new Stage();
                bothEmptyStage.initModality(Modality.APPLICATION_MODAL);
                Label bothEmpty = new Label("Both title and description of your query is empty please fill it and then click post");
                bothEmpty.setStyle("-fx-background-color: #96baff;" + "-fx-padding: 10,10,10,10;");
                Scene bothEmptyScene = new Scene(bothEmpty);
                bothEmptyStage.setScene(bothEmptyScene);
                bothEmptyStage.setTitle("Alert");
                bothEmptyStage.show();
            }
            else if(titlein.getText().isEmpty()){
                Stage titleEmptyStage = new Stage();
                titleEmptyStage.initModality(Modality.APPLICATION_MODAL);
                Label bothEmpty = new Label("Title of your query is empty its used to represent your query in short words");
                bothEmpty.setStyle("-fx-background-color: #96baff;" + "-fx-padding: 10,10,10,10;");
                Scene titleEmptyScene = new Scene(bothEmpty);
                titleEmptyStage.setScene(titleEmptyScene);
                titleEmptyStage.setTitle("Alert");
                titleEmptyStage.show();
            }
            else if(tdescriptionin.getText().isEmpty()){
                Stage descriptionEmptyStage = new Stage();
                descriptionEmptyStage.initModality(Modality.APPLICATION_MODAL);
                Label bothEmpty = new Label("Description of your query is empty its used to represent your query in brief words");
                bothEmpty.setStyle("-fx-background-color: #96baff;" + "-fx-padding: 10,10,10,10;");
                Scene descriptionEmptyScene = new Scene(bothEmpty);
                descriptionEmptyStage.setScene(descriptionEmptyScene);
                descriptionEmptyStage.setTitle("Alert");
                descriptionEmptyStage.show();
            }
            else{
                String title_in = titlein.getText();
                String description_in = tdescriptionin.getText();
                String query_poster = read_obj.getUsername();
                Query q = new Query();
                q.setQuery_name(title_in);
                q.setQuery_description(description_in);
                q.setQuery_user(query_poster);

                int status = UserDao.sendQueryData(q);
                if(status > 0){
                    System.out.println("Data Inserted Successfully");
                    threads_container_vbox.getChildren().clear();
                    Text query_posted = new Text("Your query has been posted successfully");
                    query_posted.setStyle("-fx-background-color: #3b84ff");
                    Button ok = new Button("ok");
                    ok.setStyle("-fx-background-color: #5aff78");
                    ok.setOnAction(actionEvent1 -> gtThreads());
                    threads_container_vbox.setStyle("-fx-padding: 10,10,10,10");
                    threads_container_vbox.getChildren().addAll(query_posted,ok);

                }
                else{
                    System.out.println("Failed to Insert Data");
                }
            }
        });
    }

}
