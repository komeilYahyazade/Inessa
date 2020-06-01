package sample;

import java.util.ArrayList;

public class Account {
    private String username ;
    private String password ;
    private String imagePath ;
    private byte[] image ;
    private ArrayList<Post> posts = new ArrayList<>() ;


    public Account(String username, String password){
        this.username = username ;
        this.password = password ;
    }

    public Account(String username, String password,String imagePath){
        this.username = username ;
        this.password = password ;
        this.imagePath = imagePath ;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
