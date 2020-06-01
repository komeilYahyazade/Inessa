package sample.profileController;

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
import javafx.stage.FileChooser;
import sample.Client;
import sample.Post;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    private static Client currentClient ;
    private static Post currentPost ;
    private static Post addedPost ;
    private String postImagePath ;

    public static Client getCurrentClient() {
        return currentClient;
    }

    public static void setCurrentClient(Client currentClient) {
        ProfileController.currentClient = currentClient;
    }

    @FXML
    private ImageView avatar;

    @FXML
    private ListView<String> postList;

    @FXML
    private ImageView postImage;

    @FXML
    private Label likeAmount;

    @FXML
    private Label postCaption;

    @FXML
    private ImageView addedPostImage;

    @FXML
    private TextField addedCaption;
    @FXML
    private Pane profilePane ;
    @FXML
    private Label comment ;
    @FXML
    private ListView<String> commentList ;
    @FXML
    private TextField addedPostTitle ;
    @FXML
    private TextField password ;

    public static Post getCurrentPost() {
        return currentPost;
    }

    public static void setCurrentPost(Post currentPost) {
        ProfileController.currentPost = currentPost;
    }

    public static Post getAddedPost() {
        return addedPost;
    }

    public static void setAddedPost(Post addedPost) {
        ProfileController.addedPost = addedPost;
    }

    public void backHandler() throws IOException {
        URL url = Paths.get("C:\\Users\\comiran\\IdeaProjects\\nemoone\\src\\sample\\mainController\\mainPage.fxml").toUri().toURL();
        Pane mainPane = FXMLLoader.load(url);
        profilePane.getChildren().setAll(mainPane);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        avatar.setImage(new Image(currentClient.getAccount().getImagePath()));
        ArrayList<String> titles = new ArrayList<>();
        for (Post post : currentClient.getAccount().getPosts()) {
            titles.add(post.getTitle());
        }
        ObservableList<String> postTitles = FXCollections.observableArrayList(titles);
        postList.setItems(postTitles);
        if (currentClient.getAccount().getPosts().size()>0){
            Post post = currentClient.getAccount().getPosts().get(0);
            currentPost = post ;

        }


    }
    public  void  titleClick() throws IOException {
            currentClient.refresh();
        String name = postList.getSelectionModel().getSelectedItem() ;
        for (Post post : currentClient.getAccount().getPosts()){
            if (post.getTitle().equals(name)){
                currentPost = post ;
                showPost();
            }
            
        }
    }
    public void showPost(){
        postImage.setImage(new Image(currentPost.getImagePath()));
        postCaption.setText(currentPost.getCaption());
        likeAmount.setText("Likes : " + currentPost.getLikes());
        comment.setText("Comments :");
        ObservableList<String> observableComments = FXCollections.observableArrayList(currentPost.getComments());
        commentList.setItems(observableComments);
    }

    public void chooseImage() throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users\\comiran\\IdeaProjects\\nemoone\\src\\img"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image files", "*.jpg"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            postImagePath = file.toURI().toURL().toExternalForm();
            addedPostImage.setImage(new Image(postImagePath));
        }
    }
    public void addPost() throws IOException {
        Post post = new Post(addedPostTitle.getText(),addedCaption.getText(),postImagePath);
        currentClient.addPost(post);
        addedPostImage.setImage(null);
        addedPostTitle.setText(null);
        addedCaption.setText(null);
        currentPost = currentClient.getAccount().getPosts().get(currentClient.getAccount().getPosts().size()-1);
        ArrayList<String> titles = new ArrayList<>();
        for (Post post1 : currentClient.getAccount().getPosts()) {
            titles.add(post1.getTitle());
        }
        ObservableList<String> postTitles = FXCollections.observableArrayList(titles);
        postList.setItems(postTitles);
        showPost();
    }

    public void  changePic() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users\\comiran\\IdeaProjects\\nemoone\\src\\img"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image files", "*.jpg"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String targetAvatar = file.toURI().toURL().toExternalForm();
            currentClient.changeAvatar(targetAvatar);
            avatar.setImage(new Image(targetAvatar));
        }

    }
    public void changePassword() throws IOException {
        if (password.getText() != null) {
            currentClient.changePassword(password.getText());
            password.setText(null);
        }
    }
}