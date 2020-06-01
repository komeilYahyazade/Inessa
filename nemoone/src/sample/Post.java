package sample;

import java.util.ArrayList;

public class Post {
    private String caption;
    private String title ;
    private String imagePath ;
    private int likes = 0 ;
    private ArrayList<String> comments = new ArrayList<>();

    public Post(String title, String caption , String imagePath){
            this.title = title ;
            this.caption = caption ;
            this.imagePath = imagePath ;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getComments() {
        return comments;
    }
}
