package model;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable{
    private int id;
    private User sender;
    private int talkId;
    private Date date;
    private String text;

    public Message() {
    }

    public Message(int id, User sender, int talkId, Date date, String text) {
        this.id = id;
        this.sender = sender;
        this.talkId = talkId;
        this.date = date;
        this.text = text;
    }

    public Message(User sender, int talkId, Date date, String text) {
        this.sender = sender;
        this.talkId = talkId;
        this.date = date;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public int getTalkId() {
        return talkId;
    }

    public void setTalkId(int talkId) {
        this.talkId = talkId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    
}
