package sample;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    private InputStream inputStream;
    private OutputStream outputStream;
    private String name;
    private Socket socket;

    public ClientHandler(Socket socket) throws IOException, ClassNotFoundException {
        this.socket = socket;
        inputStream = this.socket.getInputStream();
        outputStream = this.socket.getOutputStream();
        Scanner scanner = new Scanner(inputStream);
        String s = scanner.nextLine();
        YaGson yaGson = new YaGsonBuilder().create();
        Message message = yaGson.fromJson(s, (Type) Message.class);
        Account newAccount;
        if (message.getStatus() == Status.NEW_ACCOUNT_SIGN_UP) {
            newAccount = message.getNewAccount();
            int flagOfExistence = 0;
            for (Account account : Server.getAccounts()) {
                if (account.getUsername().equals(newAccount.getUsername())) {
                    flagOfExistence = 1;
                }
            }
            if (flagOfExistence == 0) {
                Server.getAccounts().add(newAccount);
                this.name = newAccount.getUsername();
                System.out.println("name: " + this.name);
                Message message1 = new Message(Status.SEND_ACCOUNTS,Server.getAccounts());
                String all = yaGson.toJson(message1);
                Formatter formatter = new Formatter(outputStream);
                formatter.format("%s\n", all);
                formatter.flush();
            } else{
                Message message1 = new Message(Status.ERROR, "already exists") ;
                String all = yaGson.toJson(message1);
                Formatter formatter = new Formatter(outputStream);
                formatter.format("%s\n", all);
                formatter.flush();
            }

        }else if (message.getStatus() == Status.NEW_ACCOUNT_LOGIN){
            newAccount = message.getNewAccount();
            int flagOfExistence = 0;
            for (Account account : Server.getAccounts()) {
                if (account.getUsername().equals(newAccount.getUsername())) {
                    if (account.getPassword().equals(newAccount.getPassword())){
                        flagOfExistence = 1;
                        this.name = newAccount.getUsername();
                        System.out.println("name: " + this.name);
                        Message message1 = new Message(Status.SEND_ACCOUNTS,Server.getAccounts(), account);
                        String all = yaGson.toJson(message1);
                        Formatter formatter = new Formatter(outputStream);
                        formatter.format("%s\n", all);
                        formatter.flush();


                    }else {
                        flagOfExistence = 1;
                        Message message1 = new Message(Status.ERROR, "wrong password");
                        String all = yaGson.toJson(message1);
                        Formatter formatter = new Formatter(outputStream);
                        formatter.format("%s\n", all);
                        formatter.flush();
                    }

                }
            }
            if (flagOfExistence == 0){
                Message message1 = new Message(Status.ERROR, "no such account");
                String all = yaGson.toJson(message1);
                Formatter formatter = new Formatter(outputStream);
                formatter.format("%s\n", all);
                formatter.flush();
            }

        }

    }

    @Override
    public void run() {
        System.out.println("client started :D");

        while (true) {
            try {
                InputStream inputStream = socket.getInputStream();
                Scanner scanner = new Scanner(inputStream);
                String s = scanner.nextLine();
                YaGson yaGson = new YaGsonBuilder().create();
                Message message = yaGson.fromJson(s, (Type) Message.class);
                if (message.getStatus() == Status.GET_ACCOUNTS){
                    Message message1 = new Message(Status.SEND_ACCOUNTS, Server.getAccounts());
                    String all = yaGson.toJson(message1) ;
                    Formatter formatter = new Formatter(outputStream);
                    formatter.format("%s\n", all);
                    formatter.flush();
                }
                else if (message.getStatus() == Status.SEND_COMMENT){
                    for (Account account:Server.getAccounts()) {
                        if (account.getUsername().equals(message.getNewAccount().getUsername())){
                            for (Post post : account.getPosts()) {
                                if (post.getTitle().equals(message.getPostTitle())){
                                    post.getComments().add(message.getComment());
                                    break;
                                }
                            }
                        }
                    }


                }else if(message.getStatus() == Status.LIKE){
                    System.out.println("im here");
                    System.out.println(message.getPostTitle());
                    for (Account account : Server.getAccounts()) {
                        if (account.getUsername().equals(message.getNewAccount().getUsername())){
                            System.out.println("im here 3");
                            for (Post post : account.getPosts()) {
                                if (post.getTitle().equals(message.getPostTitle())){
                                    System.out.println("im here 2");
                                    int temp = post.getLikes() + 1 ;
                                    post.setLikes(temp);
                                    break;
                                }
                            }
                        }
                    }
                }else if (message.getStatus() == Status.POST){
                    for (Account account:Server.getAccounts()) {
                        if (account.getUsername().equals(this.name)){
                            account.getPosts().add(message.getPost());
                            Message message1 = new Message(account);
                            String all = yaGson.toJson(message1) ;
                            Formatter formatter = new Formatter(outputStream);
                            formatter.format("%s\n", all);
                            formatter.flush();
                        }
                    }
                }else if (message.getStatus() == Status.CHANGE_AVATAR){
                    for (Account account : Server.getAccounts()) {
                        if (account.getUsername().equals(this.name)){
                            account.setImagePath(message.getImagePath());
                            Message message1 = new Message(account);
                            String all = yaGson.toJson(message1) ;
                            Formatter formatter = new Formatter(outputStream);
                            formatter.format("%s\n", all);
                            formatter.flush();
                        }
                    }
                }else if (message.getStatus() == Status.CHANGE_PASSWORD){
                    for (Account account : Server.getAccounts()){
                        if (account.getUsername().equals(this.name)){
                            account.setPassword(message.getPassWord());
                            Message message1 = new Message(account);
                            String all = yaGson.toJson(message1) ;
                            Formatter formatter = new Formatter(outputStream);
                            formatter.format("%s\n", all);
                            formatter.flush();
                        }

                    }
                }else if (message.getStatus() == Status.SAVE){
                    Server.toJson();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public String getName() {
        return name;
    }

    public Socket getSocket() {
        return socket;
    }
}
