package com.football.DataBase;

import com.football.Domain.Users.AssociationDelegate;
import com.football.Exception.ObjectNotExist;
import com.football.Service.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

@Repository
public class NotificationDao implements DAO<Notification> {

    @Autowired
    public DBConnector dbc=new DBConnector();

    public NotificationDao() {
    }

    //  private static AssociationDelegateDao instance = new AssociationDelegateDao();

    //private constructor to avoid client applications to use constructor
    // public static AssociationDelegateDao getInstance(){
    //    return instance;
    // }

    Connection connection=dbc.getConnection();

    @Override
    public String getTableName() {
        return " notification ";
    }


    @Override
    public String get(String id) {
        String gameIdFromUser=id.split(":")[0];
        String notificationIdFromUser=id.split(":")[1];
        String toReturn="";
        try {
            String sqlQuery = "SELECT * From "+getTableName()+" WHERE gameId="+"\'"+gameIdFromUser+"\'"+"AND notificationID="+"\'"+notificationIdFromUser+"\'"+";";

            PreparedStatement ps = connection.prepareStatement(sqlQuery); //compiling query in the DB
            ResultSet rs=ps.executeQuery();

            if(rs.next()) {
                String gameId = rs.getString("gameId");
                String notificationID = rs.getString("notificationID");
                String registerMembers = rs.getString("registerMembers");

                toReturn = gameId + ":" + notificationID + ":" + registerMembers;
            }
            rs.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
        return toReturn;
    }

    @Override
    public List<String> getAll() {
        LinkedList<String> allTheTable = new LinkedList<>();
        try {
            String sqlQuery = "SELECT * From " + getTableName()+ ";";

            PreparedStatement ps = connection.prepareStatement(sqlQuery); //compiling query in the DB
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String gameId = rs.getString("gameId");
                String notificationID = rs.getString("notificationID");
                String registerMembers = rs.getString("registerMembers");

                String toReturn = gameId + ":" + notificationID + ":" + registerMembers;
                allTheTable.add(toReturn);
            }
            rs.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
        return allTheTable;
    }


    @Override
    public void save(Notification notification){
        try {
            Statement stmt = connection.createStatement();

            String sql = "INSERT INTO"+getTableName()+
                    "VALUES ("+notification.toString()+");";
            stmt.executeUpdate(sql);
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
    }


    @Override
    public void update(String id , Notification notification) throws ObjectNotExist {
            //delete and than add new one
            delete(id);
            save(notification);
        }


    @Override
    public void delete(String id) {
        String gameIdFromUser=id.split(":")[0];
        String notificationIdFromUser=id.split(":")[1];
        try {
            Statement stmt = connection.createStatement();

            String sql = "DELETE FROM"+getTableName()+
                    "WHERE gameId="+"\'"+gameIdFromUser+"\'"+"AND notificationID="+"\'"+notificationIdFromUser+"\'"+";";
            stmt.executeUpdate(sql);
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
    }


    @Override
    public boolean exist(String id) {
        String gameIdFromUser=id.split(":")[0];
        String notificationIdFromUser=id.split(":")[1];
        try {
            Statement stmt = connection.createStatement();

            String sqlQuery = "SELECT * FROM"+getTableName()+
                    "WHERE gameId="+"\'"+gameIdFromUser+"\'"+"AND notificationID="+"\'"+notificationIdFromUser+"\'"+";";
            ResultSet rs = stmt.executeQuery(sqlQuery);
            return rs.next();

        } catch (java.sql.SQLException e) {
             System.out.println(e.toString());
        }
        return false;
    }
}