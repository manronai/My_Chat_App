package com.example.mychatapp.Model;

public class Users {


    String username, imageURL, id, status, email;

    public Users(String username, String imageURL, String id, String status, String email) {
        this.username = username;
        this.imageURL = imageURL;
        this.id = id;
        this.status = status;
        this.email = email;
    }

    public Users() {
    }

    public  String getEmail(){return email; }
    public  void setEmail(String email){ this.email = email;}
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
