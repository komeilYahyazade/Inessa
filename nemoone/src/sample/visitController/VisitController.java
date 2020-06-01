package sample.visitController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sample.Account;
import sample.Client;
import sample.Post;
import sample.mainController.MainController;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class VisitController implements Initializable {
    private static Client currentClient ;
    private static Post currentPost ;


    public static Client getCurrentClient() {
        return currentClient;
    }


    public static void setCurrentClient(Client currentClient) {
        VisitController.currentClient = currentClient;

    }
    @FXML
    private Pane visitPane ;
    @FXML
    private ListView<String> titleList;

    @FXML
    private ImageView targetAvatar;

    @FXML
    private Label targetName;

    @FXML
    private ImageView postImage;

    @FXML
    private Label caption;

    @FXML
    private Label likeAmount;

    @FXML
    private ListView<String> commentList;

    @FXML
    private TextField comment;

    @FXML
    private ImageView avatar;

    public static Post getCurrentPost() {
        return currentPost;
    }

    public static void setCurrentPost(Post currentPost) {
        VisitController.currentPost = currentPost;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        avatar.setImage(new Image(currentClient.getAccount().getImagePath()));
        targetAvatar.setImage(new Image(currentClient.getSelectedAccount().getImagePath()));
        targetName.setText(currentClient.getSelectedAccount().getUsername());
        ArrayList<String> titles = new ArrayList<>();
        for (Post post : currentClient.getSelectedAccount().getPosts()) {
            titles.add(post.getTitle());
        }
        ObservableList<String> postTitles = FXCollections.observableArrayList(titles);
        titleList.setItems(postTitles);
        if (currentClient.getSelectedAccount().getPosts().size()>0){
            Post post = currentClient.getSelectedAccount().getPosts().get(0);
            currentPost = post ;
            postImage.setImage(new Image(post.getImagePath()));
            caption.setText(post.getCaption());
            likeAmount.setText(post.getLikes() + "");
            ObservableList<String> observableComments = FXCollections.observableArrayList(post.getComments());
            commentList.setItems(observableComments);
        }

    }

    public void titleListClick(){
        String name = titleList.getSelectionModel().getSelectedItem() ;
        for (Post post : currentClient.getSelectedAccount().getPosts()) {
            if (name.equals(post.getTitle())){
                currentPost = post ;
                postImage.setImage(new Image(post.getImagePath()));
                caption.setText(post.getCaption());
                likeAmount.setText(post.getLikes() + "");
                ObservableList<String> observableComments = FXCollections.observableArrayList(post.getComments());
                commentList.setItems(observableComments);
                break;
            }
        }
    }

    public void refresh() throws IOException {
        currentClient.refresh();
        for (Account account : currentClient.getAccounts()) {
            if (account.getUsername().equals(currentClient.getSelectedAccount().getUsername())) {
                currentClient.setSelectedAccount(account);
                break;
            }
        }
            for (Post post : currentClient.getSelectedAccount().getPosts()) {
              if ( post.getTitle().equals(currentPost.getTitle())){
                  currentPost = post ;
                  break;
              }
            }
            postImage.setImage(new Image(currentPost.getImagePath()));
            caption.setText(currentPost.getCaption());
            likeAmount.setText(currentPost.getLikes() + "");
            ObservableList<String> observableComments = FXCollections.observableArrayList(currentPost.getComments());
            commentList.setItems(observableComments);

            targetAvatar.setImage(new Image(currentClient.getSelectedAccount().getImagePath()));
            ArrayList<String> titles = new ArrayList<>();
            for (Post post : currentClient.getSelectedAccount().getPosts()) {
                titles.add(post.getTitle());
            }
            ObservableList<String> postTitles = FXCollections.observableArrayList(titles);
            titleList.setItems(postTitles);
        }



    public void sendComment() throws IOException {
        commentList.getItems().add(currentClient.getAccount().getUsername() + " : " + comment.getText()) ;
        currentClient.sendComment(comment.getText(),currentPost.getTitle());
        comment.setText(null);
    }
    public void like() throws IOException {
        int temp = currentPost.getLikes() + 1 ;
        likeAmount.setText(temp+ "");
        currentClient.likePost(currentPost.getTitle());
    }
    public void backHandler() throws IOException {
        MainController.setCurrentClient(currentClient);
        URL url = Paths.get("C:\\Users\\comiran\\IdeaProjects\\nemoone\\src\\sample\\mainController\\mainPage.fxml").toUri().toURL();
        Pane mainPane = FXMLLoader.load(url);
        visitPane.getChildren().setAll(mainPane);
    }



}
