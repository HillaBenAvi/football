package com.football.DataBase;

import com.football.Domain.Game.Team;
import com.football.Domain.League.Season;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
@Repository

public class SeasonDao  implements DAOTEMP<Season> {

    @Autowired
    public DBConnector dbc=new DBConnector();;

  //  private static final SeasonDao instance = new SeasonDao();

    //private constructor to avoid client applications to use constructor
  //  public static SeasonDao getInstance(){
  //      return instance;
  //  }
  //  DBConnector dbc;//= DBConnector.getInstance();
    Connection connection=dbc.getConnection();

    @Override
    public String getTableName() {
        return " Season ";
    }

    public SeasonDao() {

       // connection=dbc.getConnection();
    }

    @Override
    public String get(String id) {

        String toReturn="";
        try {
            Connection connection = dbc.getConnection();
            String sqlQuery = "SELECT * From "+getTableName()+" WHERE idSeason="+"\'"+id+"\'"+";";
            //   System.out.println(sqlQuery);

            PreparedStatement ps = connection.prepareStatement(sqlQuery); //compiling query in the DB
            ResultSet rs=ps.executeQuery();
            if (rs.next()){
                String idSeason=rs.getString("idSeason");
                String leagusID=rs.getString("leagusID");
                toReturn=idSeason+":"+leagusID;
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
            //     System.out.println(sqlQuery);

            PreparedStatement ps = connection.prepareStatement(sqlQuery); //compiling query in the DB
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                String idSeason=rs.getString("idSeason");
                String leagusID=rs.getString("leagusID");

                String toReturn=idSeason+":"+leagusID;
                allTheTable.add(toReturn);
            }

            rs.close();
        } catch (java.sql.SQLException e) {
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
                    "VALUES ("+season.toString()+");";//"\'"+season.getYear()+"\'"+","+"\'"+" "+"\'"+");";
            //finish it
            // TODO: 12/05/2020
            //     System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (java.sql.SQLException e) {
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
                    "WHERE idSeason ="+"\'"+seasonYear+"\'";
            //   System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public boolean exist(String seasonYear) {
        try {
            Connection connection = dbc.getConnection();
            Statement stmt = connection.createStatement();

            String sqlQuery = "SELECT * FROM" + getTableName() +
                    "WHERE idSeason =" +"\'"+seasonYear+"\'";
            //  System.out.println(sqlQuery);
            ResultSet rs = stmt.executeQuery(sqlQuery);
            return rs.next();

        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
        return false;
    }

}