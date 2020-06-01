package sample;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Formatter;

public class Message {
    private Status status;
    private ArrayList<Account> allAccounts;
    private Account newAccount;
    private Post post;
    private String errorMessage;
    private String postTitle ;
    private String comment ;
    private String imagePath ;
    private String passWord ;
    private byte[] image ;

    public Message(Status status, byte[] image){
        this.status = status ;
        this.image = image ;
    }

    public Message(Account account){
        this.newAccount = account ;
    }

    public Message(Status status, Post post) {
        this.status = status;
        this.post = post;
    }
    public Message(Status status){
        this.status = status ;
    }


    public Message(Status status, Account targetAccount, String postTitle, String comment){
        this.status = status ;
        this.newAccount = targetAccount ;
        this.postTitle = postTitle ;
        this.comment = comment ;
    }

    public Message(Status status, Account newAccount) {
        this.status = status;
        this.newAccount = newAccount;
    }

    public Message(Status status, ArrayList<Account> allAccounts) {
        this.status = status;
        this.allAccounts = allAccounts;
    }
    public Message(Status status, ArrayList<Account> allAccounts, Account account) {
        this.status = status;
        this.allAccounts = allAccounts;
        this.newAccount = account ;
    }

    public Message(Status status, String string) {
        this.status = status;
        if (this.status == Status.ERROR)
        this.errorMessage = string;
        if (this.status == Status.CHANGE_AVATAR)
            this.imagePath = string ;
        if (this.status == Status.CHANGE_PASSWORD)
            this.passWord= string ;
        }
    public  Message(Status status , Account newAccount, String postTitle){
        this.status = status ;
        this.newAccount = newAccount ;
        this.postTitle = postTitle ;
    }

    public static void sendMessage(Message message, Socket socket) throws IOException {
        YaGson yaGson = new YaGsonBuilder().create();
        String s = yaGson.toJson(message);
        OutputStream outputStream = socket.getOutputStream();
        Formatter formatter = new Formatter(outputStream);
        formatter.format("%s\n", s);
        formatter.flush();
    }


    public ArrayList<Account> getAllAccounts() {
        return allAccounts;
    }

    public void setAllAccounts(ArrayList<Account> allAccounts) {
        this.allAccounts = allAccounts;
    }

    public Account getNewAccount() {
        return newAccount;
    }

    public void setNewAccount(Account newAccount) {
        this.newAccount = newAccount;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}

enum Status {
    POST, SEND_ACCOUNTS, NEW_ACCOUNT_SIGN_UP, ERROR, NEW_ACCOUNT_LOGIN, GET_ACCOUNTS,SEND_COMMENT,LIKE,CHANGE_AVATAR,
    CHANGE_PASSWORD, SAVE, SEND_IMAGE ;
}
