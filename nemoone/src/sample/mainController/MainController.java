package sample.mainController;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import sample.Account;
import sample.Client;
import sample.profileController.ProfileController;
import sample.visitController.VisitController;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private static Client currentClient = null ;


    @FXML
    private Pane mainPane ;

    @FXML
    private ListView<String> accountList ;
    @FXML
    private ImageView avatar ;

    public static Client getCurrentClient() {
        return currentClient;
    }

    public static void setCurrentClient(Client currentClient) {
        MainController.currentClient = currentClient;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            refreshAction();
        } catch (IOException e) {
            e.printStackTrace();
        }
        avatar.setImage(new Image(currentClient.getAccount().getImagePath()));
    }

    public void exitHandler() throws IOException {
        currentClient.save();
        Platform.exit();
    }


    public void chooseImageToSend() throws IOException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        byte[] imageTemp = new byte[(int) file.length()] ;
        if (file != null) {
            FileInputStream fis = new FileInputStream(file);
            fis.read(imageTemp);
            fis.close();
        }
        String gi = "gi" ;
        String path = new String("C:\\Users\\comiran\\IdeaProjects\\nemoone\\"+"src\\serverImage\\"+ gi+".jpg");
        File file1 = new File(path);
        OutputStream outputStream = new FileOutputStream(file1);
        outputStream.write(imageTemp);
        outputStream.close();
        String serverImage = file1.toURI().toURL().toExternalForm();
        avatar.setImage(new Image(serverImage));
    }

    public void listClick() throws IOException {
        String targetAccountName = accountList.getSelectionModel().getSelectedItem() ;
        Account targetAccount = null;
        for (Account temp:currentClient.getAccounts()) {
            if (temp.getUsername().equals(targetAccountName)){
                targetAccount = temp ;
                break;
            }
        }
        VisitController.setCurrentClient(currentClient);
        currentClient.setSelectedAccount(targetAccount);
        URL url = Paths.get("C:\\Users\\comiran\\IdeaProjects\\nemoone\\src\\sample\\visitController\\visitPage.fxml").toUri().toURL();
        Pane visitPane = FXMLLoader.load(url);
        mainPane.getChildren().setAll(visitPane);

    }

    public void refreshAction() throws IOException {
        accountList.getItems().removeAll(accountList.getItems());
        currentClient.refresh();
        ArrayList<String> accountNames = new ArrayList<>();
        for (Account account:currentClient.getAccounts()) {
            if (account.getUsername().equals(currentClient.getAccount().getUsername()))
                continue;
            accountNames.add(account.getUsername());
        }
        ObservableList<String> userNames = FXCollections.observableArrayList(accountNames);
        accountList.setItems(userNames);
    }

    public void profileHandler() throws IOException {
        ProfileController.setCurrentClient(currentClient);
        URL url = Paths.get("C:\\Users\\comiran\\IdeaProjects\\nemoone\\src\\sample\\profileController\\profilePage.fxml").toUri().toURL();
        Pane profilePane = FXMLLoader.load(url);
        mainPane.getChildren().setAll(profilePane);
    }
}
