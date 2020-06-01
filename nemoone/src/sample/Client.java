package sample;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private Account account;
    private Account selectedAccount ;
    private ArrayList<Account> accounts = new ArrayList<>();
    public Client(Account account) {
        this.account = account;
    }

    public String SignUp() throws IOException {
        String temp = null ;
        socket = new Socket("localhost", 9000);
        Message message = new Message(Status.NEW_ACCOUNT_SIGN_UP, this.account);
        Message.sendMessage(message,socket);

        Scanner scanner = new Scanner(socket.getInputStream());
        String s = scanner.nextLine();
        YaGson yaGson = new YaGsonBuilder().create();
        message = yaGson.fromJson(s, (Type) Message.class);
        if (message.getStatus() == Status.SEND_ACCOUNTS){
            accounts.addAll(message.getAllAccounts());
        }
        if (message.getStatus() == Status.ERROR){
            temp = message.getErrorMessage() ;

        }
        System.out.println(accounts.size());
        return  temp;
    }

    public String login() throws  IOException{
        String temp = null ;
        socket = new Socket("localhost",9000);
        Message message = new Message(Status.NEW_ACCOUNT_LOGIN, this.account) ;
        Message.sendMessage(message,socket);

        Scanner scanner = new Scanner(socket.getInputStream());
        String s = scanner.nextLine();
        YaGson yaGson = new YaGsonBuilder().create();
        message = yaGson.fromJson(s, (Type) Message.class);
        if (message.getStatus() == Status.SEND_ACCOUNTS){
            this.account = message.getNewAccount() ;
            accounts.addAll(message.getAllAccounts());
        }
        if (message.getStatus() == Status.ERROR){
            temp = message.getErrorMessage() ;

        }
        return temp ;




    }



    public void refresh() throws IOException {
        Message message = new Message(Status.GET_ACCOUNTS) ;
        Message.sendMessage(message, socket);

        Scanner scanner = new Scanner(socket.getInputStream());
        String s = scanner.nextLine() ;

        YaGson yaGson = new YaGsonBuilder().create();
        message = yaGson.fromJson(s, (Type) Message.class);
        accounts.removeAll(accounts);
        accounts.addAll(message.getAllAccounts());
        for (Account account:accounts) {
            if (this.account.getUsername().equals(account.getUsername())){
                this.account = account ;
                break;
            }

        }

    }
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public Account getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(Account selectedAccount) {
        this.selectedAccount = selectedAccount;
    }

    public void sendComment(String commentText, String postTitle) throws IOException {
        Message message = new Message(Status.SEND_COMMENT,this.getSelectedAccount(), postTitle,new String(this.getAccount().getUsername() + " : " + commentText)) ;
        Message.sendMessage(message, socket);
    }

    public void likePost(String postTitle) throws IOException {
        System.out.println(postTitle);
        Message message= new Message(Status.LIKE, this.getSelectedAccount(), postTitle) ;
        Message.sendMessage(message,socket);

    }

    public void addPost(Post post) throws IOException {
        System.out.println(post.getTitle());
        Message message = new Message(Status.POST, post) ;
        Message.sendMessage(message,socket);

        Scanner scanner = new Scanner(socket.getInputStream());
        String s = scanner.nextLine() ;

        YaGson yaGson = new YaGsonBuilder().create();
        message = yaGson.fromJson(s, (Type) Message.class);
        this.setAccount(message.getNewAccount());
    }

    public void changeAvatar(String targetAvatar) throws IOException {
        Message message = new Message(Status.CHANGE_AVATAR,targetAvatar) ;
        Message.sendMessage(message,socket);

        Scanner scanner = new Scanner(socket.getInputStream());
        String s = scanner.nextLine() ;

        YaGson yaGson = new YaGsonBuilder().create();
        message = yaGson.fromJson(s, (Type) Message.class);
        this.setAccount(message.getNewAccount());
    }

    public void changePassword(String passwordText) throws IOException {
        Message message = new Message(Status.CHANGE_PASSWORD, passwordText);
        Message.sendMessage(message, socket);

        Scanner scanner = new Scanner(socket.getInputStream());
        String s = scanner.nextLine() ;

        YaGson yaGson = new YaGsonBuilder().create();
        message = yaGson.fromJson(s, (Type) Message.class);
        this.setAccount(message.getNewAccount());
    }

    public void save() throws IOException {
        Message message = new Message(Status.SAVE) ;
        Message.sendMessage(message,socket);
    }
}
