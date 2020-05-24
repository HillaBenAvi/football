package com.football.DataBase;

import com.football.Domain.Game.EventLog;
import com.football.Domain.Users.AssociationDelegate;
import com.football.Exception.ObjectNotExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

@Repository
public class EventLogDao implements DAO<EventLog> {

    @Autowired
    public DBConnector dbc=new DBConnector();

    public EventLogDao() {
    }

    //  private static AssociationDelegateDao instance = new AssociationDelegateDao();

    //private constructor to avoid client applications to use constructor
    // public static AssociationDelegateDao getInstance(){
    //    return instance;
    // }

    Connection connection=dbc.getConnection();

    @Override
    public String getTableName() {
        return " eventLog ";
    }





    @Override
    public String get(String id) {
        String toReturn="";
        try {
            String sqlQuery = "SELECT * From "+getTableName()+" WHERE id="+"\'"+id+"\'"+";";

            PreparedStatement ps = connection.prepareStatement(sqlQuery); //compiling query in the DB
            ResultSet rs=ps.executeQuery();

            if(rs.next()) {
                String ID = rs.getString("id");
                String timeStamp = rs.getString("timeStamp");
                String userId = rs.getString("userId");
                String actionName = rs.getString("actionName");

                toReturn = ID + ":" + timeStamp + ":" + userId + ":" + actionName;
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
                String ID = rs.getString("id");
                String timeStamp = rs.getString("timeStamp");
                String userId = rs.getString("userId");
                String actionName = rs.getString("actionName");

                String toReturn = ID + ":" + timeStamp + ":" + userId + ":" + actionName;
                allTheTable.add(toReturn);
            }
            rs.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
        return allTheTable;
    }


    @Override
    public void save(EventLog eventLog){
        try {
            Statement stmt = connection.createStatement();

            String sql = "INSERT INTO"+getTableName()+
                    "VALUES ("+eventLog.toString()+");";
            stmt.executeUpdate(sql);
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
    }


    @Override
    public void update(String id , EventLog eventLog) throws ObjectNotExist {
            //delete and than add new one
            delete(id);
            save(eventLog);
    }

    @Override
    public void delete(String userMail) {
        try {
            Statement stmt = connection.createStatement();

            String sql = "DELETE FROM"+getTableName()+
                    "WHERE id ="+"\'"+userMail+"\'";
            stmt.executeUpdate(sql);
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
    }


    @Override
    public boolean exist(String id) {

        try {
            Statement stmt = connection.createStatement();

            String sqlQuery = "SELECT * FROM"+getTableName()+
                    "WHERE id ="+"\'"+id+"\'";
            ResultSet rs = stmt.executeQuery(sqlQuery);
            return rs.next();

        } catch (java.sql.SQLException e) {
             System.out.println(e.toString());
        }
        return false;
    }
}