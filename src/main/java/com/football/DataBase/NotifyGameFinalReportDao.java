package com.football.DataBase;

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
public class NotifyGameFinalReportDao implements DAO<String> {

    @Autowired
    public DBConnector dbc=new DBConnector();

    public NotifyGameFinalReportDao() {
    }

    Connection connection=dbc.getConnection();

    @Override
    public String getTableName() {
        return " NotifyGameFinalReport ";
    }



    @Override
    public String get(String userMail) {
        String toReturn="";
        try {
            String sqlQuery = "SELECT * From "+getTableName()+" WHERE userMail="+"\'"+userMail+"\'"+";";

            PreparedStatement ps = connection.prepareStatement(sqlQuery); //compiling query in the DB
            ResultSet rs=ps.executeQuery();

            if(rs.next()) {
                String userName = rs.getString("userMail");

                toReturn = userName;
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
                String userName = rs.getString("userMail");

                String toReturn = userName;
                allTheTable.add(toReturn);
            }
            rs.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
        return allTheTable;
    }


    @Override
    public void save(String userMail){
        try {
            Statement stmt = connection.createStatement();

            String sql = "INSERT INTO"+getTableName()+
                    "VALUES (\'"+userMail+"\');";//"\'"+associationDelegate.getUserMail()+"\'"+","+"\'"+associationDelegate.getPassword()+"\'"+","+"\'"+associationDelegate.getName()+"\'"+","+"\'"+associationDelegate.getBirthDate().toString()+"\'"+");";
            stmt.executeUpdate(sql);
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
    }


    @Override
    public void update(String oldUserMail , String userMail ) throws ObjectNotExist {
        if(exist(oldUserMail)) {
            //delete and than add new one
            delete(oldUserMail);
            save(userMail);
        }
        else
        {
            throw new ObjectNotExist("this object not exist , so you cant update it");
        }
    }

    @Override
    public void delete(String userMail) {
        try {
            Statement stmt = connection.createStatement();

            String sql = "DELETE FROM"+getTableName()+
                    "WHERE userMail ="+"\'"+userMail+"\'";
            stmt.executeUpdate(sql);
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
    }


    @Override
    public boolean exist(String userMail) {

        try {
            Statement stmt = connection.createStatement();

            String sqlQuery = "SELECT * FROM"+getTableName()+
                    "WHERE userMail ="+"\'"+userMail+"\'";
            ResultSet rs = stmt.executeQuery(sqlQuery);
            return rs.next();

        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
        return false;
    }
}