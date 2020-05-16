package com.football.DataBase;

import Domain.League.League;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class LeagueDao  implements DAOTEMP<League> {
    private static final LeagueDao instance = new LeagueDao();

    //private constructor to avoid client applications to use constructor
    public static LeagueDao getInstance(){
        return instance;
    }
    DBConnector dbc = DBConnector.getInstance();

    @Override
    public String getTableName() {
        return "`League`";
    }

    private LeagueDao() {

    }


    @Override
    public String get(String id) {


        String toReturn="";
        try {
            Connection connection = dbc.getConnection();
            String sqlQuery = "SELECT * From "+getTableName()+" WHERE idLeague="+id+";";
            System.out.println(sqlQuery);

            PreparedStatement ps = connection.prepareStatement(sqlQuery); //compiling query in the DB
            ResultSet rs=ps.executeQuery();
            String idLeague=rs.getString("`idLeague`");
            String seasonsId=rs.getString("`seasonsId`");
            toReturn=idLeague+":"+seasonsId;

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
                String idLeague=rs.getString("`idLeague`");
                String seasonsId=rs.getString("`seasonsId`");

                String toReturn=idLeague+":"+seasonsId;
                allTheTable.add(toReturn);
            }

            rs.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

        return allTheTable;
    }


    @Override
    public void save(League league){
        try {
            Connection connection = dbc.getConnection();
            Statement stmt = connection.createStatement();

            String sql = "INSERT INTO"+getTableName()+
                    "VALUES ("+league.getName()+","+league.getSeasons().toString()+");";
            //finish it
            // TODO: 12/05/2020
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }


    @Override
    public void update(String name , League league) {
        //delete and than add new one
        delete(name);
        save(league);
    }

    @Override
    public void delete(String leagueName) {
        try {
            Connection connection = dbc.getConnection();
            Statement stmt = connection.createStatement();

            String sql = "DELETE FROM"+getTableName()+
                    "WHERE name ="+leagueName;
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }


    @Override
    public boolean exist(String leagueName) {

        try {
            Connection connection = dbc.getConnection();
            Statement stmt = connection.createStatement();

            String sqlQuery = "SELECT * FROM"+getTableName()+
                    "WHERE name ="+leagueName;
            System.out.println(sqlQuery);
            ResultSet rs = stmt.executeQuery(sqlQuery);
            return rs.next();

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return false;
    }
}