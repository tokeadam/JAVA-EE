package model;

import java.io.Serializable;


public class User implements Serializable{
    private int id;
    private String userName;
    private String password;
    private String picture;
    private String name;

    public User() {
    }

    public User(int id, String userName, String password, String picture, String name) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.picture = picture;
        this.name = name;
    }

    public User(String userName, String password, String picture, String name) {
        this.userName = userName;
        this.password = password;
        this.picture = picture;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
