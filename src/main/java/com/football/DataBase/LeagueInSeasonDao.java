package com.football.DataBase;

import com.football.Domain.League.LeagueInSeason;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Repository

public class LeagueInSeasonDao  implements DAO<LeagueInSeason> {

    @Autowired
    public DBConnector dbc=new DBConnector();;

 //   private static final LeagueInSeasonDao instance = new LeagueInSeasonDao();

    //private constructor to avoid client applications to use constructor
 //   public static LeagueInSeasonDao getInstance(){
    //    return instance;
  //  }
  //  DBConnector dbc;//= DBConnector.getInstance();
    Connection connection=dbc.getConnection();

    @Override
    public String getTableName() {
        return " LeagueInSeason ";
    }

    public LeagueInSeasonDao() {
    }

    @Override
    public String get(String id) {
        String leagueIdFromUser=id.split(":")[0];
        String seasonIdFromUser=id.split(":")[1];
        String toReturn="";
        try {
            String sqlQuery = "SELECT * From "+getTableName()+" WHERE leagueId="+"\'"+leagueIdFromUser+"\'"+"AND seasonId="+"\'"+seasonIdFromUser+"\'"+";";

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
            String sqlQuery = "SELECT * From "+getTableName()+";";

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
            Statement stmt = connection.createStatement();

            String sql = "INSERT INTO"+getTableName()+
                    "VALUES ("+leagueInSeason.toString()+");";
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
            Statement stmt = connection.createStatement();

            String sql = "DELETE FROM"+getTableName()+
                    "WHERE leagueId ="+"\'"+leagueId +"\'"+" And seasonId=" + "\'"+seasonId+"\'";
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
            Statement stmt = connection.createStatement();

            String sqlQuery = "SELECT * FROM"+getTableName()+
                    "WHERE leagueId ="+"\'"+leagueId+"\'" +" And seasonId=" +"\'"+ seasonId+"\'";
            ResultSet rs = stmt.executeQuery(sqlQuery);
            return rs.next();

        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
        return false;
    }
}