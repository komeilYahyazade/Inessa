package sample.loginController;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import sample.Account;
import sample.Client;
import sample.mainController.MainController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

public class LoginController {

    @FXML
    private Pane loginPane ;

    @FXML
    private Label msg;

    @FXML
    private TextField signUpUsername;
    @FXML
    private PasswordField signUpPassword;
    @FXML
    private TextField loginUsername;
    @FXML
    private PasswordField loginPassword;
    @FXML
    private Label msg2;
    @FXML
    private ImageView avatar;

    private String imagePath;


    public void signUp() throws IOException {
        String userName = signUpUsername.getText();
        String passWord = signUpPassword.getText();
        Account account = new Account(userName, passWord, imagePath);
        Client client = new Client(account);
        try {
            String s = client.SignUp();
            if (s != null) {
                signUpPassword.setText(null);
                signUpUsername.setText(null);
                msg.setText(s);
            }else{
                MainController.setCurrentClient(client);
                URL url = Paths.get("C:\\Users\\comiran\\IdeaProjects\\nemoone\\src\\sample\\mainController\\mainPage.fxml").toUri().toURL();
                Pane mainPane = FXMLLoader.load(url);
                loginPane.getChildren().setAll(mainPane);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void exitHandler(){
        Platform.exit();
    }

    public void chooseImage() throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users\\comiran\\IdeaProjects\\nemoone\\src\\img"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image files", "*.jpg"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            imagePath = file.toURI().toURL().toExternalForm();
            avatar.setImage(new Image(imagePath));

        }

    }



    public void login() {
     //   msg2.setText("here");
        String userName = loginUsername.getText();
        String passWord = loginPassword.getText();
        Account account = new Account(userName, passWord);
        Client client = new Client(account);
        try {
            String s = client.login();
            if (s != null) {
                msg2.setText(s);
            }else{
                MainController.setCurrentClient(client);
                URL url = Paths.get("C:\\Users\\comiran\\IdeaProjects\\nemoone\\src\\sample\\mainController\\mainPage.fxml").toUri().toURL();
                Pane mainPane = FXMLLoader.load(url);
                loginPane.getChildren().setAll(mainPane);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
