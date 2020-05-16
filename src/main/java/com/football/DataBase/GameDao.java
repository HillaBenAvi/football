package com.football.DataBase;

import com.football.Domain.Game.Game;
import com.football.Domain.Game.Team;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class GameDao implements DAOTEMP<Game> {
    private static final GameDao instance = new GameDao();
    DBConnector dbc = DBConnector.getInstance();

    //private constructor to avoid client applications to use constructor
    public static GameDao getInstance(){
        return instance;
    }

    @Override
    public String getTableName() {
        return "`Games`";
    }

    private GameDao() {

    }

    @Override
    public String get(String id) {

        String toReturn="";
        try {
            //it was before   Connection connection = DBconector.getConnection();
            Connection connection = dbc.getConnection();
            String sqlQuery = "SELECT * From "+getTableName()+" WHERE idGame="+id+";";
            System.out.println(sqlQuery);

            PreparedStatement ps = connection.prepareStatement(sqlQuery); //compiling query in the DB
            ResultSet rs=ps.executeQuery();

            String idGame=rs.getString("`idGame`");
            String dateTime=rs.getString("`dateTime`");
            String hostTeam=rs.getString("`hostTeam`");
            String visitorTeam=rs.getString("`visitorTeam`");
            String field=rs.getString("`field`");
            String result=rs.getString("`result`");
            String eventList=rs.getString("`eventList`");
            String league=rs.getString("`league`");
            String season=rs.getString("`season`");
            String refereeList=rs.getString("`refereeList`");

            toReturn=idGame+":"+dateTime+":"+hostTeam+":"+visitorTeam+":"+field+":"+result+":"+eventList+":"+league+":"+season+":"+refereeList;
            rs.close();

        } catch (SQLException e) {
            System.out.println(e.toString());
        }

        return toReturn;

    }


    @Override
    public List<String> getAll() {
        LinkedList<String> allTheTable=new LinkedList<>();
        try {
            Connection connection = dbc.getConnection();
            String sqlQuery = "SELECT * From "+getTableName()+";";
            System.out.println(sqlQuery);

            PreparedStatement ps = connection.prepareStatement(sqlQuery); //compiling query in the DB
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                String idGame=rs.getString("`idGame`");
                String dateTime=rs.getString("`dateTime`");
                String hostTeam=rs.getString("`hostTeam`");
                String visitorTeam=rs.getString("`visitorTeam`");
                String field=rs.getString("`field`");
                String result=rs.getString("`result`");
                String eventList=rs.getString("`eventList`");
                String league=rs.getString("`league`");
                String season=rs.getString("`season`");
                String refereeList=rs.getString("`refereeList`");

                String toReturn=idGame+":"+dateTime+":"+hostTeam+":"+visitorTeam+":"+field+":"+result+":"+eventList+":"+league+":"+season+":"+refereeList;
                allTheTable.add(toReturn);
            }

            rs.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

        return allTheTable;
    }

    @Override
    public void save(Game game) {
        try {
            Connection connection = dbc.getConnection();
            Statement stmt = connection.createStatement();

            String sql = "INSERT INTO"+getTableName()+
                    "VALUES ("+game.getId()+","+game.getDateAndTimeString()+","+game.getHostTeam().getName()+","+game.getVisitorTeam().getName()+","+game.getField().getName()+");";
            //finish it
            // TODO: 12/05/2020
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
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
            Connection connection = dbc.getConnection();
            Statement stmt = connection.createStatement();

            String sql = "DELETE FROM"+getTableName()+
                    "WHERE gameid="+id;
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public boolean exist(String id) {
        try {
            Connection connection = dbc.getConnection();
            Statement stmt = connection.createStatement();

            String sqlQuery = "SELECT * FROM"+getTableName()+
                    "WHERE gameid="+id;
            System.out.println(sqlQuery);
            ResultSet rs = stmt.executeQuery(sqlQuery);
            return rs.next();

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return false;
    }
}