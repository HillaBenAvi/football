package com.football.DataBase;

import Domain.Game.Team;
import Domain.League.Season;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class SeasonDao  implements DAOTEMP<Season> {

    private static final SeasonDao instance = new SeasonDao();

    //private constructor to avoid client applications to use constructor
    public static SeasonDao getInstance(){
        return instance;
    }

    @Override
    public String getTableName() {
        return "`Season`";
    }

    private SeasonDao() {

    }
    DBConnector dbc = DBConnector.getInstance();





    @Override
    public String get(String id) {

        String toReturn="";
        try {
            Connection connection = dbc.getConnection();
            String sqlQuery = "SELECT * From "+getTableName()+" WHERE idSeason="+id+";";
            System.out.println(sqlQuery);

            PreparedStatement ps = connection.prepareStatement(sqlQuery); //compiling query in the DB
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                String idSeason=rs.getString("`idSeason`");
                String leagusID=rs.getString("`leagusID`");
                toReturn=idSeason+":"+leagusID;
            }

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
                String idSeason=rs.getString("`idSeason`");
                String leagusID=rs.getString("`leagusID`");

                String toReturn=idSeason+":"+leagusID;
                allTheTable.add(toReturn);
            }

            rs.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

        return allTheTable;
    }

    @Override
    public void save(Season season){
        try {
            Connection connection = dbc.getConnection();
            Statement stmt = connection.createStatement();

            String sql = "INSERT INTO"+getTableName()+
                    "VALUES ("+season.getYear()+","+season.getLeagues().toString()+");";
            //finish it
            // TODO: 12/05/2020
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void update(String year , Season season) {
        //delete and than add new one
        delete(year);
        save(season);
    }

    @Override
    public void delete(String seasonYear) {
        try {
            Connection connection = dbc.getConnection();
            Statement stmt = connection.createStatement();

            String sql = "DELETE FROM"+getTableName()+
                    "WHERE year ="+seasonYear;
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public boolean exist(String seasonYear) {
        try {
            Connection connection = dbc.getConnection();
            Statement stmt = connection.createStatement();

            String sqlQuery = "SELECT * FROM" + getTableName() +
                    "WHERE YEAR =" + seasonYear;
            System.out.println(sqlQuery);
            ResultSet rs = stmt.executeQuery(sqlQuery);
            return rs.next();

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return false;
    }

}