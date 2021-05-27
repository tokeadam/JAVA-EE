package model;

import java.io.Serializable;
import java.util.Date;


public class Talk implements Serializable{
    private int id;
    private Date startTime;
    private User user1;
    private User user2;

    public Talk() {
    }

    public Talk(int id, Date startTime, User user1, User user2) {
        this.id = id;
        this.startTime = startTime;
        this.user1 = user1;
        this.user2 = user2;
    }

    public Talk(Date startTime, User user1, User user2) {
        this.startTime = startTime;
        this.user1 = user1;
        this.user2 = user2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }
    
    
}
