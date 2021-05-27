package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBModel {

    private Connection conn;
    private PreparedStatement authUser;
    private PreparedStatement getUserById;
    private PreparedStatement getTalks;
    private PreparedStatement getMessages;
    private PreparedStatement getUsers;
    private PreparedStatement addUser;

    public DBModel(Connection conn) throws SQLException {
        this.conn = conn;
        authUser = conn.prepareStatement("SELECT * FROM user WHERE username=? AND password=?");
        getUserById = conn.prepareStatement("SELECT * FROM user WHERE id=?");
        getTalks = conn.prepareStatement("SELECT * FROM talk WHERE user1=? OR user2=?");
        getMessages = conn.prepareStatement("SELECT * FROM message WHERE talkid=?");
        getUsers = conn.prepareStatement("SELECT * FROM user");
        addUser = conn.prepareStatement("INSERT INTO user (username,password,name) VALUES (?,?,?)");
    }

    public User authUser(String userName, String password) throws SQLException {
        authUser.setString(1, userName);
        authUser.setString(2, password);
        ResultSet rs = authUser.executeQuery();
        User user = null;
        if (rs.next()) {
            user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("picture"),
                    rs.getString("name")
            );
        }
        return user;
    }

    public User getUserById(int id) throws SQLException {
        getUserById.setInt(1, id);
        ResultSet rs = getUserById.executeQuery();
        User user = null;
        if (rs.next()) {
            user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("picture"),
                    rs.getString("name")
            );
        }
        return user;
    }

    public Map<Integer, User> getUsersMap() throws SQLException {
        Map<Integer, User> userMap = new HashMap<Integer, User>();
        ResultSet rs = getUsers.executeQuery();
        while (rs.next()) {
            User user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("picture"),
                    rs.getString("name")
            );
            userMap.put(user.getId(), user);
        }
        return userMap;
    }

    public List<Talk> getTalks(User user) throws SQLException {
        getTalks.setInt(1, user.getId());
        getTalks.setInt(2, user.getId());
        ResultSet rs = getTalks.executeQuery();
        List<Talk> talks = new ArrayList<Talk>();
        Map<Integer, User> userMap = getUsersMap();

        while (rs.next()) {
            int id = rs.getInt("id");
            Date startTime = rs.getTimestamp("starttime");
            User user1 = userMap.get(rs.getInt("user1"));
            User user2 = userMap.get(rs.getInt("user2"));

            Talk talk = new Talk(id, startTime, user1, user2);
            talks.add(talk);

        }
        return talks;
    }

    public List<Message> getMessages(int talkId) throws SQLException {
        getMessages.setInt(1, talkId);
        ResultSet rs = getMessages.executeQuery();
        List<Message> messages = new ArrayList<>();
        Map<Integer, User> userMap = getUsersMap();
        while (rs.next()) {
            int id = rs.getInt("id");
            User sender = userMap.get(rs.getInt("senderid"));
            int tId = rs.getInt("talkid");
            Date date = rs.getTimestamp("date");
            String text = rs.getString("text");

            Message mes = new Message(id, sender, tId, date, text);
            messages.add(mes);
        }
        return messages;

    }

    public int addUser(User user) throws SQLException {
        addUser.setString(1, user.getUserName());
        addUser.setString(2, user.getPassword());
        addUser.setString(3, user.getName());
        return addUser.executeUpdate(); //mennyi sort érintett a művelet
    }

}
