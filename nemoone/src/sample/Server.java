package sample;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Formatter;

public class Server extends Thread {

    private ServerSocket serverSocket = new ServerSocket(9000);
    private static ArrayList<ClientHandler> allClients = new ArrayList<>();
    private static ArrayList<Account> accounts = new ArrayList<>();

    public static ArrayList<Account> getAccounts() {
        return accounts;
    }

    public Server() throws IOException {

    }

    @Override
    public void run() {

        System.out.println("Server");
        Server.getJson();

        Socket socket;
        while (true) {
            System.out.println("Server is ready to get Request...");
            try {
                socket = serverSocket.accept();

                System.out.println("new server send request to connect!");

                System.out.println("client " + "" + " connected with socket: " + socket);


                ClientHandler clientHandler = new ClientHandler(socket);
                allClients.add(clientHandler);
                Thread t = new Thread(clientHandler);
                t.start();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    public static ArrayList<ClientHandler> getAllClients() {
        return allClients;
    }

    public static void toJson() {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("C:\\Users\\comiran\\IdeaProjects\\nemoone\\src\\json\\account.json");
            YaGson gson = new YaGson();
            String z = gson.toJson(Server.getAccounts());
            fileWriter.write(z);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void getJson() {
        YaGson yaGson = new YaGson();
        try {
            Account[] accounts = yaGson.fromJson(new FileReader("C:\\Users\\comiran\\IdeaProjects\\nemoone\\src\\json\\account.json"), Account[].class);
            if (accounts != null) {
                for (int i = 0; i < accounts.length; i++) {
                    Server.getAccounts().add(accounts[i]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
