package com.football.DataBase;

import com.football.Domain.Game.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
@Repository

public class TeamDao implements DAOTEMP<Team> {
    @Autowired
    public DBConnector dbc=new DBConnector();;

 //   private static final TeamDao instance = new TeamDao();

    //private constructor to avoid client applications to use constructor
  //  public static TeamDao getInstance() {
       // return instance;
  //  }

  //  DBConnector dbc;// = DBConnector.getInstance();
  Connection connection=dbc.getConnection();

    @Override
    public String getTableName() {
        return " Teams ";
    }

    public TeamDao() {

     //   connection = dbc.getConnection();
    }

    @Override
    public String get(String id) {
        String toReturn="";
        try {
            String sqlQuery = "SELECT * From "+getTableName()+" WHERE name="+"\'"+id+"\'"+";";
            System.out.println(sqlQuery);

            PreparedStatement ps = connection.prepareStatement(sqlQuery); //compiling query in the DB
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String account = rs.getString("account");
                String coaches = rs.getString("coaches");
                String players = rs.getString("players");
                String managers = rs.getString("managers");
                String owners = rs.getString("owners");
                String homeField = rs.getString("homeField");
                String games = rs.getString("games");
                String trainingField = rs.getString("trainingField");
                String status = rs.getString("status");
                String personalPage = rs.getString("personalPage");

                toReturn = name + ":" + account + ":" + coaches + ":" + players+ ":" + managers+ ":" +
                        owners+ ":" + homeField+ ":" + games+ ":" + trainingField+ ":" + status+ ":" + personalPage;
            }
            rs.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
        return toReturn;
    }

    //   @Override
    public List<String> getAll() {
        LinkedList<String> allTheTable = new LinkedList<>();
        try {
            String sqlQuery = "SELECT * From " + getTableName()+ ";";
            System.out.println(sqlQuery);

            PreparedStatement ps = connection.prepareStatement(sqlQuery); //compiling query in the DB
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String account = rs.getString("account");
                String coaches = rs.getString("coaches");
                String players = rs.getString("players");
                String managers = rs.getString("managers");
                String owners = rs.getString("owners");
                String homeField = rs.getString("homeField");
                String games = rs.getString("games");
                String trainingField = rs.getString("trainingField");
                String status = rs.getString("status");
                String personalPage = rs.getString("personalPage");

                String toReturn = name + ":" + account + ":" + coaches + ":" + players+ ":" + managers+ ":" +
                        owners+ ":" + homeField+ ":" + games+ ":" + trainingField+ ":" + status+ ":" + personalPage;
                allTheTable.add(toReturn);
            }
            rs.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
        return allTheTable;
    }

    //   @Override
    public void save(Team team) {
        try {
            // Connection connection = dbc.getConnection();
            Statement stmt = connection.createStatement();

            String sql = "INSERT INTO"+getTableName()+
                    " VALUES ("+team.toString()+");";
          //  System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void update(String id, Team team) {
        //delete and than add new one
        delete(id);
        save(team);
    }

    @Override
    public void delete(String id) {
        try {
            Statement stmt = connection.createStatement();

            String sql = "DELETE FROM "+getTableName()+
                    "WHERE name = "+"\'"+id+"\'";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
    }


    @Override
    public boolean exist(String id) {
        try {
            Statement stmt = connection.createStatement();

            String sqlQuery = "SELECT * FROM "+getTableName()+
                    "WHERE userName ="+"\'"+id+"\'";
            System.out.println(sqlQuery);
            ResultSet rs = stmt.executeQuery(sqlQuery);
            return rs.next();

        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
        return false;
    }

}