package com.xuxe.falconHeavy.database.points;

import com.xuxe.falconHeavy.database.ConnectionManager;
import com.xuxe.falconHeavy.database.framework.DBChecks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class Points {
    private Connection connection;

    public Points() {
        connection = ConnectionManager.getConnection();
    }

    public void addPoints(String id, int count) {
        try {

            if (!DBChecks.userExists(id)) {
                DBChecks.makeUser(id);
            } else {
                PreparedStatement statement = connection.prepareStatement("update users set points=points+? where uid = ?");
                statement.setInt(1, count);
                statement.setString(2, id);
                statement.execute();
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removePoints(String id, int count) {
        try {

            if (!connection.prepareStatement("select * from users where uid=" + id).executeQuery().first()) {
                PreparedStatement statement = connection.prepareStatement("insert into users(uid,points) values( ?, ?)");
                statement.setString(1, id);
                statement.setInt(2, count);
                statement.execute();
            } else {
                PreparedStatement statement = connection.prepareStatement("update users set points=points-? where uid = ?");
                statement.setInt(1, count);
                statement.setString(2, id);
                statement.execute();
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int countPoints(String id) {
        try {
            PreparedStatement statement = connection.prepareStatement("select points from users where uid = ?");
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.first())
                return resultSet.getInt("points");
            resultSet.close();
            statement.close();
        } catch (SQLException sql) {
            sql.printStackTrace();
            return -1;
        }
        return 0;
    }

    public void setPoints(String id, int count) {
        try {

            if (connection.prepareStatement("select uid from users where uid='" + id + "'").executeQuery() == null) {

                PreparedStatement statement = connection.prepareStatement("insert into users(uid,points) values(?, ?)");
                statement.setString(1, id);
                statement.setInt(2, count);
                statement.execute();
                statement.close();
            } else {
                PreparedStatement statement = connection.prepareStatement("update users set points = ? where uid = ?");
                statement.setInt(1, count);
                statement.setString(2, id);
                statement.execute();
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getTopPoints(int count) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from users order by points DESC LIMIT ? ");
            statement.setInt(1, count);
            return statement.executeQuery();
        } catch (Exception e) {
            return null;
        }
    }
}
