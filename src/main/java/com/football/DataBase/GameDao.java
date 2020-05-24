package com.football.DataBase;

import com.football.Domain.Game.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Repository
public class GameDao implements DAO<Game> {

    @Autowired
    public DBConnector dbc=new DBConnector();;

 //   private static final GameDao instance = new GameDao();
   // DBConnector dbc;// = DBConnector.getInstance();

    //private constructor to avoid client applications to use constructor
   // public static GameDao getInstance(){
   //     return instance;
  //  }
    Connection connection=dbc.getConnection();

    @Override
    public String getTableName() {
        return " Games ";
    }

    public GameDao() {

    }

    @Override
    public String get(String id) {

        String toReturn="";
        try {
            Connection connection = dbc.getConnection();
            String sqlQuery = "SELECT * From "+getTableName()+" WHERE idGame=\'"+id+"\';";

            PreparedStatement ps = connection.prepareStatement(sqlQuery); //compiling query in the DB
            ResultSet rs=ps.executeQuery();

            if(rs.next()) {
                String idGame=rs.getString("idGame");
                String dateTime=rs.getString("dateTime");
                String hostTeam=rs.getString("hostTeam");
                String visitorTeam=rs.getString("visitorTeam");
                String field=rs.getString("field");
                String result=rs.getString("result");
                String eventList=rs.getString("eventList");
                String league=rs.getString("league");
                String season=rs.getString("season");
                String refereeList=rs.getString("refereeList");

                toReturn=idGame+":"+dateTime+":"+hostTeam+":"+visitorTeam+":"+field+":"+result+":"+eventList+":"+league+":"+season+":"+refereeList;

            }

            rs.close();

        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }

        return toReturn;

    }


    @Override
    public List<String> getAll() {
        LinkedList<String> allTheTable=new LinkedList<>();
        try {
            String sqlQuery = "SELECT * From "+getTableName()+";";

            PreparedStatement ps = connection.prepareStatement(sqlQuery); //compiling query in the DB
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                String idGame=rs.getString("idGame");
                String dateTime=rs.getString("dateTime");
                String hostTeam=rs.getString("hostTeam");
                String visitorTeam=rs.getString("visitorTeam");
                String field=rs.getString("field");
                String result=rs.getString("result");
                String eventList=rs.getString("eventList");
                String league=rs.getString("league");
                String season=rs.getString("season");
                String refereeList=rs.getString("refereeList");

                String toReturn=idGame+":"+dateTime+":"+hostTeam+":"+visitorTeam+":"+field+":"+result+":"+eventList+":"+league+":"+season+":"+refereeList;
                allTheTable.add(toReturn);
            }

            rs.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }

        return allTheTable;
    }

    @Override
    public void save(Game game) {
        try {
            Statement stmt = connection.createStatement();

            String sql = "INSERT INTO"+getTableName()+
                    "VALUES ("+game.toString()+");";//+game.getId()+","+game.getDateAndTimeString()+","+game.getHostTeam().getName()+","+game.getVisitorTeam().getName()+","+game.getField().getName()+");";
            stmt.executeUpdate(sql);
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void update(String gameId, Game game) {
        //delete and than add new one
        delete(gameId);
        save(game);
    }

    @Override
    public void delete(String id) {
        try {
            Statement stmt = connection.createStatement();

            String sql = "DELETE FROM"+getTableName()+
                    "WHERE gameid="+id;
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
                    "WHERE gameid="+id;
            ResultSet rs = stmt.executeQuery(sqlQuery);
            return rs.next();

        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
        return false;
    }
}