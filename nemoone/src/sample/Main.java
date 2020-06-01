package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("loginController/login.fxml"));
        primaryStage.setTitle("Inessa");
        primaryStage.setScene(new Scene(root, 1000, 1000));
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream("isServer.txt");
        byte[] bytes = new byte[100];
        inputStream.read(bytes);
        inputStream.close();
        if (bytes[0] == 49) {
            new FileOutputStream("isServer.txt").write(48);
            new Server().start();
        } else {
            launch(args);
        }
    }
}
