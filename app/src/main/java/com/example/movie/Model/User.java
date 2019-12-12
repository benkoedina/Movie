package com.example.movie.Model;

public class User {
    private int user_id;
    private String name;
    private String password;
    private String image_path;

    public  User(){};

    public User(int user_id, String name, String password, String image_path) {
        this.user_id = user_id;
        this.name = name;
        this.password = password;
        this.image_path = image_path;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}
