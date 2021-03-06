package com.football.DataBase;

import com.football.Domain.League.League;
import com.football.Exception.ObjectNotExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;


@Repository
public class LeagueDao  implements DAO<League> {

    @Autowired
    public DBConnector dbc=new DBConnector();;

//    private static final LeagueDao instance = new LeagueDao();

    //private constructor to avoid client applications to use constructor
  //  public static LeagueDao getInstance(){
    //    return instance;
  //  }

 //   DBConnector  dbc;//= DBConnector.getInstance();
 Connection connection=dbc.getConnection();

    @Override
    public String getTableName() {
        return " League ";
    }

    public LeagueDao() {

      // connection=dbc.getConnection();
    }

    @Override
    public String get(String id) {


        String toReturn="";
        try {
            String sqlQuery = "SELECT * From "+getTableName()+" WHERE idLeague="+"\'"+id+"\'"+";";

            PreparedStatement ps = connection.prepareStatement(sqlQuery); //compiling query in the DB
            ResultSet rs=ps.executeQuery();
            if(rs.next()) {
                String idLeague = rs.getString("idLeague");
                String seasonsId="";
                if (!rs.wasNull() || rs.getObject("seasonsId")!=null) {
                    seasonsId = rs.getString("seasonsId");
                }
                toReturn = idLeague + ":" + seasonsId;
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
                String idLeague=rs.getString("idLeague");
                String seasonsId=rs.getString("seasonsId");

                String toReturn=idLeague+":"+seasonsId;
                allTheTable.add(toReturn);
            }

            rs.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }

        return allTheTable;
    }


    @Override
    public void save(League league){
        try {
            Statement stmt = connection.createStatement();
            String sql="";
            sql = "INSERT INTO" + getTableName() +
                    "VALUES ("+league.toString()+");";// + "\'" + league.getName() + "\'" + "," + "\'" + league.getSeasonString() + "\'" + ");";
            stmt.executeUpdate(sql);
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void update(String name , League league) throws ObjectNotExist {
        if(exist(league.getName())) {
            //delete and than add new one
            delete(name);
            save(league);
        }
        else
        {
            throw new ObjectNotExist("this object not exist , so you cant update it");
        }

    }

    @Override
    public void delete(String leagueName) {
        try {
            Statement stmt = connection.createStatement();

            String sql = "DELETE FROM"+getTableName()+
                    "WHERE idLeague ="+ "\'" +leagueName+ "\'" ;
            stmt.executeUpdate(sql);
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
    }


    @Override
    public boolean exist(String leagueName) {

        try {
            Statement stmt = connection.createStatement();

            String sqlQuery = "SELECT * FROM"+getTableName()+
                    "WHERE idLeague ="+ "\'" +leagueName+ "\'" ;
            ResultSet rs = stmt.executeQuery(sqlQuery);
            return rs.next();

        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
        return false;
    }
}