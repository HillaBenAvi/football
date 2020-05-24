package com.football.DataBase;

import com.football.Domain.Asset.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

@Repository
public class FieldDao implements DAO<Field> {
    @Autowired
    public DBConnector dbc=new DBConnector();;

  //  private static final FieldDao instance = new FieldDao();

    //private constructor to avoid client applications to use constructor
 //   public static FieldDao getInstance(){
  //      return instance;
 //   }
  //  DBConnector dbc;//= DBConnector.getInstance();
    Connection connection=dbc.getConnection();

    @Override
    public String getTableName() {
        return " field ";
    }

    public FieldDao() {
    }

    @Override
    public String get(String id) {
        String toReturn="";
        try {
            String sqlQuery = "SELECT * From "+getTableName()+" WHERE nameOfField="+"\'"+id+"\'"+";";

            PreparedStatement ps = connection.prepareStatement(sqlQuery); //compiling query in the DB
            ResultSet rs=ps.executeQuery();
            if(rs.next()) {
                String nameOfField = rs.getString("nameOfField");
                String team = rs.getString("team");


                toReturn = nameOfField + ":" + team;
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

                String nameOfField=rs.getString("nameOfField");
                String team=rs.getString("team");


                String toReturn=nameOfField+":"+team;
                allTheTable.add(toReturn);
            }
            rs.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
        return allTheTable;
    }





    @Override
    public void save(Field field){
        try {
            Statement stmt = connection.createStatement();

            String sql = "INSERT INTO"+getTableName()+
                    "VALUES ("+field.toString()+");";//"\'"+field.getNameOfField()+"\'"+","+"\'"+" "+"\'"+");";
            stmt.executeUpdate(sql);
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
    }


    @Override
    public void update(String userMail , Field field) {
        //delete and than add new one
        delete(userMail);
        save(field);
    }

    @Override
    public void delete(String nameOfField) {
        try {
            Statement stmt = connection.createStatement();

            String sql = "DELETE FROM"+getTableName()+
                    "WHERE  nameOfField="+"\'"+nameOfField+"\'";
            stmt.executeUpdate(sql);
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
    }


    @Override
    public boolean exist(String fanName) {

        try {
            Statement stmt = connection.createStatement();

            String sqlQuery = "SELECT * FROM"+getTableName()+
                    "WHERE nameOfField ="+"\'"+fanName+"\'";
            ResultSet rs = stmt.executeQuery(sqlQuery);
            return rs.next();

        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
        return false;
    }
}