package com.football.DataBase;

import com.football.Domain.League.LeagueInSeason;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
public class LeagueInSeasonDao  implements DAOTEMP<LeagueInSeason> {

    private static final LeagueInSeasonDao instance = new LeagueInSeasonDao();

    //private constructor to avoid client applications to use constructor
    public static LeagueInSeasonDao getInstance(){
        return instance;
    }
    DBConnector dbc;//= DBConnector.getInstance();
    Connection connection;

    @Override
    public String getTableName() {
        return " LeagueInSeason ";
    }

    private LeagueInSeasonDao() {

        connection=dbc.getConnection();
    }

    @Override
    public String get(String id) {
        String leagueIdFromUser=id.split(":")[0];
        String seasonIdFromUser=id.split(":")[1];
        String toReturn="";
        try {
            Connection connection = dbc.getConnection();
            String sqlQuery = "SELECT * From "+getTableName()+" WHERE leagueId="+"\'"+leagueIdFromUser+"\'"+"AND seasonId="+"\'"+seasonIdFromUser+"\'"+";";
            //  System.out.println(sqlQuery);

            PreparedStatement ps = connection.prepareStatement(sqlQuery); //compiling query in the DB
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                String leagueId=rs.getString("leagueId");
                String seasonId=rs.getString("seasonId");
                String teams=rs.getString("teams");
                String referees=rs.getString("referees");
                String games=rs.getString("games");
                String scedulePolicy=rs.getString("scedulePolicy");
                String pointPolicy=rs.getString("pointPolicy");

                toReturn=leagueId+":"+seasonId+":"+teams+":"+referees+":"+games+":"+scedulePolicy+":"+pointPolicy;
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
            Connection connection = dbc.getConnection();
            String sqlQuery = "SELECT * From "+getTableName()+";";
            //  System.out.println(sqlQuery);

            PreparedStatement ps = connection.prepareStatement(sqlQuery); //compiling query in the DB
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                String leagueId=rs.getString("leagueId");
                String seasonId=rs.getString("seasonId");
                String teams=rs.getString("teams");
                String referees=rs.getString("referees");
                String games=rs.getString("games");
                String scedulePolicy=rs.getString("scedulePolicy");
                String pointPolicy=rs.getString("pointPolicy");

                String toReturn=leagueId+":"+seasonId+":"+teams+":"+referees+":"+games+":"+scedulePolicy+":"+pointPolicy;
                allTheTable.add(toReturn);
            }

            rs.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }

        return allTheTable;
    }



    @Override
    public void save(LeagueInSeason leagueInSeason) {
        try {
            Connection connection = dbc.getConnection();
            Statement stmt = connection.createStatement();

            String sql = "INSERT INTO"+getTableName()+
                    "VALUES ("+leagueInSeason.toString()+");";//+"\'"+leagueInSeason.getLeague().getName()+"\'"+","+"\'"+leagueInSeason.getSeason().getYear()+"\'"+
            //    ","+"\'"+leagueInSeason.getTeams().toString()+"\'"+","+"\'"+leagueInSeason.getReferees().toString()+"\'"+
            //   ","+"\'"+/leagueInSeason.getGames().toString()/" "+"\'"+","+"\'"+/leagueInSeason.getSchedulePolicy().toString()/""+"\'"+
            //   ","+"\'"+/leagueInSeason.getScorePolicy().toString()/""+"\'"+");";
            //finish it
            // TODO: 12/05/2020
            //  System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void update(String id , LeagueInSeason leagueInSeason) {
        //delete and than add new one
        delete(id);
        save(leagueInSeason);
    }

    @Override
    public void delete(String leagueInSeason) {
        String leagueId=leagueInSeason.split(":")[0];
        String seasonId=leagueInSeason.split(":")[1];

        try {
            Connection connection = dbc.getConnection();
            Statement stmt = connection.createStatement();

            String sql = "DELETE FROM"+getTableName()+
                    "WHERE leagueId ="+"\'"+leagueId +"\'"+" And seasonId=" + "\'"+seasonId+"\'";
            // System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public boolean exist(String leagueInSeason) {
        String leagueId=leagueInSeason.split(":")[0];
        String seasonId=leagueInSeason.split(":")[1];
        try {
            Connection connection = dbc.getConnection();
            Statement stmt = connection.createStatement();

            String sqlQuery = "SELECT * FROM"+getTableName()+
                    "WHERE leagueId ="+"\'"+leagueId+"\'" +" And seasonId=" +"\'"+ seasonId+"\'";
            // System.out.println(sqlQuery);
            ResultSet rs = stmt.executeQuery(sqlQuery);
            return rs.next();

        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
        return false;
    }
}